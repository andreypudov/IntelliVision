/* 
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2014 Andrey Pudov.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 * Athlete's data file reader interface provides ability to read given CSV text
 * file and validate a content of the file.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      athletes.js
 * %date      05:40:00 PM, Apr 09, 2014
 */

 /**
  * Athlete's data file format explanation.
  *
  * The athletes data file uses CSV file format where fields separated by comma
  * character and records terminated by newlines. All records have the same 
  * number of fields, in the same order. The first record is a file format 
  * header contains column names.
  *
  * The athlete record contains following fields:
  *     - the first name of the athlete.
  *     - the middle name of the athlete.
  *     - the last name of the athlete.
  *     - the first name of the athlete in local language.
  *     - the middle name of the athlete in local language.
  *     - the last name of the athlete in local language.
  *     - the birthday of the athlete in following format:
  *           dd-MM-yyyy
  *       where:
  *           dd   - the calendar day number starting by 01.
  *           MM   - the calendar month number starting by 01.
  *           yyyy - the calendar year number.
  *     - the birthplace of the athlete in following format
  *           "Country, Region, City"
  *       where:
  *           country - the name of the country.
  *           region  - the name of the region of the country.
  *           city    - the name of the city the region.
  *       * double-quotes are required.
  *     - the sex of the athlete where M is male and F is female.
  *     - the first home region in the same format as a birthplace.
  *     - the second home region in the same format as a birthplace.
  *     - the local language name.
  *
  * An athlete record should meet following rules:
  *    - the first name, the middle name and the last name shall be 
  *      provided in English, use official form and can be omitted to pass
  *      transliteration to server.
  *    - the first name, the middle name and the last name fields can be 
  *      omitted to pass transliteration to server.
  *    - at least one of the name variation should be present, localized of English.
  *    - the localized first name, the localized middle name, the localized 
  *      last name shall be provided in the same language as the local language
  *      name value.
  *    - the birthplace, the first and the second home regions values can be
  *      provided in the same language as the local language name value, otherwise
  *      their values shall be provided in English.
  *    - the first and the second home regions can be omitted.
  *    - the local language and localized name fields can be omitted
  *      if localized name fields and geo names for birthplace and home regions
  *      provided in English.
  *
  * A file format header contains following columns in the same order:
  *    - First Name
  *    - Middle Name
  *    - Last Name,
  *    - Localized First Name
  *    - Localized Middle Name
  *    - Localized Last Name
  *    - Birthday
  *    - Birthplace
  *    - Sex
  *    - Home Region 1
  *    - Home Region 2
  *    - Language
  */

/* the maximum length of athlete data file */
var MAX_FILE_LENGTH = 24 * 1024;

/* athlete data file reader */
var reader = new AthleteReader();

/**
 * Initializes file uploading user interface and functionality.
 */
$(function() {
    var $athletes = $('#athletes');
    var $area     = $athletes.find('.droparea');
    var $entry    = $area.find('.dropentry');
    var $select   = $entry.find('.btn-file :file');
    var $label    = $entry.find(':text');
    var $alert    = $athletes.find('.alert-droparea');

    /* check for the various File API support */
    if (window.File && window.FileReader && window.FileList && window.Blob) {
        // success - file interface is present
        $alert.css('display', 'none');

        $area.on('dragover', function(event) {
            event.stopPropagation();
            event.preventDefault();

            $area.addClass('droparea-hover'); 
            event.originalEvent.dataTransfer.dropEffect = 'copy';
        });

        $area.on('dragleave', function(event) {
            event.stopPropagation();
            event.preventDefault();

            $area.removeClass('droparea-hover'); 
        });

        $area.on('drop', function(event) {
            event.stopPropagation();
            event.preventDefault();

            $area.removeClass('droparea-hover');
            $label.val(event.originalEvent.dataTransfer.files[0].name);

            reader.read(event);
        });

        $select.on('change', function(event) {
            var input = $(this), 
                count = input.get(0).files ? input.get(0).files.length : 1,
                label = input.val().replace(/\\/g, '/').replace(/.*\//, '');

            $label.val(label);

            reader.read(event);
        });
    } else {
        // failure - file interface is missing
        $area.css('display', 'none');
    }
});

function AthleteReader() {
    var $athletes = $('#athletes');
    var $area     = $('.droparea');
    var $entry    = $area.find('.dropentry');
    var $select   = $entry.find('.btn-file :file');
    var $list     = $athletes.find('.athletes-list-group');
    var $control  = $athletes.find('.control-panel');

    /**
     * Reads athletes data file and puts the list of athletes in JSON format
     * into athletes data area.
     *
     * @param event the value of the event.
     */
    this.read = function(event) {
        var files = event.target.files || event.originalEvent.dataTransfer.files;

        for (var index = 0; index < files.length; ++index) {
            var file   = files[index];
            var reader = new FileReader();

            if (!file.name.match('\.csv')) {
                togglePopover($('#athletesDropArea .validation-unsupported').text());
                return;
            }

            if (file.size > MAX_FILE_LENGTH) {
                togglePopover($('#athletesDropArea .validation-overlong').text());
                return;
            }

            reader.onload = function(event) {
                /* reinitialize file selection control */
                $select.val('');

                var results = $.parse(reader.result);
                if (results.errors.length !== 0) {
                    var errors  = results.errors;
                    var message = '';

                    for (var type in errors) {
                        /* skip object's length property */
                        if (type === 'length') {
                          continue;
                        }

                        for (var entry in errors[type]) {
                            var error = errors[type][entry];

                            message += ((message.length === 0) ? '' : '\n') 
                                + error['message'] + ' [Index: ' + error['index'] + ', Line: ' + error['line'] + ']';
                        }
                    }

                    togglePopover($('#athletesDropArea .validation-invalid').text() + '. ' + message);
                    return;
                }

                var entry = results.results.rows[0];
                if (!entry) {
                    togglePopover($('#athletesDropArea .validation-missing').text());
                    return;
                }

                /* validates for file consistency */
                if (!('First Name' in entry) || !('Middle Name' in entry) || !('Last Name' in entry)
                    || !('Localized First Name' in entry) || !('Localized Middle Name' in entry) || !('Localized Last Name' in entry)
                    || !('Birthday' in entry) || !('Birthplace' in entry) || !('Sex' in entry)
                    || !('Home Region 1' in entry) || !('Home Region 2' in entry) || !('Language' in entry)) {

                    togglePopover($('#athletesDropArea .validation-invalid').text());
                    return;
                }

                $area.addClass('droparea-sm');

                var $data = $('#athletes\\:athletesDataArea');
                var list  = merge(JSONToList($data.val()), results.results.rows);
                var json  = listToJSON(list);

                $list.find('.list-group-item:not(.athletes-list-group-item-template)').remove();
                $data.val(json);
                validateList(list);
                displayList(list);

                $control.css({'display': 'block'});
                $control.find('.btn:has(#athletes\\:cancel)').click(function() {
                  $area.removeClass('droparea-sm');
                  $list.find('.list-group-item:not(.athletes-list-group-item-template)').remove();
                  $data.val('');

                  $control.css({'display': 'none'});
                });
            };

            reader.readAsText(file);
        }
    };

    /**
     * Adds an entry to the list of athletes.
     *
     * @param entry the array of athlete identification entries.
     */
    function add(entry) {
        var $template = $list.find('.athletes-list-group-item-template');
        var $entry    = $template.clone();
        var $header   = $entry.find('.list-group-item-heading');

        var nameIsEmpty = false;
        $entry.removeClass('athletes-list-group-item-template');

        /* validate for first and second names emptiness */
        if (!(entry['First Name']) || !(entry['Last Name'])) {
            entry['First Name']  = '';
            entry['Middle Name'] = '';
            entry['Last Name']   = '';
            $header.addClass('subheader-only');

            nameIsEmpty = true;
        }

        /* set sex style */
        var $sexField = $header.find((nameIsEmpty) ? 'small' : 'h3');
        var $sexIcon  = $entry.find('.athletes-list-icon');
        $sexField.addClass((entry['Sex'] === 'M') ? 'male' : 'female');
        $sexIcon.addClass((entry['Sex'] === 'M') ? 'male' : 'female');

        /* replace teamplate data with entry values */
        $entry.html($entry.html().replace(/\@\{FirstName\}/g,  normalizeName(entry['First Name'])));
        $entry.html($entry.html().replace(/\@\{MiddleName\}/g, normalizeName(entry['Middle Name'])));
        $entry.html($entry.html().replace(/\@\{LastName\}/g,   normalizeName(entry['Last Name'])));
        $entry.html($entry.html().replace(/\@\{LocalizedFirstName\}/g,  normalizeName(entry['Localized First Name'])));
        $entry.html($entry.html().replace(/\@\{LocalizedMiddleName\}/g, normalizeName(entry['Localized Middle Name'])));
        $entry.html($entry.html().replace(/\@\{LocalizedLastame\}/g,    normalizeName(entry['Localized Last Name'])));
        $entry.html($entry.html().replace(/\@\{Birthday\}/g,    entry['Birthday']));
        $entry.html($entry.html().replace(/\@\{Birthplace\}/g,  entry['Birthplace'].replace(/\,/g, ', ')));
        $entry.html($entry.html().replace(/\@\{HomeRegion1\}/g, entry['Home Region 1'].replace(/\,/g, ', ')));
        $entry.html($entry.html().replace(/\@\{HomeRegion2\}/g, entry['Home Region 2'].replace(/\,/g, ', ')));

        /* validate an athlete entry */
        var $group      = $entry.find('.validation-group');
        var $name       = $group.find('.name');
        var $birthday   = $group.find('.birthday');
        var $birthplace = $group.find('.birthplace');
        var $sex        = $group.find('.sex');
        var $region     = $group.find('.region');
        var $language   = $group.find('.language');
        var $duplicate  = $group.find('.duplicate');

        if (hasError(entry)) {
            var errors = entry.errors;

            if (errors.indexOf('name') !== -1) {
                $entry.addClass('invalid');
                $name.addClass('visible');
            }

            if (errors.indexOf('birthday') !== -1) {
                $entry.addClass('invalid');
                $birthday.addClass('visible');
            }

            if (errors.indexOf('birthplace') !== -1) {
                $entry.addClass('invalid');
                $birthplace.addClass('visible');
            }

            if (errors.indexOf('sex') !== -1) {
                $entry.addClass('invalid');
                $sex.addClass('visible');
            }

            if (errors.indexOf('region') !== -1) {
                $entry.addClass('invalid');
                $region.addClass('visible');
            }

            if (errors.indexOf('language') !== -1) {
                $entry.addClass('invalid');
                $language.addClass('visible');
            }

            if (errors.indexOf('duplicate') !== -1) {
                $entry.addClass('invalid');
                $duplicate.addClass('visible');
            }
        }

        $list.append($entry);
    }

    /**
     * Merges two lists and returns combined result.
     *
     * @param list1 the list to combine.
     * @param list2 the list to combine.
     *
     * @result returns the list contains elements of both given lists.
     */
    function merge(list1, list2) {
        return list1.concat(list2);
    }

    /**
     * Validates an athlete entry and returns validation status.
     *
     * @param {Object} athlete the athlete identification entry.
     *
     * @return true if an athlete entry is valid and false otherwise.
     */
    function validate(athlete) {
        var GEO_REGULAR_EXPRESSION      = /^\D+,\D+,\D+$/;
        var LANGUAGE_REGULAR_EXPRESSION = /^\w{2}$/;

        var status = true;

        if ((!(athlete['First Name']) || !(athlete['Last Name'])) 
            && (!(athlete['Localized First Name']) || !(athlete['Localized Last Name']))) {

            assignError(athlete, 'name');
            status = false;
        }

        if (!isValidDate(athlete['Birthday'])) {
            assignError(athlete, 'birthday');
            status = false;
        }

        if (!athlete['Birthplace'].match(GEO_REGULAR_EXPRESSION)) {
            assignError(athlete, 'birthplace');
            status = false;
        }

        if ((athlete['Sex'] !== 'M') && (athlete['Sex'] !== 'F')) {
            assignError(athlete, 'sex');
            status = false;
        }

        if ((athlete['Home Region 1'] && !athlete['Home Region 1'].match(GEO_REGULAR_EXPRESSION))
            || (athlete['Home Region 2'] && !athlete['Home Region 2'].match(GEO_REGULAR_EXPRESSION))) {

            assignError(athlete, 'region');
            status = false;
        }

        if (athlete['Language'] && !athlete['Language'].match(LANGUAGE_REGULAR_EXPRESSION)) {
            assignError(athlete, 'language');
            status = false;
        }

        return status;
    }

    /**
     * Validates a list of athletes for collisions and return the boolean status.
     *
     * @param {Object} list the list of athletes to display.
     *
     * @return true if list of athletes is valid and false otherwise.
     */
    function validateList(list) {
        var status = true;
        
        for (var index = 0; index < list.length; ++index) {
            var athlete = list[index];

            for (var jndex = index + 1; jndex < list.length; ++jndex) {
                var value = list[jndex];

                if (equals(athlete, value)) {
                    assignError(value, 'duplicate');
                }
            }

            status &= validate(athlete);
        }

        /* validate last entry */
        status &= validate(list[list.length - 1]);

        /* disable submit button on invalid list */
        $control.find('.btn:has(#athletes\\:submit)').attr('disabled', (Boolean(status) === false));
    }

    /**
     * Display given list of athletes.
     *
     * @param list the list of athletes to display.
     */
    function displayList(list) {
        for (var index = 0; index < list.length; ++index) {
            add(list[index]);
        }
    }
    
    /**
     * Updates athletes list view.
     */
    this.updateList = function() {
        var $data = $('#athletes\\:athletesDataArea');
        var list  = JSONToList($data.val());
        
        if (list.length !== 0) {
            $list.find('.list-group-item:not(.athletes-list-group-item-template)').remove();
            validateList(list);
            displayList(list);
        }
    };

    /**
     * Compare atletes instances and returns true if athletes are equals,
     * and false otherwise. Only English name of an athlete is used.
     *
     * @param value1 the athlete instance to compare.
     * @param value2 the athlete instance to compare.
     *
     * @return       true if athletes are equals, and false otherwise.
     */
    function equals(value1, value2) {
        return ((value1['First Name'] === value2['First Name'])
            && (value1['Middle Name'] === value2['Middle Name'])
            && (value1['Last Name'] === value2['Last Name'])
            && (value1['Birthday'] === value2['Birthday'])
            && (value1['Birthplace'] === value2['Birthplace'])
            && (value1['Sex'] === value2['Sex']));
    }

    /**
     * Returns true if an athlete instance is invalid (contains error indicators).
     *
     * @param {Object} athlete the athlete instance to look.
     *
     * @return true if an athlete instance is invalid (contains error indicators).
     */
    function hasError(athlete) {
        return (Boolean((athlete.errors) && (athlete.errors.length > 0)));
    }

    /**
     * Add an error indicator to a given athlete instance.
     *
     * @param {Object} athlete the athlete instance.
     * @param {String} error   the error indicator to add.
     */
    function assignError(athlete, error) {
        /* initialize errors block */
        if (hasError(athlete) === false) {
            athlete.errors = new Array();
        }

        athlete.errors.push(error);
    }

    /**
     * Stringify the list of athletes to JSON format and returns its value.
     *
     * @param  list the list of athletes.
     *
     * @return the JSON string.
     */
    function listToJSON(list) {
        return JSON.stringify(list);
    }

    /**
     * Returns the list of athletes from specified JSON string.
     *
     * @param value the JSON string to translate.
     *
     * @return      the list of athletes or null on error.
     */
    function JSONToList(value) {
        var $alert = $athletes.find('.alert-invalid');
        var list   = new Object();

        if (value.length === 0) {
            return new Array();
        }

        try {
            list = JSON.parse(value);
            return list;
        } catch (e) {
            $('html, body').animate({scrollTop: 0}, 'slow');
            
            $alert.css('display', 'block');
            return new Array();
        }
    }

    /**
     * Validates given string for valid date in format dd-MM-yyyy
     * and returns boolean value.
     *
     * @param {String} date the string to validate.
     *
     * @return true if string is valid date and false otherwise.
     */
    function isValidDate(date) {
        var value = date.match(/^(\d{1,2})-(\d{1,2})-(\d{4})$/);
        if (!value) {
            return false;
        }

        var entry = new Date(value[3], value[2] - 1, value[1]);
        return ((Number(value[1]) === entry.getDate()) 
                && (Number(value[2]) === entry.getMonth() + 1) 
                && (Number(value[3]) === entry.getFullYear()));
    }

    /**
     * Normalizes the name value by changing string to lower case 
     * and first latter to upper case.
     *
     * @param {String} value the string to normilize.
     *
     * @return the normalized string.
     */
    function normalizeName(value) {
        return value.charAt(0).toUpperCase() + value.slice(1).toLowerCase();
    }

    /**
     * Toggles popover for a specific validation function. 
     * 
     * @param {String} message the error message.
     */
    function togglePopover(message) {
        var $group  = $('#athletesDropArea .validation-title');
        var options = {placement: 'top', trigger: 'manual', content: message};

        var $popover    = $group.popover().popover('destroy');
            $popover    = $group.popover(options);

        $popover.popover('show');
    }
}

/**
 * Process AJAX response from the server.
 * 
 * @param {Object} data the AJAX response value.
 */
function processAthleteResponse(data) {
    var status = data.status;
    
    switch (status) {
        case 'begin':
            break;
        case 'complete':
            break;
        case 'success':
            reader.updateList();
            break;
    }
}

/**
 * Process AJAX response exception from the server.
 * 
 * @param {Object} error the AJAX response exception value.
 */
function processAthleteException(error) {
    var $athletes = $('#athletes');
    var $alert    = $athletes.find('.alert-invalid');
    
    $('html, body').animate({scrollTop: 0}, 'slow');
    $alert.css('display', 'block');
}