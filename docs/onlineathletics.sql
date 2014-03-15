--
-- IntelliJustice Intelligent Referee Assistant System 
--
-- The MIT License
--
-- Copyright 2009-2014 Andrey Pudov.
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
	first_name  VARCHAR(35)  NOT NULL UNIQUE,
	PRIMARY KEY	(first_nm_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_middle_nm_tbl (
	middle_nm_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	middle_name  VARCHAR(35)  NOT NULL UNIQUE,
	PRIMARY KEY	(middle_nm_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_last_nm_tbl (
	last_nm_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	last_name  VARCHAR(35)  NOT NULL UNIQUE,
	PRIMARY KEY	(last_nm_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_first_nm_lc_tbl (
	first_nm_lc_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	first_name_lc  VARCHAR(35)  NOT NULL,
	language_id    INT UNSIGNED NOT NULL,
	PRIMARY KEY	(first_nm_lc_id),
	UNIQUE KEY (first_name_lc, language_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_middle_nm_lc_tbl (
	middle_nm_lc_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	middle_name_lc  VARCHAR(35)  NOT NULL,
	language_id     INT UNSIGNED NOT NULL,
	PRIMARY KEY	(middle_nm_lc_id),
	UNIQUE KEY (middle_name_lc, language_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_last_nm_lc_tbl (
	last_nm_lc_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	last_name_lc  VARCHAR(35)  NOT NULL,
	language_id   INT UNSIGNED NOT NULL,
	PRIMARY KEY	(last_nm_lc_id),
	UNIQUE KEY (last_name_lc, language_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_birthday_tbl (
	birthday_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	birthday	TIMESTAMP NOT NULL DEFAULT 0 UNIQUE,
	PRIMARY KEY	(birthday_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_athl_tbl (
	athl_id 	     INT UNSIGNED NOT NULL AUTO_INCREMENT,
    -- the name of the athlete in English
	first_nm_key     INT UNSIGNED NOT NULL,
	middle_nm_key    INT UNSIGNED NOT NULL,
	last_nm_key      INT UNSIGNED NOT NULL,
    -- the name of the athlete in local language
	first_nm_lc_key  INT UNSIGNED NOT NULL,
	middle_nm_lc_key INT UNSIGNED NOT NULL,
	last_nm_lc_key   INT UNSIGNED NOT NULL,

	birthday_key     INT UNSIGNED NOT NULL,
	sex              TINYINT(1) NOT NULL DEFAULT 0,
	PRIMARY KEY (athl_id),

	FOREIGN KEY (first_nm_key)     REFERENCES oa_first_nm_tbl(first_nm_id),
	FOREIGN KEY (middle_nm_key)    REFERENCES oa_middle_nm_tbl(middle_nm_id),
	FOREIGN KEY (last_nm_key)      REFERENCES oa_last_nm_tbl(last_nm_id),

	FOREIGN KEY (first_nm_lc_key)  REFERENCES oa_first_nm_lc_tbl(first_nm_lc_id),
	FOREIGN KEY (middle_nm_lc_key) REFERENCES oa_middle_nm_lc_tbl(middle_nm_lc_id),
	FOREIGN KEY (last_nm_lc_key)   REFERENCES oa_last_nm_lc_tbl(last_nm_lc_id),

	FOREIGN KEY (birthday_key)     REFERENCES oa_birthday_tbl(birthday_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8' AUTO_INCREMENT = 100;

-- create geo tables
CREATE TABLE oa_geo_country_tbl (
	geo_nm_id	  INT UNSIGNED NOT NULL UNIQUE,
	name          VARCHAR(200) NOT NULL,
	feature_class VARCHAR(1)   NOT NULL,
	feature_code  VARCHAR(10)  NOT NULL,
	country_code  VARCHAR(2)   NOT NULL,
	admin1_code   VARCHAR(20)  NOT NULL,
	admin2_code   VARCHAR(80)  NOT NULL,
	PRIMARY KEY	(geo_nm_id),
	INDEX (feature_code),
	INDEX (country_code),
	INDEX (admin1_code)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_geo_administration_first_tbl (
	geo_nm_id	 INT UNSIGNED NOT NULL UNIQUE,
	country_code VARCHAR(2)   NOT NULL,
	admin1_code  VARCHAR(20)  NOT NULL,
	PRIMARY KEY	(geo_nm_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_geo_alternative_tbl (
	alt_nm_id     INT UNSIGNED NOT NULL UNIQUE,
	geo_nm_key    INT UNSIGNED NOT NULL,
	language      VARCHAR(7)   NOT NULL,
	alt_name      VARCHAR(200) NOT NULL,
	is_preferred  TINYINT(1)   NOT NULL DEFAULT 0,
	is_short_nm   TINYINT(1)   NOT NULL DEFAULT 0,
	is_colloquial TINYINT(1)   NOT NULL DEFAULT 0,
	is_historic   TINYINT(1)   NOT NULL DEFAULT 0,
	PRIMARY KEY	(alt_nm_id),
	FOREIGN KEY (geo_nm_key)
		REFERENCES oa_geo_country_tbl(geo_nm_id),
	INDEX (alt_name)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

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
	VALUES ('onlineathletics', '', 0),
		   ('apudov',    @apudov_hashed, 0),
	       ('sijbaraev', @sijbaraev_hashed, 0);

INSERT INTO oa_accnt_groups_tbl(group_name)
	VALUES ('db_read'), ('db_write');

SET @oa_key        = (SELECT user_id FROM oa_accnt_user_tbl WHERE user_name = 'onlineathletics');
SET @apudov_key    = (SELECT user_id FROM oa_accnt_user_tbl WHERE user_name = 'apudov');
SET @sijbaraev_key = (SELECT user_id FROM oa_accnt_user_tbl WHERE user_name = 'sijbaraev');

SET @db_read_key   = (SELECT group_id FROM oa_accnt_groups_tbl WHERE group_name = 'db_read');
SET @db_write_key  = (SELECT group_id FROM oa_accnt_groups_tbl WHERE group_name = 'db_write');

INSERT INTO oa_accnt_roles_tbl(user_key, group_key)
	VALUES (@oa_key,        @db_read_key),
	       (@apudov_key,    @db_read_key),
	       (@apudov_key,    @db_write_key),
	       (@sijbaraev_key, @db_read_key);

-- create stored procedures
DELIMITER //

CREATE PROCEDURE add_athlete (first_nm_arg   VARCHAR(35), 
	middle_nm_arg   VARCHAR(35), last_nm_arg      VARCHAR(35), 
	first_nm_lc_arg VARCHAR(35), middle_nm_lc_arg VARCHAR(35),
	last_nm_lc_arg  VARCHAR(35), birthday_arg     TIMESTAMP,
    sex_arg         TINYINT(1),  language_arg     INT UNSIGNED, 
    user_nm_arg      VARCHAR(32))
	NOT DETERMINISTIC
	COMMENT 'Adds athlete entry and returns identification number.

			 @param first_nm_arg     the firth name of the athlete.
			 @param middle_nm_arg    the middle name of the athlete.
             @param last_nm_arg      the last name of the athlete.

             @param first_nm_lc_arg  the firth name of the athlete in local language.
			 @param middle_nm_lc_arg the middle name of the athlete in local language.
             @param last_nm_lc_arg   the last name of the athlete in local language.

             @param birthday_arg     the birthday of the athlete.
             @param sex_arg          the sex of the athlete (true for male).

             @param language_arg     the identifier of the local language.
			 @param user_nm_arg      the name value to authenticate query.

             @return the database id for the athlete as athlete_indx column.

			 @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.
			 @throws 45000 Athlete entry already exists.'
BEGIN
	DECLARE athlete_indx      INT UNSIGNED;

	DECLARE first_nm_indx     INT UNSIGNED;
	DECLARE middle_nm_indx    INT UNSIGNED;
	DECLARE last_nm_indx      INT UNSIGNED;

	DECLARE first_nm_lc_indx  INT UNSIGNED;
	DECLARE middle_nm_lc_indx INT UNSIGNED;
	DECLARE last_nm_lc_indx   INT UNSIGNED;

	DECLARE birthday_indx     INT UNSIGNED;

	-- validate routine arguments
	IF ((first_nm_arg IS NULL) 
		    OR (middle_nm_arg IS NULL)
			OR (last_nm_arg IS NULL)

			OR (first_nm_lc_arg IS NULL)
			OR (middle_nm_lc_arg IS NULL)
			OR (last_nm_lc_arg IS NULL)

			OR (birthday_arg IS NULL)
			OR (sex_arg IS NULL)

			OR (CHAR_LENGTH(first_nm_arg) = 0)
			OR (CHAR_LENGTH(last_nm_arg) = 0)
			OR (CHAR_LENGTH(first_nm_lc_arg) = 0)
			OR (CHAR_LENGTH(last_nm_lc_arg) = 0)

			OR ((sex_arg != 0) AND (sex_arg != 1))
			OR (language_arg is NULL)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL auth_has_group (user_nm_arg, 'db_write');

	-- set first name id
	SET first_nm_indx = (SELECT first_nm_id
		FROM  oa_first_nm_tbl
		WHERE first_name = first_nm_arg);
	IF (first_nm_indx IS NULL) THEN
		INSERT INTO oa_first_nm_tbl(first_name) 
			VALUES(first_nm_arg);
		SET first_nm_indx = (SELECT last_insert_id());
	END IF;

	-- set middle name id
	SET middle_nm_indx = (SELECT middle_nm_id
		FROM  oa_middle_nm_tbl
		WHERE middle_name = middle_nm_arg);
	IF (middle_nm_indx IS NULL) THEN
		INSERT INTO oa_middle_nm_tbl(middle_name) 
			VALUES(middle_nm_arg);
		SET middle_nm_indx = (SELECT last_insert_id());
	END IF;

	-- set last name id
	SET last_nm_indx = (SELECT last_nm_id
		FROM  oa_last_nm_tbl
		WHERE last_name = last_nm_arg);
	IF (last_nm_indx IS NULL) THEN
		INSERT INTO oa_last_nm_tbl(last_name) 
			VALUES(last_nm_arg);
		SET last_nm_indx = (SELECT last_insert_id());
	END IF;

	-- set localized first name id
	SET first_nm_lc_indx = (SELECT first_nm_lc_id
		FROM  oa_first_nm_lc_tbl
		WHERE first_name_lc = first_nm_lc_arg
			AND language_id = language_arg);
	IF (first_nm_lc_indx IS NULL) THEN
		INSERT INTO oa_first_nm_lc_tbl(first_name_lc, language_id) 
			VALUES(first_nm_lc_arg, language_arg);
		SET first_nm_lc_indx = (SELECT last_insert_id());
	END IF;

	-- set localized middle name id
	SET middle_nm_lc_indx = (SELECT middle_nm_lc_id
		FROM  oa_middle_nm_lc_tbl
		WHERE middle_name_lc = middle_nm_lc_arg
			AND language_id  = language_arg);
	IF (middle_nm_lc_indx IS NULL) THEN
		INSERT INTO oa_middle_nm_lc_tbl(middle_name_lc, language_id) 
			VALUES(middle_nm_lc_arg, language_arg);
		SET middle_nm_lc_indx = (SELECT last_insert_id());
	END IF;

	-- set localized last name id
	SET last_nm_lc_indx = (SELECT last_nm_lc_id
		FROM  oa_last_nm_lc_tbl
		WHERE last_name_lc  = last_nm_lc_arg
			AND language_id = language_arg);
	IF (last_nm_lc_indx IS NULL) THEN
		INSERT INTO oa_last_nm_lc_tbl(last_name_lc, language_id) 
			VALUES(last_nm_lc_arg, language_arg);
		SET last_nm_lc_indx = (SELECT last_insert_id());
	END IF;

	-- set localized middle name id
	SET first_nm_lc_indx = (SELECT first_nm_lc_id
		FROM  oa_first_nm_lc_tbl
		WHERE first_name_lc = first_nm_lc_arg
			AND language_id = language_arg);
	IF (first_nm_lc_indx IS NULL) THEN
		INSERT INTO oa_first_nm_lc_tbl(first_name_lc, language_id) 
			VALUES(first_nm_lc_arg, language_arg);
		SET first_nm_lc_indx = (SELECT last_insert_id());
	END IF;

	-- set birthday id
	SET birthday_indx = (SELECT birthday_id
		FROM  oa_birthday_tbl
		WHERE birthday = birthday_arg);
	IF (birthday_indx IS NULL) THEN
		INSERT INTO oa_birthday_tbl(birthday) 
			VALUES(birthday_arg);
		SET birthday_indx = (SELECT last_insert_id());
	END IF;

	-- search for present entry
	-- comparison uses only English name of the athlete
	SET athlete_indx = (SELECT athl_id
		FROM oa_athl_tbl
		WHERE   first_nm_key  = first_nm_indx
			AND middle_nm_key = middle_nm_indx
			AND last_nm_key   = last_nm_indx
			AND birthday_key  = birthday_indx
			AND sex           = sex_arg);
	IF (athlete_indx IS NOT NULL) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry already exists.';
	ELSE
		INSERT INTO oa_athl_tbl(first_nm_key, middle_nm_key, last_nm_key, 
			first_nm_lc_key, middle_nm_lc_key, last_nm_lc_key, birthday_key, sex)
			VALUES(first_nm_indx, middle_nm_indx, last_nm_indx, 
				first_nm_lc_indx, middle_nm_lc_indx, last_nm_lc_indx, birthday_indx, sex_arg);
		SET athlete_indx = (SELECT last_insert_id());
	END IF;

	SELECT athlete_indx;
END //

CREATE PROCEDURE edit_athlete (athlete_id_arg INT UNSIGNED, 
	first_nm_arg     VARCHAR(35),  middle_nm_arg   VARCHAR(35), 
	last_nm_arg      VARCHAR(35),  first_nm_lc_arg VARCHAR(35),
	middle_nm_lc_arg VARCHAR(35),  last_nm_lc_arg  VARCHAR(35),
	birthday_arg     TIMESTAMP,    sex_arg         TINYINT(1),
	language_arg     INT UNSIGNED, user_nm_arg      VARCHAR(32))
	NOT DETERMINISTIC
	COMMENT 'Edit athlete entry.

			 @param athlete_id_arg the database id of the athlete.

			 @param first_nm_arg     the firth name of the athlete.
			 @param middle_nm_arg    the middle name of the athlete.
             @param second_nm_arg    the second name of the athlete.

             @param first_nm_lc_arg  the firth name of the athlete in local language.
			 @param middle_nm_lc_arg the middle name of the athlete in local language.
             @param last_nm_lc_arg   the last name of the athlete in local language.

             @param birthday_arg     the birthday of the athlete.
             @param sex_arg          the sex of the athlete (true for male).

             @param language_arg     the identifier of the local language.
			 @param user_nm_arg      the name value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.
             @throws 45000 Athlete entry doesn\'t exists.
             @throws 45000 Athlete entry the same as requested to change.
             @throws 45000 Athlete entry already exists.'
BEGIN
	DECLARE athlete_indx   INT UNSIGNED;

	DECLARE first_nm_indx  INT UNSIGNED;
	DECLARE middle_nm_indx INT UNSIGNED;
	DECLARE last_nm_indx   INT UNSIGNED;

	DECLARE first_nm_lc_indx  INT UNSIGNED;
	DECLARE middle_nm_lc_indx INT UNSIGNED;
	DECLARE last_nm_lc_indx   INT UNSIGNED;

	DECLARE birthday_indx  INT UNSIGNED;

	-- validate routine arguments
	IF ((athlete_id_arg IS NULL)
			OR (first_nm_arg IS NULL)
			OR (middle_nm_arg IS NULL)
			OR (last_nm_arg IS NULL)

			OR (first_nm_lc_arg IS NULL)
			OR (middle_nm_lc_arg IS NULL)
			OR (last_nm_lc_arg IS NULL)

			OR (birthday_arg IS NULL)
			OR (sex_arg IS NULL)

			OR (CHAR_LENGTH(first_nm_arg) = 0)
			OR (CHAR_LENGTH(last_nm_arg) = 0)
			OR (CHAR_LENGTH(first_nm_lc_arg) = 0)
			OR (CHAR_LENGTH(last_nm_lc_arg) = 0)

			OR ((sex_arg != 0) AND (sex_arg != 1))
			OR (language_arg is NULL)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL auth_has_group (user_nm_arg, 'db_write');

	IF ((SELECT COUNT(*)
			FROM  oa_athl_tbl
			WHERE athl_id = athlete_id_arg) != 1) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry doesn\'t exists.';
	END IF;

	-- set first name id
	SET first_nm_indx = (SELECT first_nm_id
		FROM  oa_first_nm_tbl
		WHERE first_name = first_nm_arg);
	IF (first_nm_indx IS NULL) THEN
		INSERT INTO oa_first_nm_tbl(first_name) 
			VALUES(first_nm_arg);
		SET first_nm_indx = (SELECT last_insert_id());
	END IF;

	-- set middle name id
	SET middle_nm_indx = (SELECT middle_nm_id
		FROM  oa_middle_nm_tbl
		WHERE middle_name = middle_nm_arg);
	IF (middle_nm_indx IS NULL) THEN
		INSERT INTO oa_middle_nm_tbl(middle_name) 
			VALUES(middle_nm_arg);
		SET middle_nm_indx = (SELECT last_insert_id());
	END IF;

	-- set last name id
	SET last_nm_indx = (SELECT last_nm_id
		FROM  oa_last_nm_tbl
		WHERE last_name = last_nm_arg);
	IF (last_nm_indx IS NULL) THEN
		INSERT INTO oa_last_nm_tbl(last_name) 
			VALUES(last_nm_arg);
		SET last_nm_indx = (SELECT last_insert_id());
	END IF;

	-- set localized first name id
	SET first_nm_lc_indx = (SELECT first_nm_lc_id
		FROM  oa_first_nm_lc_tbl
		WHERE first_name_lc = first_nm_lc_arg
			AND language_id = language_arg);
	IF (first_nm_lc_indx IS NULL) THEN
		INSERT INTO oa_first_nm_lc_tbl(first_name_lc, language_id) 
			VALUES(first_nm_lc_arg, language_arg);
		SET first_nm_lc_indx = (SELECT last_insert_id());
	END IF;

	-- set localized middle name id
	SET middle_nm_lc_indx = (SELECT middle_nm_lc_id
		FROM  oa_middle_nm_lc_tbl
		WHERE middle_name_lc = middle_nm_lc_arg
			AND language_id  = language_arg);
	IF (middle_nm_lc_indx IS NULL) THEN
		INSERT INTO oa_middle_nm_lc_tbl(middle_name_lc, language_id) 
			VALUES(middle_nm_lc_arg, language_arg);
		SET middle_nm_lc_indx = (SELECT last_insert_id());
	END IF;

	-- set localized last name id
	SET last_nm_lc_indx = (SELECT last_nm_lc_id
		FROM  oa_last_nm_lc_tbl
		WHERE last_name_lc  = last_nm_lc_arg
			AND language_id = language_arg);
	IF (last_nm_lc_indx IS NULL) THEN
		INSERT INTO oa_last_nm_lc_tbl(last_name_lc, language_id) 
			VALUES(last_nm_lc_arg, language_arg);
		SET last_nm_lc_indx = (SELECT last_insert_id());
	END IF;

	-- set birthday id
	SET birthday_indx = (SELECT birthday_id
		FROM  oa_birthday_tbl
		WHERE birthday = birthday_arg);
	IF (birthday_indx IS NULL) THEN
		INSERT INTO oa_birthday_tbl(birthday) 
			VALUES(birthday_arg);
		SET birthday_indx = (SELECT last_insert_id());
	END IF;

	-- search for present entry
	SET athlete_indx = (SELECT athl_id
		FROM oa_athl_tbl
		WHERE   first_nm_key     = first_nm_indx
			AND middle_nm_key    = middle_nm_indx
			AND last_nm_key      = last_nm_indx

			AND first_nm_lc_key  = first_nm_lc_indx
			AND middle_nm_lc_key = middle_nm_lc_indx
			AND last_nm_lc_key   = last_nm_lc_indx

			AND birthday_key     = birthday_indx
			AND sex              = sex_arg);
	IF (athlete_indx = athlete_id_arg) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry the same as requested to change.';
	ELSEIF (athlete_indx IS NOT NULL) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry already exists.';
	END IF;

	-- update athlete information
	UPDATE oa_athl_tbl
		SET first_nm_key     = first_nm_indx,
			middle_nm_key    = middle_nm_indx,
			last_nm_key      = last_nm_indx,

			first_nm_lc_key  = first_nm_lc_indx,
			middle_nm_lc_key = middle_nm_lc_indx,
			last_nm_lc_key   = last_nm_lc_indx,

			birthday_key     = birthday_indx,
			sex              = sex_arg
		WHERE athl_id = athlete_id_arg;
END //

CREATE PROCEDURE get_athlete (athlete_id_arg INT UNSIGNED, user_nm_arg VARCHAR(32))
	NOT DETERMINISTIC
	COMMENT 'Returns athlete entry for given database id.

			 @param athlete_id_arg the database id of the athlete.
			 @param user_nm_arg    the name value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.
             @throws 45000 Athlete entry doesn\'t exists.'
BEGIN
	DECLARE athlete_id_var   INT UNSIGNED;

	DECLARE first_nm_var     VARCHAR(35);
	DECLARE middle_nm_var    VARCHAR(35);
	DECLARE last_nm_var      VARCHAR(35);

	DECLARE first_nm_lc_var  VARCHAR(35);
	DECLARE middle_nm_lc_var VARCHAR(35);
	DECLARE last_nm_lc_var   VARCHAR(35);

	DECLARE birthday_var     TIMESTAMP;
	DECLARE sex_var          TINYINT(1);
	DECLARE language_var     INT UNSIGNED;

	-- validate routine arguments
	IF (athlete_id_arg IS NULL) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL auth_has_group (user_nm_arg, 'db_read');

	SELECT a.athl_id, f.first_name, m.middle_name, l.last_name,
		fl.first_name_lc, ml.middle_name_lc, ll.last_name_lc, 
		b.birthday, a.sex, fl.language_id 
		INTO athlete_id_var, first_nm_var, middle_nm_var, last_nm_var, 
			first_nm_lc_var, middle_nm_lc_var, last_nm_lc_var,
			birthday_var, sex_var, language_var
		FROM oa_athl_tbl a
			INNER JOIN oa_first_nm_tbl  f ON f.first_nm_id  = a.first_nm_key
			INNER JOIN oa_middle_nm_tbl m ON m.middle_nm_id = a.middle_nm_key
			INNER JOIN oa_last_nm_tbl   l ON l.last_nm_id   = a.last_nm_key

			INNER JOIN oa_first_nm_lc_tbl  fl ON fl.first_nm_lc_id  = a.first_nm_lc_key
			INNER JOIN oa_middle_nm_lc_tbl ml ON ml.middle_nm_lc_id = a.middle_nm_lc_key
			INNER JOIN oa_last_nm_lc_tbl   ll ON ll.last_nm_lc_id   = a.last_nm_lc_key

			INNER JOIN oa_birthday_tbl b ON b.birthday_id   = a.birthday_key
		WHERE a.athl_id = athlete_id_arg
		LIMIT 1;

	-- select doesn't returns any data
	IF (athlete_id_var IS NULL) THEN
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry doesn\'t exists.';
	END IF;

	SELECT athlete_id_var, first_nm_var, middle_nm_var, last_nm_var, 
		first_nm_lc_var, middle_nm_lc_var, last_nm_lc_var, birthday_var, sex_var, 
		language_var;
END //

CREATE PROCEDURE get_athlete_by_name (first_nm_arg VARCHAR(35),
	middle_nm_arg VARCHAR(35), last_nm_arg VARCHAR(35), 
	user_nm_arg   VARCHAR(32))
	NOT DETERMINISTIC
	COMMENT 'Returns athlete entry for given name.

			 @param first_nm_arg  the first name of the athlete.
			 @param middle_nm_arg the middle name of the athlete.
             @param last_nm_arg   the last name of the athlete.

			 @param user_nm_arg   the name value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.
             @throws 45000 Athlete entry doesn\'t exists.'
BEGIN
	DECLARE athlete_id_var   INT UNSIGNED;

	DECLARE first_nm_var     VARCHAR(35);
	DECLARE middle_nm_var    VARCHAR(35);
	DECLARE last_nm_var      VARCHAR(35);

	DECLARE first_nm_lc_var  VARCHAR(35);
	DECLARE middle_nm_lc_var VARCHAR(35);
	DECLARE last_nm_lc_var   VARCHAR(35);

	DECLARE birthday_var     TIMESTAMP;
	DECLARE sex_var          TINYINT(1);
	DECLARE language_var     INT UNSIGNED;

	-- validate routine arguments
	IF ((first_nm_arg IS NULL)
			OR (middle_nm_arg IS NULL)
			OR (last_nm_arg IS NULL)
			OR (CHAR_LENGTH(first_nm_arg) = 0)
			OR (CHAR_LENGTH(last_nm_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL auth_has_group (user_nm_arg, 'db_read');

	SELECT a.athl_id, f.first_name, m.middle_name, l.last_name,
		fl.first_name_lc, ml.middle_name_lc, ll.last_name_lc, 
		b.birthday, a.sex, fl.language_id 
		INTO athlete_id_var, first_nm_var, middle_nm_var, last_nm_var, 
			first_nm_lc_var, middle_nm_lc_var, last_nm_lc_var,
			birthday_var, sex_var, language_var
		FROM oa_athl_tbl a
			INNER JOIN oa_first_nm_tbl  f ON f.first_nm_id  = a.first_nm_key
			INNER JOIN oa_middle_nm_tbl m ON m.middle_nm_id = a.middle_nm_key
			INNER JOIN oa_last_nm_tbl   l ON l.last_nm_id   = a.last_nm_key

			INNER JOIN oa_first_nm_lc_tbl  fl ON fl.first_nm_lc_id  = a.first_nm_lc_key
			INNER JOIN oa_middle_nm_lc_tbl ml ON ml.middle_nm_lc_id = a.middle_nm_lc_key
			INNER JOIN oa_last_nm_lc_tbl   ll ON ll.last_nm_lc_id   = a.last_nm_lc_key

			INNER JOIN oa_birthday_tbl b ON b.birthday_id   = a.birthday_key
		WHERE f.first_name    = first_nm_arg
			AND m.middle_name = middle_nm_arg
			AND l.last_name   = last_nm_arg
		LIMIT 1;

	-- select doesn't returns any data
	IF (athlete_id_var IS NULL) THEN
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry doesn\'t exists.';
	END IF;

	SELECT athlete_id_var, first_nm_var, middle_nm_var, last_nm_var, 
		first_nm_lc_var, middle_nm_lc_var, last_nm_lc_var, birthday_var, sex_var, 
		language_var;
END //

-- geo layer

CREATE PROCEDURE geo_country_list (
	language_arg VARCHAR(7), user_nm_arg VARCHAR(32))
	NOT DETERMINISTIC
    COMMENT 'Returns a list of countries using given language. 
             If a translationg to given language does not provided,
             an empty value returns.

    		 @param language_arg the language to use in lookup.
			 @param user_nm_arg  the name value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.'
BEGIN
	-- validate routine arguments
	IF ((language_arg IS NULL)
			OR (CHAR_LENGTH(language_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL auth_has_group (user_nm_arg, 'db_read');

	-- return a list of countries
	IF (language_arg = 'EN') THEN
		SELECT c.geo_nm_id, c.country_code, c.name
			FROM oa_geo_country_tbl c
			WHERE c.feature_code = 'PCLI'
		GROUP BY (c.geo_nm_id)
		ORDER BY (c.name);
	ELSE
		SELECT c.geo_nm_id, c.country_code, a.alt_name
			FROM oa_geo_country_tbl c
				INNER JOIN oa_geo_alternative_tbl a ON a.geo_nm_key = c.geo_nm_id
			WHERE c.feature_code  = 'PCLI'
				AND a.language    = language_arg
				AND a.is_historic = 0
		GROUP BY (c.geo_nm_id)
		ORDER BY (a.alt_name);
	END IF;
END //

CREATE PROCEDURE geo_region_list (country_id_arg INT UNSIGNED,
	language_arg VARCHAR(7), user_nm_arg VARCHAR(32))
	NOT DETERMINISTIC
    COMMENT 'Returns a list of regions for a given country using given language.

    		 @param country_id_arg the geo id of the country to look.
    		 @param language_arg   the language to use in lookup.
			 @param user_nm_arg    the name value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.'
BEGIN
	DECLARE country_code_var VARCHAR(2);

	-- validate routine arguments
	IF ((country_id_arg IS NULL)
			OR (language_arg IS NULL)
			OR (CHAR_LENGTH(language_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL auth_has_group (user_nm_arg, 'db_read');

	SELECT c.country_code
		INTO country_code_var
		FROM oa_geo_country_tbl c
			INNER JOIN oa_geo_alternative_tbl a ON a.geo_nm_key = c.geo_nm_id
		WHERE c.feature_code  = 'PCLI'
			AND c.geo_nm_id   = country_id_arg
		LIMIT 1; 

	-- return a list of countries
	IF (language_arg = 'EN') THEN
		SELECT c.geo_nm_id, c.name
			FROM oa_geo_administration_first_tbl ad
				INNER JOIN oa_geo_country_tbl    c  ON c.geo_nm_id = ad.geo_nm_id
			WHERE ad.country_code = country_code_var
		GROUP BY (c.geo_nm_id)
		ORDER BY (c.name);
	ELSE
		SELECT source.geo_nm_id, source.alt_name 
			FROM (
				SELECT c.geo_nm_id, al.alt_name, al.language, al.is_preferred, al.is_short_nm, al.is_historic
					FROM oa_geo_administration_first_tbl  ad
						INNER JOIN oa_geo_country_tbl     c  ON c.geo_nm_id   = ad.geo_nm_id
						INNER JOIN oa_geo_alternative_tbl al ON al.geo_nm_key = ad.geo_nm_id
					WHERE ad.country_code = country_code_var
					ORDER BY
						al.language = language_arg DESC, 
						al.language = '' DESC,
						al.is_preferred  DESC,
						al.is_short_nm
			) AS source
		GROUP BY (source.geo_nm_id)
		ORDER BY (source.alt_name);
	END IF;
END //

CREATE PROCEDURE geo_city_list (region_id_arg INT UNSIGNED,
	language_arg VARCHAR(7), user_nm_arg VARCHAR(32))
	NOT DETERMINISTIC
    COMMENT 'Returns a list of countries using given language.

    		 @param region_id_arg the geo id of the region to look.
    		 @param language_arg  the language to use in lookup.
			 @param user_nm_arg   the name value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.'
BEGIN
	DECLARE country_code_var VARCHAR(2);
	DECLARE region_code_var  VARCHAR(20);

	-- validate routine arguments
	IF ((region_id_arg IS NULL)
			OR (language_arg IS NULL)
			OR (CHAR_LENGTH(language_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL auth_has_group (user_nm_arg, 'db_read');

	SELECT country_code, admin1_code 
		INTO  country_code_var, region_code_var
		FROM  oa_geo_administration_first_tbl
		WHERE geo_nm_id = region_id_arg
		LIMIT 1; 

	-- return a list of countries
	IF (language_arg = 'EN') THEN
		SELECT geo_nm_id, name
			FROM oa_geo_country_tbl
			WHERE feature_class  = 'P'
				AND country_code = country_code_var
				AND admin1_code  = region_code_var
		GROUP BY (geo_nm_id)
		ORDER BY (name);
	ELSE
		SELECT source.geo_nm_id, source.alt_name 
			FROM (
				SELECT c.geo_nm_id, al.alt_name, al.language, al.is_preferred, al.is_short_nm, al.is_historic
					FROM oa_geo_country_tbl c
						INNER JOIN oa_geo_alternative_tbl al ON al.geo_nm_key = c.geo_nm_id
					WHERE c.feature_class  = 'P'
						AND c.country_code = country_code_var
						AND c.admin1_code  = region_code_var
					ORDER BY 
						al.language = language_arg DESC, 
						al.language = '' DESC,
						al.is_preferred  DESC,
						al.is_short_nm
			) AS source
		GROUP BY (source.geo_nm_id)
		ORDER BY (source.alt_name);
	END IF;
END //

CREATE PROCEDURE geo_get_city_list_by_name (
	city_nm_arg VARCHAR(200), user_nm_arg VARCHAR(32))
	NOT DETERMINISTIC
    COMMENT 'Returns a list of possible city names including complete city name.

    		 @param city_nm_arg the name of the city to look.
			 @param user_nm_arg the name value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.'
BEGIN
	DECLARE geo_id_var  INT UNSIGNED;

	DECLARE done INT DEFAULT FALSE;
	DECLARE city_cr_var CURSOR FOR 
		SELECT geo_nm_key 
			FROM oa_geo_alternative_tbl 
			WHERE alt_name = city_nm_arg;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

	-- validate routine arguments
	IF ((city_nm_arg IS NULL)
			OR (CHAR_LENGTH(city_nm_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL auth_has_group (user_nm_arg, 'db_read');

	OPEN city_cr_var;

	city_loop: LOOP
		FETCH city_cr_var INTO geo_id_var;
		IF (done) THEN
			LEAVE city_loop;
		END IF;


	END LOOP city_loop;

	CLOSE city_cr_var;
END //

-- authentication layer

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