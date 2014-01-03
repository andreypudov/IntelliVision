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
	birthday	  TIMESTAMP NOT NULL DEFAULT 0 UNIQUE,
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
	user_name   VARCHAR(255) NOT NULL,
	pass_phrase VARCHAR(255) NOT NULL,
	PRIMARY KEY	(user_name)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_accnt_groups_tbl (
	group_name VARCHAR(20)  NOT NULL,
	user_name VARCHAR(255) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE oa_accnt_time_tbl (
	user_name VARCHAR(255) NOT NULL UNIQUE,
	attempt   INT UNSIGNED NOT NULL,
	PRIMARY KEY	(user_name)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- SHA256 is a Password Encryption Algorithm in Application Container
INSERT INTO oa_accnt_user_tbl(user_name, pass_phrase)
	VALUES ('administrator', SHA2('dfgiwr@lk5f$oiu%5e4r', 512)),
	       ('user', SHA2('ljdf5i@4o#p#3q3er', 512));

INSERT INTO oa_accnt_groups_tbl(group_name, user_name)
	VALUES ('administrators', 'administrator'),
	       ('users', 'user');

-- create public stored procedures
DELIMITER //

CREATE PROCEDURE authenticate (user_nm_arg VARCHAR(255),
							   pass_ph_arg VARCHAR(255))
	NOT DETERMINISTIC
    COMMENT 'Authenticates query with specified credentials. Function validates
             the number of authentication attempt and if third attempt is 
             failed, a signal fired for any following
			 queries, even if provided credentials are correct.

			 @param user_nm_arg the name value to authenticate query.
			 @param pass_ph_arg the password value to authenticate query.

             @throws 45000 Authentication failed.'
BEGIN
	DECLARE attempt_val INT UNSIGNED;
    DECLARE count       INT UNSIGNED;

	-- validate routine arguments
	IF ((user_nm_arg IS NULL) 
			OR (pass_ph_arg IS NULL)
			OR (CHAR_LENGTH(user_nm_arg) = 0)
			OR (CHAR_LENGTH(pass_ph_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Authentication failed.';
	END IF;

	-- specified user name doesn't exists
	SET count = (SELECT COUNT(user_name)  
		FROM  oa_accnt_user_tbl
		WHERE user_name = user_nm_arg);
	IF (count = 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Authentication failed.';
	END IF;

	-- the number of already listed attempts
	SET attempt_val = (SELECT attempt 
		FROM  oa_accnt_time_tbl
		WHERE user_name = user_nm_arg);
	IF (attempt_val >= 3) THEN
		-- authentication failed when three and more failed attempts occured
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Authentication failed.';
	ELSEIF (attempt_val IS NULL) THEN
		-- add new user name to validation table
		SET attempt_val = 0;
		INSERT INTO oa_accnt_time_tbl(user_name, attempt) 
			VALUES (user_nm_arg, attempt);
	END IF;

	-- specified cedentials are incorrect
	SET count = (SELECT COUNT(user_name)
		FROM oa_accnt_user_tbl
		WHERE   user_name   = user_nm_arg
			AND pass_phrase = SHA2(pass_ph_arg, 512));
	IF (count = 0) THEN
		UPDATE oa_accnt_time_tbl
			SET   attempt   = attempt_val + 1 
			WHERE user_name = user_nm_arg;
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Authentication failed.';
	END IF;

	-- clear attempts index on success
	UPDATE oa_accnt_time_tbl
		SET   attempt   = 0 
		WHERE user_name = user_nm_arg;
END //

CREATE PROCEDURE add_athlete (first_nm_arg  VARCHAR(255), 
	second_nm_arg VARCHAR(255), birthday_arg TIMESTAMP,
    sex_arg       TINYINT(1),   user_nm_arg  VARCHAR(255),
	pass_ph_arg   VARCHAR(255))
	NOT DETERMINISTIC
	COMMENT 'Adds athlete entry and returns identification number.

			 @param first_nm_arg  the firth name of the athlete.
             @param second_nm_arg the second name of the athlete.
             @param birthday_arg  the birthday of the athlete.
             @param sex_arg       the sex of the athlete (true for male).

			 @param user_nm_arg   the name value to authenticate query.
			 @param pass_ph_arg   the password value to authenticate query.

             @return the database id for the athlete as athlete_indx column.

			 @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.
			 @throws 45000 Athlete entry already exists.'
BEGIN
	DECLARE athlete_indx   INT UNSIGNED;
	DECLARE first_nm_indx  INT UNSIGNED;
	DECLARE second_nm_indx INT UNSIGNED;
	DECLARE birthday_indx  INT UNSIGNED;

	-- validate routine arguments
	IF ((first_nm_arg IS NULL) 
			OR (second_nm_arg IS NULL)
			OR (birthday_arg IS NULL)
			OR (sex_arg IS NULL)
			OR (CHAR_LENGTH(first_nm_arg) = 0)
			OR (CHAR_LENGTH(second_nm_arg) = 0)
			OR ((sex_arg != 0) AND (sex_arg != 1))) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL authenticate (user_nm_arg, pass_ph_arg);
	IF ((SELECT COUNT(*) 
			FROM oa_accnt_groups_tbl
			WHERE   user_name  = user_nm_arg 
				AND group_name = 'administrators') = 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Permissions denied.';
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

	-- set second name id
	SET second_nm_indx = (SELECT second_nm_id
		FROM  oa_second_nm_tbl
		WHERE second_name = second_nm_arg);
	IF (second_nm_indx IS NULL) THEN
		INSERT INTO oa_second_nm_tbl(second_name) 
			VALUES(second_nm_arg);
		SET second_nm_indx = (SELECT last_insert_id());
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
		WHERE   first_nm_key  = first_nm_indx
			AND second_nm_key = second_nm_indx
			AND birthday_key  = birthday_indx
			AND sex           = sex_arg);
	IF (athlete_indx IS NOT NULL) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry already exists.';
	ELSE
		INSERT INTO oa_athl_tbl(first_nm_key, second_nm_key, birthday_key, sex)
			VALUES(first_nm_indx, second_nm_indx, birthday_indx, sex_arg);
		SET athlete_indx = (SELECT last_insert_id());
	END IF;

	SELECT athlete_indx;
END //

CREATE PROCEDURE edit_athlete (athlete_id_arg INT UNSIGNED, 
	first_nm_arg  VARCHAR(255), second_nm_arg VARCHAR(255), 
	birthday_arg  TIMESTAMP,    sex_arg       TINYINT(1),
	user_nm_arg   VARCHAR(255), pass_ph_arg   VARCHAR(255))
	NOT DETERMINISTIC
	COMMENT 'Adds athlete entry and returns identification number.

			 @param athlete_id_arg the database id of the athlete.
			 @param first_nm_arg   the firth name of the athlete.
             @param second_nm_arg  the second name of the athlete.
             @param birthday_arg   the birthday of the athlete.
             @param sex_arg        the sex of the athlete (true for male).

			 @param user_nm_arg    the name value to authenticate query.
			 @param pass_ph_arg    the password value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.
             @throws 45000 Athlete entry doesn\'t exists.
             @throws 45000 Athlete entry the same as requested to change.
             @throws 45000 Athlete entry already exists.'
BEGIN
	DECLARE athlete_indx   INT UNSIGNED;
	DECLARE first_nm_indx  INT UNSIGNED;
	DECLARE second_nm_indx INT UNSIGNED;
	DECLARE birthday_indx  INT UNSIGNED;

	-- validate routine arguments
	IF ((athlete_id_arg IS NULL)
			OR (first_nm_arg IS NULL) 
			OR (second_nm_arg IS NULL)
			OR (birthday_arg IS NULL)
			OR (sex_arg IS NULL)
			OR (CHAR_LENGTH(first_nm_arg) = 0)
			OR (CHAR_LENGTH(second_nm_arg) = 0)
			OR ((sex_arg != 0) AND (sex_arg != 1))) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL authenticate (user_nm_arg, pass_ph_arg);
	IF ((SELECT COUNT(*) 
			FROM oa_accnt_groups_tbl
			WHERE   user_name  = user_nm_arg 
				AND group_name = 'administrators') = 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Permissions denied.';
	END IF;

	IF ((SELECT COUNT(*)
			FROM oa_athl_tbl
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

	-- set second name id
	SET second_nm_indx = (SELECT second_nm_id
		FROM  oa_second_nm_tbl
		WHERE second_name = second_nm_arg);
	IF (second_nm_indx IS NULL) THEN
		INSERT INTO oa_second_nm_tbl(second_name) 
			VALUES(second_nm_arg);
		SET second_nm_indx = (SELECT last_insert_id());
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
		WHERE   first_nm_key  = first_nm_indx
			AND second_nm_key = second_nm_indx
			AND birthday_key  = birthday_indx
			AND sex           = sex_arg);
	IF (athlete_indx = athlete_id_arg) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry the same as requested to change.';
	ELSEIF (athlete_indx IS NOT NULL) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry already exists.';
	END IF;

	-- update athlete information
	UPDATE oa_athl_tbl
		SET first_nm_key  = first_nm_indx,
			second_nm_key = second_nm_indx,
			birthday_key  = birthday_indx,
			sex           = sex_arg
		WHERE athl_id = athlete_id_arg;
END //

CREATE PROCEDURE get_athlete (athlete_id_arg INT UNSIGNED, 
	user_nm_arg VARCHAR(255), pass_ph_arg VARCHAR(255))
	NOT DETERMINISTIC
	COMMENT 'Returns athlete entry for given database id.

			 @param athlete_id_arg the database id of the athlete.

			 @param user_nm_arg    the name value to authenticate query.
			 @param pass_ph_arg    the password value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.
             @throws 45000 Athlete entry doesn\'t exists.'
BEGIN
	DECLARE athlete_id_var INT UNSIGNED;
	DECLARE first_nm_var   VARCHAR(255);
	DECLARE second_nm_var  VARCHAR(255);
	DECLARE birthday_var   TIMESTAMP;
	DECLARE sex_var        TINYINT(1); 

	-- validate routine arguments
	IF (athlete_id_arg IS NULL) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL authenticate (user_nm_arg, pass_ph_arg);
	IF ((SELECT COUNT(*) 
			FROM oa_accnt_groups_tbl
			WHERE   user_name  = user_nm_arg 
				AND group_name = 'administrators') = 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Permissions denied.';
	END IF;

	SELECT a.athl_id, f.first_name, s.second_name, b.birthday, a.sex
		INTO athlete_id_var, first_nm_var, second_nm_var, birthday_var, sex_var
		FROM oa_athl_tbl a
			INNER JOIN oa_first_nm_tbl  f ON f.first_nm_id  = a.first_nm_key
			INNER JOIN oa_second_nm_tbl s ON s.second_nm_id = a.second_nm_key
			INNER JOIN oa_birthday_tbl  b ON b.birthday_id  = a.birthday_key
		WHERE a.athl_id = athlete_id_arg
		LIMIT 1;

	-- select doesn't returns any data
	IF (athlete_id_var IS NULL) THEN
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry doesn\'t exists.';
	END IF;

	SELECT athlete_id_var, first_nm_var, second_nm_var, birthday_var, sex_var;
END //

CREATE PROCEDURE get_athlete_by_name (
    first_nm_arg VARCHAR(255), second_nm_arg VARCHAR(255), 
	user_nm_arg  VARCHAR(255), pass_ph_arg   VARCHAR(255))
	NOT DETERMINISTIC
	COMMENT 'Returns athlete entry for given name.

			 @param first_nm_arg   the firth name of the athlete.
             @param second_nm_arg  the second name of the athlete.

			 @param user_nm_arg    the name value to authenticate query.
			 @param pass_ph_arg    the password value to authenticate query.

             @throws 45000 Invalid argument exception.
             @throws 45000 Permissions denied.
             @throws 45000 Athlete entry doesn\'t exists.'
BEGIN
	DECLARE athlete_id_var INT UNSIGNED;
	DECLARE first_nm_var   VARCHAR(255);
	DECLARE second_nm_var  VARCHAR(255);
	DECLARE birthday_var   TIMESTAMP;
	DECLARE sex_var        TINYINT(1); 

	-- validate routine arguments
	IF ((first_nm_arg IS NULL) 
			OR (second_nm_arg IS NULL)
			OR (CHAR_LENGTH(first_nm_arg) = 0)
			OR (CHAR_LENGTH(second_nm_arg) = 0)) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid argument exception.';
	END IF;

	-- validate authentication and permissions
	CALL authenticate (user_nm_arg, pass_ph_arg);
	IF ((SELECT COUNT(*) 
			FROM oa_accnt_groups_tbl
			WHERE   user_name  = user_nm_arg 
				AND group_name = 'administrators') = 0) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Permissions denied.';
	END IF;

	SELECT a.athl_id, f.first_name, s.second_name, b.birthday, a.sex
		INTO athlete_id_var, first_nm_var, second_nm_var, birthday_var, sex_var
		FROM oa_athl_tbl a
			INNER JOIN oa_first_nm_tbl  f ON f.first_nm_id  = a.first_nm_key
			INNER JOIN oa_second_nm_tbl s ON s.second_nm_id = a.second_nm_key
			INNER JOIN oa_birthday_tbl  b ON b.birthday_id  = a.birthday_key
		WHERE   f.first_name   = first_nm_arg
	        AND s.second_name  = second_nm_arg
		LIMIT 1;

	-- select doesn't returns any data
	IF (athlete_id_var IS NULL) THEN
		 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Athlete entry doesn\'t exists.';
	END IF;

	SELECT athlete_id_var, first_nm_var, second_nm_var, birthday_var, sex_var;
END //

-- --------------------------------------------------------------------------------
-- Returns Online Athletics database version number.
-- --------------------------------------------------------------------------------
CREATE PROCEDURE `getDatabaseVersion` () DETERMINISTIC
BEGIN
	SELECT '0.00.00';
END //

DELIMITER ;