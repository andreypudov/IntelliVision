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
	name     	VARCHAR(255) NOT NULL,
	PRIMARY KEY	(champ_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- create athlete layer
CREATE TABLE ij_athl_tbl (
	athl_id 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PRIMARY KEY (athl_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_first_nm_en_tbl (
	first_nm_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	first_name		VARCHAR(255) NOT NULL,
	PRIMARY KEY	(first_nm_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_first_nm_tbl (
	athl_key		INT UNSIGNED NOT NULL, 
	first_nm_key	INT UNSIGNED NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (first_nm_key)
		REFERENCES ij_first_nm_en_tbl(first_nm_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_second_nm_en_tbl (
	second_nm_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	second_name		VARCHAR(255) NOT NULL,
	PRIMARY KEY	(second_nm_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_second_nm_tbl (
	athl_key		INT UNSIGNED NOT NULL, 
	second_nm_key	INT UNSIGNED NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (second_nm_key)
		REFERENCES ij_second_nm_en_tbl(second_nm_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_birthday_en_tbl (
	birthday_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	birthday		VARCHAR(255) NOT NULL,
	PRIMARY KEY	(birthday_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_birthday_tbl (
	athl_key		INT UNSIGNED NOT NULL, 
	birthday_key	INT UNSIGNED NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (birthday_key)
		REFERENCES ij_birthday_en_tbl(birthday_en_id)
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
	type		VARCHAR(255) NOT NULL,
	PRIMARY KEY	(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_type_tbl (
	comp_key	INT UNSIGNED NOT NULL, 
	type_en_key INT UNSIGNED NOT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id),
	FOREIGN KEY (type_en_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_name_en_tbl (
	name_en_id	INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name		VARCHAR(255) NOT NULL,
	PRIMARY KEY	(name_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_comp_name_tbl (
	comp_key	INT UNSIGNED NOT NULL, 
	name_en_key INT UNSIGNED NOT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id),
	FOREIGN KEY (name_en_key)
		REFERENCES ij_comp_name_en_tbl(name_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_athl_list_tbl (
	comp_key	INT UNSIGNED NOT NULL, 
	athl_key 	INT UNSIGNED NOT NULL,
	number      INT UNSIGNED NOT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id),
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- create athlete sub layer for resords
CREATE TABLE ij_personal_bests_tbl (
	athl_key 	INT UNSIGNED NOT NULL,
	type_key	INT UNSIGNED NOT NULL, 
	personal    VARCHAR(255) NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (type_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_season_bests_tbl (
	athl_key 	INT UNSIGNED NOT NULL,
	type_key	INT UNSIGNED NOT NULL, 
	season    	VARCHAR(255) NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (type_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_world_records_men_tbl (
	athl_key 	INT UNSIGNED NOT NULL,
	type_key	INT UNSIGNED NOT NULL, 
	record    	VARCHAR(255) NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (type_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

CREATE TABLE ij_world_records_women_tbl (
	athl_key 	INT UNSIGNED NOT NULL,
	type_key	INT UNSIGNED NOT NULL, 
	record    	VARCHAR(255) NOT NULL,
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id),
	FOREIGN KEY (type_key)
		REFERENCES ij_comp_type_en_tbl(type_en_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';

-- create competition sub layer for results
CREATE TABLE ij_track_results_tbl (
	comp_key	INT UNSIGNED NOT NULL, 
	athl_key 	INT UNSIGNED NOT NULL,
	result      VARCHAR(255) NOT NULL,
	FOREIGN KEY (comp_key)
		REFERENCES ij_comp_tbl(comp_id),
	FOREIGN KEY (athl_key)
		REFERENCES ij_athl_tbl(athl_id)
) ENGINE = InnoDB DEFAULT CHARSET = 'utf8';