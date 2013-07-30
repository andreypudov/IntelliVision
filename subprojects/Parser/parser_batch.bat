::
:: IntelliJustice Intelligence Referee Assistant System
::
:: The MIT License
::
:: Copyright 2013 Andrey Pudov.
::
:: Permission is hereby granted, free of charge, to any person obtaining a copy
:: of this software and associated documentation files (the "Software"), to deal
:: in the Software without restriction, including without limitation the rights
:: to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
:: copies of the Software, and to permit persons to whom the Software is
:: furnished to do so, subject to the following conditions:
::
:: The above copyright notice and this permission notice shall be included in
:: all copies or substantial portions of the Software.
::
:: THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
:: IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
:: FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
:: AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
:: LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
:: OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
:: THE SOFTWARE.
::

::
:: Executes command-line parser with provided arguments.
::
:: @author    Andrey Pudov        <mail@andreypudov.com>
:: @version   0.00.00
:: %name      parser_batch.bat
:: %date      02:17:00 PM, Jul 23, 2013
::

@ECHO OFF

:: initialize variables
SET available=
SET downloads=
SET currntdir=%~dp0

CD %currntdir:~0,-1%

:: check if Java is available and if not, prompt for download
java -version > NUL 2>&1
IF ERRORLEVEL 1 (
	SET available=No
	ECHO IntelliJustice requires Oracle Java Runtime Environemnt.
	SET /P download=Do you want to download it now? [y/N] 
)

:: start download if confirmed
IF /I "%download%"=="Y" (
	START "DevCon" "http://java.com"
	ECHO.
	ECHO Install the downloaded files and make sure java.exe is in the PATH.
	ECHO Then try again.
)

:: abort if Java is not available yet
IF "%available%"=="No" GOTO:EOF

FOR /f %%f in ('DIR /b report_*') DO (
	ECHO REPORT %%~nf
	java -jar .\parser.jar --method=rus --verbose --source=%%f --report=%%~nf.xlsx
	PAUSE
)
::--proxy=proxy-chain.intel.com:911

:: wait for user interaction on error
:: echo %errorlevel%
IF ERRORLEVEL 127 PAUSE
