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
 * The collection of validation functions used in client side validation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      validation.js
 * %date      11:40:00 AM, Jan 06, 2014
 */

/* the list of commonly used constants */
var CONTACT_NAME_MAX_LENGTH    = 255;
var CONTACT_EMAIL_MAX_LENGTH   = 255;
var CONTACT_MESSAGE_MAX_LENGTH = 4096;

var MESSAGE_MIN_OFFSET = 200;

/* the element top offest used in scroll animation */
//var FOCUS_OFFSET = 24;

var VALIDATION_POPOVER_OPTIONS = {
    placement: function(context, source) {
        var position = $(source).offset();

        return (position.left > MESSAGE_MIN_OFFSET) 
            ? 'left'
            : 'top';
    },

    trigger: 'manual'
};

/**
 * Validates contacts form and returns true if form data is correct, and false
 * otherwise.
 * 
 * @returns {Boolean} the validation status.
 */
function validateContactForm() {
    var $nameField    = $('#contactForm\\:contactInputName');
    var $emailField   = $('#contactForm\\:contactInputEmail');
    var $textArea     = $('#contactForm\\:contactTextArea');
    var $submitButton = $('#contactForm\\:submitButton');
    
    var status =  validateText($nameField, CONTACT_NAME_MAX_LENGTH);
    status     &= validateEmail($emailField);
    status     &= validateText($textArea, CONTACT_MESSAGE_MAX_LENGTH);
    status     =  Boolean(status);
    
    /* $('html, body').animate({
        scrollTop: $($group).offset().top - FOCUS_OFFSET
    }); */
    
    $submitButton.parent().toggleClass('disabled', status);

    return status;
}

/**
 * Validates a value of input text field and if the value is emptry sets error
 * style class and returns false, otherwise return true.
 * 
 * @param {Element} field   the input text field.
 * @param {Number}  length  the maximumlength of the text vfield alue.
 * 
 * @returns {Boolean}       true if text field value is valid, false otherwise.
 */
function validateText(field, length) {
    var $field = $(field);
    var $group = $field.parent();     
    var style  = ($field.prop('tagName').toUpperCase() === 'INPUT' 
        ? 'input-group has-error' : 'has-error');

    return !(stringIsEmpty($field, $group, style, getMessage($group, '.validation-missing'))
          || stringIsOverlong($field, $group, length, style, getMessage($group, '.validation-overlong')));
}

/**
 * Validates given text field, and returns true if a text field value is 
 * a correct email address. Otherwise returns false.
 * 
 * @param {Element} field the input text field.
 * 
 * @returns {Boolean}     true if text field value is valid, false otherwise.
 */
function validateEmail(field) {
    var $field = $(field);
    var $group = $field.parent();
    var  style = 'input-group has-error';
    
    return !(stringIsEmpty($field, $group, style, getMessage($group, '.validation-missing'))
          || stringIsOverlong($field, $group, CONTACT_EMAIL_MAX_LENGTH, style, 
                             getMessage($group, '.validation-overlong')));
}

/**
 * Returns the message for a specific selector inside a form validation  group.
 * 
 * @param {Element} $groupd  the form group.
 * @param {String}  selector the CSS selector of the message.
 * 
 * @returns {String} the message text.
 */
function getMessage($groupd, selector) {
    return $groupd.children(selector).text();
}

/**
 * Validates specified input field and returns true if input value is empty.
 * 
 * @param {Element} $field  the input field to validate.
 * @param {Element} $group  the form group where input field from.
 * @param {String}  style   the error style for the form group.
 * @param {String}  message the error message.
 * 
 * @returns {Boolean}       true if value is empty and false otherwise.
 */
function stringIsEmpty($field, $group, style, message) {
    var status  = ($field.val() === '');
    var options = VALIDATION_POPOVER_OPTIONS;
        options.content = message;
    var $popover    = $field.popover().popover('destroy');
        $popover    = $field.popover(options);
    
    $group.toggleClass(style, status);    
    $popover.popover(status ? 'show' : 'hide');
    
    return status;
}

/**
 * Validates specified input field and returns true if input value string is
 * longer than specified length.
 * 
 * @param {Element} $field  the input field to validate.
 * @param {Element} $group  the form group where input field from.
 * @param {Number}  length  the maximum allowed length.
 * @param {String}  style   the error style for the form group.
 * @param {String}  message the error message.
 * 
 * @returns {Boolean}       true if value is longer than specified length, 
 *                          and false otherwise.
 */
function stringIsOverlong($field, $group, length, style, message) {
    var status   = ($field.val().length > length);
    var options  = VALIDATION_POPOVER_OPTIONS;
        options.content = message;
    var $popover    = $field.popover().popover('destroy');
        $popover    = $field.popover(options);
    
    $group.toggleClass(style, status);    
    $popover.popover(status ? 'show' : 'hide');
    
    return status;
}