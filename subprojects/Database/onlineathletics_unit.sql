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
-- The project database unit validation script.
--
-- @author    Andrey Pudov        <mail@andreypudov.com>
-- @version   0.00.00
-- %name      onlineathletics_unit.sql
-- %date      06:00:00 PM, Jul 13, 2014
--

USE onlineathletics;

DROP PROCEDURE IF EXISTS u_clear_database;
DROP PROCEDURE IF EXISTS u_add_athlete;
DROP PROCEDURE IF EXISTS u_validate_database;

DROP FUNCTION IF EXISTS u_validate_athlete;

DELIMITER //

CREATE PROCEDURE u_clear_database()
	NOT DETERMINISTIC
	COMMENT 'Clear database content.'
BEGIN
	SET FOREIGN_KEY_CHECKS=0;

	TRUNCATE oa_athl_tbl;
	TRUNCATE oa_first_nm_tbl;
	TRUNCATE oa_middle_nm_tbl;
	TRUNCATE oa_last_nm_tbl;
	TRUNCATE oa_birthday_tbl;

	SET FOREIGN_KEY_CHECKS=1;
END //

CREATE PROCEDURE u_add_athlete()
	DETERMINISTIC
	COMMENT 'Unit validation test for add_athlete procedure.'
BEGIN
	DECLARE novoc INT UNSIGNED;
	SET novoc = (SELECT(CALL geo_get_city_id_by_complete_name('Россия', 'Чувашия', 'Новочебоксарск', 'apudov')));

	SELECT 'Unit u_add_athlete';
	
	CALL u_clear_database();
	CALL add_athlete("Andrey", "Semenovich", "Pudov", "Андрей", "Семёнович", "Пудов", "1989-03-17", 518976, 1, 133, 'apudov');
END //

CREATE FUNCTION u_validate_athlete() RETURNS BOOL
	COMMENT 'Validates an athlete data structure to equality with given pattern.'
BEGIN
	DECLARE value VARCHAR(255);

	SET value = (SELECT CONCAT_WS(',', a.athl_id,
		a.first_nm_key,    a.middle_nm_key,    a.last_nm_key, 
		a.first_nm_lc_key, a.middle_nm_lc_key, a.last_nm_lc_key, a.birthday_key, a.birthplace_key, a.sex, 

		fn.first_nm_id,    fn.first_name,      fn.language_id, 
		mn.middle_nm_id,   mn.middle_name,     mn.language_id, 
		ln.last_nm_id,     ln.last_name,       ln.language_id,

		fnl.first_nm_id,   fnl.first_name,     fnl.language_id, 
		mnl.middle_nm_id,  mnl.middle_name,    mnl.language_id, 
		lnl.last_nm_id,    lnl.last_name,      lnl.language_id,

		bd.birthday_id,    bd.birthday) 
	FROM oa_athl_tbl a
		INNER JOIN oa_first_nm_tbl  fn  ON a.first_nm_key  = fn.first_nm_id 
		INNER JOIN oa_middle_nm_tbl mn  ON a.middle_nm_key = mn.middle_nm_id
		INNER JOIN oa_last_nm_tbl   ln  ON a.last_nm_key   = ln.last_nm_id
		INNER JOIN oa_first_nm_tbl  fnl ON a.first_nm_lc_key  = fnl.first_nm_id
		INNER JOIN oa_middle_nm_tbl mnl ON a.middle_nm_lc_key = mnl.middle_nm_id
		INNER JOIN oa_last_nm_tbl   lnl ON a.last_nm_lc_key   = lnl.last_nm_id
		INNER JOIN oa_birthday_tbl  bd  ON a.birthday_key     = bd.birthday_id);

	RETURN True;
END //

CREATE PROCEDURE u_validate_database()
	NOT DETERMINISTIC
	COMMENT 'Validates database stored procedures and functions.'
BEGIN
	DECLARE status INT UNSIGNED;

	SELECT 'Online Athletics Database Unit Validation Script';

	-- collect validation results
	CALL u_add_athlete();
END //

DELIMITER ;

CALL u_validate_database();