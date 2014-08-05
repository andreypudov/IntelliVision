#!/bin/sh
#
# IntelliJustice Intelligent Referee Assistant System 
#
# The MIT License
#
# Copyright 2011-2014 Andrey Pudov.
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in
# all copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
# THE SOFTWARE.
#

#
# Online Athletics database unit validation launcher..
#
# @author    Andrey Pudov        <mail@andreypudov.com>
# @version   0.00.00
# %name      validate.sh
# %date      11:11:00 AM, Jul 20, 2014
#

COMPILR=javac
MACHINE=java
SOURCES=$(find "Unit/" -name "*.java")
USRNAME=''
PASSWRD=''
LIBRARS='.:Unit/:../../../libs/MySQL JDBC/*'

# change the current directory to the scripts directory
cd "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# compile the Unit source code
for file in $SOURCES; do
    $COMPILR -cp "$LIBRARS"  $file
done

# TODO CHANGE THIS LINES
USRNAME=''
PASSWRD=''

# launch unit validation
cd "Unit" && $MACHINE -cp "$LIBRARS" "Unit" $USRNAME $PASSWRD; cd ../

# remove temporary files
for file in $(find "Unit/" -name "*.class"); do
    rm $file
done
