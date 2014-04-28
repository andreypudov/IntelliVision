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
 * %date      05:40:00 PM, Apt 09, 2014
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
  *           yyyy - the calndar year number.
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
  */

var MAX_FILE_LENGTH = 24 * 1024;

/**
 * Initializes file uploading user interface and functionality.
 */
$(function() {
  var $area   = $('.droparea');
  var $form   = $area.find('form');
  var $select = $form.find('.btn-file :file');
  var $label  = $form.find(':text');
  var $alert  = $('.alert-droparea');

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

      readAthletesFile(event);
    });

    $select.on('change', function(event) {
      var input = $(this), 
        count = input.get(0).files ? input.get(0).files.length : 1,
        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');

      $label.val(label);

      readAthletesFile(event);
    });
  } else {
    // failure - file interface is missing
    $area.css('display', 'none');
  }
});

/**
 * Reads athletes data file and returns an array of values.
 *
 * @param event the value of the event.
 */
function readAthletesFile(event) {
  var $area = $('.droparea');
  var files = event.target.files || event.originalEvent.dataTransfer.files;
           
  for (var index = 0; index < files.length; ++index) {
    var file   = files[index];
    var reader = new FileReader();

    if ((!file.name.match('\.csv')) || (!file.type.match('text.*'))) {
      alert('File type is not supported.');
      return;
    }

    if (file.size > MAX_FILE_LENGTH) {
      alert('File is too long.');
      return;
    }

    $area.addClass('droparea-sm');

    reader.onload = function(event) {
      var s = $.csv.toObjects(reader.result);
      console.log(s);
    };

    reader.readAsText(file);
  }
}


