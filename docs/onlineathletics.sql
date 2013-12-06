--
-- IntelliJustice Intelligent Referee Assistant System 
--
-- The MIT License
--
-- Copyright 2009-2013 Andrey Pudov.
--
-- Permission is hereby granted, free of charge, to any person obtaining a copy
-- of this software and associated documentation files (the "Software"), to deal
-- in the Software without restriction, including without limitation the rights
-- to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
-- copies of the Software, and to permit persons to whom the Software is
-- furnished to do so, subject to the following conditions:
--
-- The above copyright notice and this permission notice shall be included in
-- all copies or substantial portions of the Software.
--
-- THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
-- IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
-- FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
-- AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
-- LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
-- OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
-- THE SOFTWARE.
--

--
-- The project database creation script.
--
-- @author    Andrey Pudov        <mail@andreypudov.com>
-- @version   0.00.00
-- %name      onlineathletics.sql
-- %date      08:40:00 PM, Nov 28, 2013
--

-- start database    -- mysqld
-- shutdown database -- mysqladmin -u root shutdown

DROP DATABASE IF EXISTS onlineathletics;
CREATE DATABASE onlineathletics CHARACTER SET 'utf8';

USE onlineathletics;

-- create online account table to store user credentials
CREATE TABLE oa_accnt_user_tbl (
	name     VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY	(name)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_accnt_groups_tbl (
	name VARCHAR(20) NOT NULL,
	user VARCHAR(255) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- SHA256 is a Password Encryption Algorithm in Application Container
INSERT INTO oa_accnt_user_tbl(name, password)
	VALUES ('administrator', SHA2('dfgiwr@lk5f$oiu%5e4r', 512)),
	       ('user', SHA2('ljdf5i@4o#p#3q3er', 512));

INSERT INTO oa_accnt_groups_tbl(name, user)
	VALUES ('administrators', 'administrator'),
	       ('users', 'user');

-- create public stored procedures
DELIMITER //

-- --------------------------------------------------------------------------------
-- Returns Online Athletics database version number.
-- --------------------------------------------------------------------------------
CREATE PROCEDURE `getDatabaseVersion` () DETERMINISTIC
BEGIN
	SELECT '0.00.00';
END //

DELIMITER ;

CALL `onlineathletics`.`proc`('string');