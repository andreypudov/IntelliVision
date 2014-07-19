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
-- %name      intellijustice.sql
-- %date      10:00:00 AM, Jul 28, 2012
--

-- start database    -- mysqld
-- shutdown database -- mysqladmin -u root shutdown

DROP DATABASE IF EXISTS intellijustice;
CREATE DATABASE intellijustice CHARACTER SET 'utf8';

USE intellijustice;

-- create championship table that stores the lists of all processed championships
CREATE TABLE ij_champ_tbl (
	champ_id 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name     	VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(champ_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_champ_type_en_tbl (
	type_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	type		VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_champ_type_tbl (
	champ_key	INT UNSIGNED NOT NULL UNIQUE, 
	type_en_key INT UNSIGNED NOT NULL,
	FOREIGN KEY (champ_key)
		REFERENCES ij_champ_tbl(champ_id),
	FOREIGN KEY (type_en_key)
		REFERENCES ij_champ_type_en_tbl(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_champ_city_en_tbl (
	city_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	city		VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(city_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_champ_city_tbl (
	champ_key	INT UNSIGNED NOT NULL UNIQUE, 
	city_en_key INT UNSIGNED NOT NULL,
	FOREIGN KEY (champ_key)
		REFERENCES ij_champ_tbl(champ_id),
	FOREIGN KEY (city_en_key)
		REFERENCES ij_champ_city_en_tbl(city_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- true(1) is outdoor, false(0) is indoor
CREATE TABLE ij_champ_format_tbl (
	champ_key	INT UNSIGNED NOT NULL UNIQUE, 
	format      TINYINT(1) DEFAULT NULL,
	FOREIGN KEY (champ_key)
		REFERENCES ij_champ_tbl(champ_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- create athlete layer
CREATE TABLE ij_athl_tbl (
	athl_id 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (athl_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_first_nm_en_tbl (
	first_nm_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	first_name		VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(first_nm_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_first_nm_tbl (
	athl_key		INT UNSIGNED NOT NULL UNIQUE, 
	first_nm_key	INT UNSIGNED NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (first_nm_key)
		REFERENCES ij_first_nm_en_tbl(first_nm_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_second_nm_en_tbl (
	second_nm_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	second_name		VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(second_nm_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_second_nm_tbl (
	athl_key		INT UNSIGNED NOT NULL UNIQUE, 
	second_nm_key	INT UNSIGNED NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (second_nm_key)
		REFERENCES ij_second_nm_en_tbl(second_nm_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_birthday_en_tbl (
	birthday_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	birthday		TIMESTAMP    DEFAULT 0,
	PRIMARY KEY	(birthday_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_birthday_tbl (
	athl_key		INT UNSIGNED NOT NULL UNIQUE, 
	birthday_key    INT UNSIGNED NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (birthday_key)
		REFERENCES ij_birthday_en_tbl(birthday_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_sex_tbl (
	athl_key	INT UNSIGNED NOT NULL UNIQUE, 
	sex         TINYINT(1) DEFAULT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- create competition layer
CREATE TABLE ij_comp_tbl (
	comp_id 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	champ_key 	INT UNSIGNED NOT NULL,
	PRIMARY KEY	(comp_id),
	FOREIGN KEY (champ_key)
		REFERENCES ij_champ_tbl(champ_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_type_en_tbl (
	type_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	type		VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_type_tbl (
	comp_key	INT UNSIGNED NOT NULL UNIQUE, 
	type_en_key INT UNSIGNED NOT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id),
	FOREIGN KEY (type_en_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_name_en_tbl (
	name_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(255) NOT NULL UNIQUE,
	PRIMARY KEY	(name_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_name_tbl (
	comp_key	INT UNSIGNED NOT NULL UNIQUE, 
	name_en_key INT UNSIGNED NOT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id),
	FOREIGN KEY (name_en_key)
		REFERENCES ij_comp_name_en_tbl(name_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_athl_list_tbl (
	comp_key	INT UNSIGNED NOT NULL, 
	athl_key 	INT UNSIGNED NOT NULL,
	number      INT UNSIGNED NOT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id),
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	UNIQUE INDEX (comp_key, athl_key)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_time_tbl (
	comp_key	INT UNSIGNED NOT NULL UNIQUE, 
	time        TIMESTAMP    DEFAULT 0,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- true(1) is male, false(0) is female
CREATE TABLE ij_comp_sex_tbl (
	comp_key	INT UNSIGNED NOT NULL UNIQUE, 
	sex         TINYINT(1) DEFAULT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- create athlete sub layer for resords
CREATE TABLE ij_personal_bests_tbl (
	athl_key 	INT UNSIGNED NOT NULL,
	type_key	INT UNSIGNED NOT NULL, 
	personal    VARCHAR(255) NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (type_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id),
	UNIQUE INDEX (athl_key, type_key)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_season_bests_tbl (
	athl_key 	INT UNSIGNED NOT NULL,
	type_key	INT UNSIGNED NOT NULL, 
	season    	VARCHAR(255) NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (type_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id),
	UNIQUE INDEX (athl_key, type_key)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_world_records_men_tbl (
	athl_key 	INT UNSIGNED NOT NULL,
	type_key	INT UNSIGNED NOT NULL, 
	record    	VARCHAR(255) NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (type_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id),
	UNIQUE INDEX (athl_key, type_key)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_world_records_women_tbl (
	athl_key 	INT UNSIGNED NOT NULL,
	type_key	INT UNSIGNED NOT NULL, 
	record    	VARCHAR(255) NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (type_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id),
	UNIQUE INDEX (athl_key, type_key)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- create competition sub layer for results
CREATE TABLE ij_track_results_tbl (
	comp_key	INT UNSIGNED NOT NULL, 
	athl_key 	INT UNSIGNED NOT NULL,
	result      VARCHAR(255) NOT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id),
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	UNIQUE INDEX (comp_key, athl_key)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- setup user accounts
DROP USER 'administrator'@'%';
DROP USER 'client'@'%';
FLUSH PRIVILEGES;

CREATE USER 'administrator'@'%' IDENTIFIED BY 'dsEd5f6iR7FReEDu';
CREATE USER 'client'@'%' IDENTIFIED BY 'lFDL9eIU3stT5r54';

GRANT CREATE, SELECT, INSERT, UPDATE, DELETE, DROP
	ON intellijustice.*
	TO 'administrator'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE 
	ON intellijustice.*
	TO 'client'@'%';
FLUSH PRIVILEGES;


-- fill fundamental types
INSERT INTO ij_comp_type_en_tbl(type) 
	VALUES ('100m'), ('200m'), ('300m'), ('400m'), ('600m'), ('800m'),
	       ('1000m'), ('1500m'), ('Mile'), ('2000m'), ('3000m'), ('2 Miles'),
	       ('5000m'), ('10,000m'), ('100mH'), ('110mH'), ('400mH'), 
	       ('2000mSC'), ('3000mSC'), ('High Jump'), ('Pole Vault'), 
	       ('Long Jump'), ('Triple Jump'), ('Shot Put'), ('Discus Throw'),
	       ('Hammer Throw'), ('Javelian Throw'), ('10 km'), ('15 km'), 
	       ('10 Miles'), ('20 km'), ('Half Marathon'), ('Marathon'), 
	       ('3000mW'), ('5000mW'), ('5kmW'), ('10,000mW'), ('10kmW'),
	       ('20,000mW'), ('20kmW'), ('35kmW'), ('50kmW'), ('Heptathlon'), 
	       ('Decathlon'), ('4x100m'), ('4x400m');

-- examples for the common statements


--SET AUTOCOMMIT = 0;
START TRANSACTION;
	SET @second_name = 'Shomova';

	-- reserve athlete id entry
	INSERT INTO ij_athl_tbl() VALUES();
	SET @athl_id = LAST_INSERT_ID();

	-- specify second name
	INSERT IGNORE INTO ij_second_nm_en_tbl(second_name) 
		VALUES (@second_name);
	SELECT @second_nm_en_id := second_nm_en_id 
		FROM ij_second_nm_en_tbl WHERE second_name = @second_name;
	INSERT IGNORE INTO ij_second_nm_tbl(athl_key, second_nm_key) 
		VALUES (@athl_id, @second_nm_en_id);
COMMIT;
--SET AUTOCOMMIT = 1;

-- select an athlete by id
SELECT first_name, second_name
  FROM  ij_first_nm_en_tbl fn_en, ij_first_nm_tbl fn, 
  	ij_second_nm_en_tbl sn_en, ij_second_nm_tbl sn
  WHERE fn.athl_key = 1 AND fn_en.first_nm_en_id = fn.first_nm_key
  	AND sn_en.second_nm_en_id = sn.second_nm_key;