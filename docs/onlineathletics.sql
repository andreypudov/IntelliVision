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

DROP DATABASE IF EXISTS onlineathletics;
CREATE DATABASE onlineathletics CHARACTER SET 'utf8';

USE onlineathletics;

-- create general application table structure

-- create athlete layer
CREATE TABLE oa_first_nm_tbl (
	first_nm_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	first_name  VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(first_nm_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_second_nm_tbl (
	second_nm_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	second_name  VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(second_nm_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_birthday_tbl (
	birthday_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	birthday	TIMESTAMP NOT NULL DEFAULT 0 UNIQUE,
	PRIMARY KEY	(birthday_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_athl_tbl (
	athl_id 	  INT UNSIGNED NOT NULL AUTO_INCREMENT,
	first_nm_key  INT UNSIGNED NOT NULL,
	second_nm_key INT UNSIGNED NOT NULL,
	birthday_key  INT UNSIGNED NOT NULL,
	sex           TINYINT(1) NOT NULL DEFAULT 0,
	PRIMARY KEY (athl_id),
	FOREIGN KEY (first_nm_key)
		REFERENCES oa_first_nm_tbl(first_nm_id),
	FOREIGN KEY (second_nm_key)
		REFERENCES oa_second_nm_tbl(second_nm_id),
	FOREIGN KEY (birthday_key)
		REFERENCES oa_birthday_tbl(birthday_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8' AUTO_INCREMENT = 100;

-- create online account table to store user credentials
CREATE TABLE oa_accnt_user_tbl (
	user_id     INT UNSIGNED NOT NULL AUTO_INCREMENT,
	user_name   VARCHAR(32)  NOT NULL UNIQUE,
	pass_phrase VARCHAR(60)  NOT NULL,
	attempt     INT UNSIGNED NOT NULL,
	PRIMARY KEY	(user_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_accnt_groups_tbl (
	group_id   INT UNSIGNED NOT NULL AUTO_INCREMENT,
	group_name VARCHAR(16)  NOT NULL,
	PRIMARY KEY	(group_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_accnt_roles_tbl (
	user_key  INT UNSIGNED NOT NULL,
	group_key INT UNSIGNED NOT NULL,
	PRIMARY KEY (user_key, group_key),
	FOREIGN KEY (user_key)
		REFERENCES oa_accnt_user_tbl(user_id),
	FOREIGN KEY (group_key)
		REFERENCES oa_accnt_groups_tbl(group_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- apudov    'dfgiwr@lk5f$oiu%5e4r'
-- sijbaraev 'f23ca5f9e48b06c0e270'
SET @apudov_hashed    = '$2a$31$4GQZ7v9mMnKFCLHivRdCHewX6XP4Mn.jQu.Y04Z38yA2dMzZv5fjG';
SET @sijbaraev_hashed = '$2a$31$wr62CwTtIouEhQOM/m1EB.ArYHPL6Vu.kiTnfxwT5iRGsvR1FXkZ.';

INSERT INTO oa_accnt_user_tbl(user_name, pass_phrase, attempt)
	VALUES ('apudov',    @apudov_hashed, 0),
	       ('sijbaraev', @sijbaraev_hashed, 0);

INSERT INTO oa_accnt_groups_tbl(group_name)
	VALUES ('db_read'), ('db_write');

SET @apudov_key    = (SELECT user_id FROM oa_accnt_user_tbl WHERE user_name = 'apudov');
SET @sijbaraev_key = (SELECT user_id FROM oa_accnt_user_tbl WHERE user_name = 'sijbaraev');

SET @db_read_key   = (SELECT group_id FROM oa_accnt_groups_tbl WHERE group_name = 'db_read');
SET @db_write_key  = (SELECT group_id FROM oa_accnt_groups_tbl WHERE group_name = 'db_write');

INSERT INTO oa_accnt_roles_tbl(user_key, group_key)
	VALUES (@apudov_key,    @db_read_key),
	       (@apudov_key,    @db_write_key),
	       (@sijbaraev_key, @db_read_key);

-- create stored procedures
DELIMITER //

CREATE PROCEDURE auth_validate_credentials (
	user_nm_arg VARCHAR(32), pass_ph_arg VARCHAR(60))
	NOT DETERMINISTIC
    COMMENT 'Authenticates query with specified credentials. Function validates
             the number of authentication attempt and if third attempt is 
             failed, a signal fired for any following
			 queries, even if provided credentials are correct.

			 @param user_nm_arg the name value to authenticate query.
			 @param pass_ph_arg the hashed password value to authenticate query.

             @throws 45000 Authentication failed.'
BEGIN
	DECLARE attempt_val INT UNSIGNED;

	-- validate routine arguments
	IF ((user_nm_arg IS NULL) 
			OR (pass_ph_arg IS NULL)
			OR (CHAR_LENGTH(user_nm_arg) = 0)
			OR (CHAR_LENGTH(pass_ph_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Authentication failed.';
	END IF;

	-- specified user name doesn't exists
	IF ((SELECT COUNT(user_name)  
			FROM  oa_accnt_user_tbl
			WHERE user_name = user_nm_arg) = 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Authentication failed.';
	END IF;

	-- the number of already listed attempts
	SET attempt_val = (SELECT attempt 
		FROM  oa_accnt_user_tbl
		WHERE user_name = user_nm_arg);
	IF (attempt_val >= 3) THEN
		-- authentication failed when three and more failed attempts occured
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Authentication failed.';
	END IF;

	-- validate credentials
	IF ((SELECT COUNT(user_name)
			FROM oa_accnt_user_tbl
			WHERE   user_name   = user_nm_arg
				AND pass_phrase = pass_ph_arg) = 0) THEN
		UPDATE oa_accnt_user_tbl
			SET   attempt   = attempt_val + 1 
			WHERE user_name = user_nm_arg;
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Authentication failed.';
	END IF;

	-- clear attempts index on success
	UPDATE oa_accnt_user_tbl
		SET   attempt   = 0 
		WHERE user_name = user_nm_arg;
END //

CREATE PROCEDURE auth_has_group (user_nm_arg VARCHAR(32), user_gp_arg VARCHAR(16))
	NOT DETERMINISTIC
	COMMENT 'Validates that specified user account is in the given group.
	         If given user has not in the given group, an signal has thrown.

	         @param user_gp_arg the user group to validate.
	         @param user_nm_arg the name value to authenticate query.

			 @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.'
BEGIN
	-- validate routine arguments
	IF ((user_nm_arg IS NULL)
		 	OR (user_gp_arg IS NULL)
			OR (CHAR_LENGTH(user_nm_arg) = 0)
			OR (CHAR_LENGTH(user_gp_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate user account group
	IF ((SELECT COUNT(*) 
			FROM oa_accnt_roles_tbl r
				INNER JOIN oa_accnt_groups_tbl g ON g.group_id = r.group_key
				INNER JOIN oa_accnt_user_tbl   a ON a.user_id  = r.user_key
			WHERE   a.user_name  = user_nm_arg
				AND g.group_name = user_gp_arg) = 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Permissions denied.';
	END IF;
END //

CREATE PROCEDURE auth_get_hash (user_nm_arg VARCHAR(32))
	NOT DETERMINISTIC
	COMMENT 'Returns a hashed password value of a given user account.

	         @param user_nm_arg the user account which hash to return.

			 @throws 45000 Invalid argument exception.
			 @throws 45000 Account doesn\'t exists.'
BEGIN
	DECLARE hash_var VARCHAR(60);

	-- validate routine arguments
	IF ((user_nm_arg IS NULL)
			OR (CHAR_LENGTH(user_nm_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	SELECT pass_phrase
		INTO hash_var
		FROM oa_accnt_user_tbl
		WHERE user_name = user_nm_arg;

	-- select doesn't returns any data
	IF (hash_var IS NULL) THEN
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Account doesn\'t exists.';
	END IF;

	SELECT hash_var;
END //

CREATE PROCEDURE auth_get_group_list (user_nm_arg VARCHAR(32))
	NOT DETERMINISTIC
	COMMENT 'Returns a list of groups assigned to a given user account.

	         @param user_nm_arg the user account which groups to return.

			 @throws 45000 Invalid argument exception.'
BEGIN
	-- validate routine arguments
	IF ((user_nm_arg IS NULL)
			OR (CHAR_LENGTH(user_nm_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	SELECT g.group_name
		FROM oa_accnt_roles_tbl r
			INNER JOIN oa_accnt_groups_tbl g ON g.group_id = r.group_key
			INNER JOIN oa_accnt_user_tbl   a ON a.user_id  = r.user_key
		WHERE a.user_name = user_nm_arg; 
END //

DELIMITER ;