@REM ----------------------------------------------------------------------------
@REM  Copyright 2001-2006 The Apache Software Foundation.
@REM
@REM  Licensed under the Apache License, Version 2.0 (the "License");
@REM  you may not use this file except in compliance with the License.
@REM  You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.
@REM ----------------------------------------------------------------------------
@REM
@REM   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
@REM   reserved.

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
for %%i in ("%~dp0..") do set "BASEDIR=%%~fi"

:repoSetup
set REPO=


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\org\springframework\boot\spring-boot-starter\2.6.11\spring-boot-starter-2.6.11.jar;"%REPO%"\org\springframework\boot\spring-boot\2.6.11\spring-boot-2.6.11.jar;"%REPO%"\org\springframework\spring-context\5.3.22\spring-context-5.3.22.jar;"%REPO%"\org\springframework\spring-expression\5.3.22\spring-expression-5.3.22.jar;"%REPO%"\org\springframework\boot\spring-boot-autoconfigure\2.6.11\spring-boot-autoconfigure-2.6.11.jar;"%REPO%"\org\springframework\boot\spring-boot-starter-logging\2.6.11\spring-boot-starter-logging-2.6.11.jar;"%REPO%"\ch\qos\logback\logback-classic\1.2.11\logback-classic-1.2.11.jar;"%REPO%"\ch\qos\logback\logback-core\1.2.11\logback-core-1.2.11.jar;"%REPO%"\org\apache\logging\log4j\log4j-to-slf4j\2.17.2\log4j-to-slf4j-2.17.2.jar;"%REPO%"\org\apache\logging\log4j\log4j-api\2.17.2\log4j-api-2.17.2.jar;"%REPO%"\org\slf4j\jul-to-slf4j\1.7.36\jul-to-slf4j-1.7.36.jar;"%REPO%"\jakarta\annotation\jakarta.annotation-api\1.3.5\jakarta.annotation-api-1.3.5.jar;"%REPO%"\org\springframework\spring-core\5.3.22\spring-core-5.3.22.jar;"%REPO%"\org\springframework\spring-jcl\5.3.22\spring-jcl-5.3.22.jar;"%REPO%"\org\yaml\snakeyaml\1.29\snakeyaml-1.29.jar;"%REPO%"\org\telegram\telegrambots-spring-boot-starter\6.1.0\telegrambots-spring-boot-starter-6.1.0.jar;"%REPO%"\org\telegram\telegrambots\6.1.0\telegrambots-6.1.0.jar;"%REPO%"\org\telegram\telegrambots-meta\6.1.0\telegrambots-meta-6.1.0.jar;"%REPO%"\com\google\guava\guava\31.1-jre\guava-31.1-jre.jar;"%REPO%"\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar;"%REPO%"\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;"%REPO%"\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar;"%REPO%"\com\google\errorprone\error_prone_annotations\2.11.0\error_prone_annotations-2.11.0.jar;"%REPO%"\com\google\j2objc\j2objc-annotations\1.3\j2objc-annotations-1.3.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-annotations\2.13.3\jackson-annotations-2.13.3.jar;"%REPO%"\com\fasterxml\jackson\jaxrs\jackson-jaxrs-json-provider\2.13.3\jackson-jaxrs-json-provider-2.13.3.jar;"%REPO%"\com\fasterxml\jackson\jaxrs\jackson-jaxrs-base\2.13.3\jackson-jaxrs-base-2.13.3.jar;"%REPO%"\com\fasterxml\jackson\module\jackson-module-jaxb-annotations\2.13.3\jackson-module-jaxb-annotations-2.13.3.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-core\2.13.3\jackson-core-2.13.3.jar;"%REPO%"\com\fasterxml\jackson\core\jackson-databind\2.13.3\jackson-databind-2.13.3.jar;"%REPO%"\org\glassfish\jersey\inject\jersey-hk2\2.35\jersey-hk2-2.35.jar;"%REPO%"\org\glassfish\jersey\core\jersey-common\2.35\jersey-common-2.35.jar;"%REPO%"\org\glassfish\hk2\osgi-resource-locator\1.0.3\osgi-resource-locator-1.0.3.jar;"%REPO%"\org\glassfish\hk2\hk2-locator\2.6.1\hk2-locator-2.6.1.jar;"%REPO%"\org\glassfish\hk2\external\aopalliance-repackaged\2.6.1\aopalliance-repackaged-2.6.1.jar;"%REPO%"\org\glassfish\hk2\hk2-api\2.6.1\hk2-api-2.6.1.jar;"%REPO%"\org\glassfish\hk2\hk2-utils\2.6.1\hk2-utils-2.6.1.jar;"%REPO%"\org\javassist\javassist\3.25.0-GA\javassist-3.25.0-GA.jar;"%REPO%"\org\glassfish\jersey\media\jersey-media-json-jackson\2.35\jersey-media-json-jackson-2.35.jar;"%REPO%"\org\glassfish\jersey\ext\jersey-entity-filtering\2.35\jersey-entity-filtering-2.35.jar;"%REPO%"\org\glassfish\jersey\containers\jersey-container-grizzly2-http\2.35\jersey-container-grizzly2-http-2.35.jar;"%REPO%"\org\glassfish\hk2\external\jakarta.inject\2.6.1\jakarta.inject-2.6.1.jar;"%REPO%"\org\glassfish\grizzly\grizzly-http-server\2.4.4\grizzly-http-server-2.4.4.jar;"%REPO%"\org\glassfish\grizzly\grizzly-http\2.4.4\grizzly-http-2.4.4.jar;"%REPO%"\org\glassfish\grizzly\grizzly-framework\2.4.4\grizzly-framework-2.4.4.jar;"%REPO%"\jakarta\ws\rs\jakarta.ws.rs-api\2.1.6\jakarta.ws.rs-api-2.1.6.jar;"%REPO%"\org\glassfish\jersey\core\jersey-server\2.35\jersey-server-2.35.jar;"%REPO%"\org\glassfish\jersey\core\jersey-client\2.35\jersey-client-2.35.jar;"%REPO%"\jakarta\validation\jakarta.validation-api\2.0.2\jakarta.validation-api-2.0.2.jar;"%REPO%"\org\json\json\20220320\json-20220320.jar;"%REPO%"\commons-io\commons-io\2.11.0\commons-io-2.11.0.jar;"%REPO%"\org\slf4j\slf4j-api\1.7.36\slf4j-api-1.7.36.jar;"%REPO%"\org\flywaydb\flyway-core\8.0.5\flyway-core-8.0.5.jar;"%REPO%"\mysql\mysql-connector-java\8.0.30\mysql-connector-java-8.0.30.jar;"%REPO%"\org\springframework\boot\spring-boot-starter-data-jpa\2.6.11\spring-boot-starter-data-jpa-2.6.11.jar;"%REPO%"\org\springframework\boot\spring-boot-starter-aop\2.6.11\spring-boot-starter-aop-2.6.11.jar;"%REPO%"\org\springframework\spring-aop\5.3.22\spring-aop-5.3.22.jar;"%REPO%"\org\aspectj\aspectjweaver\1.9.7\aspectjweaver-1.9.7.jar;"%REPO%"\org\springframework\boot\spring-boot-starter-jdbc\2.6.11\spring-boot-starter-jdbc-2.6.11.jar;"%REPO%"\com\zaxxer\HikariCP\4.0.3\HikariCP-4.0.3.jar;"%REPO%"\org\springframework\spring-jdbc\5.3.22\spring-jdbc-5.3.22.jar;"%REPO%"\jakarta\transaction\jakarta.transaction-api\1.3.3\jakarta.transaction-api-1.3.3.jar;"%REPO%"\jakarta\persistence\jakarta.persistence-api\2.2.3\jakarta.persistence-api-2.2.3.jar;"%REPO%"\org\hibernate\hibernate-core\5.6.10.Final\hibernate-core-5.6.10.Final.jar;"%REPO%"\org\jboss\logging\jboss-logging\3.4.3.Final\jboss-logging-3.4.3.Final.jar;"%REPO%"\net\bytebuddy\byte-buddy\1.11.22\byte-buddy-1.11.22.jar;"%REPO%"\antlr\antlr\2.7.7\antlr-2.7.7.jar;"%REPO%"\org\jboss\jandex\2.4.2.Final\jandex-2.4.2.Final.jar;"%REPO%"\com\fasterxml\classmate\1.5.1\classmate-1.5.1.jar;"%REPO%"\org\hibernate\common\hibernate-commons-annotations\5.1.2.Final\hibernate-commons-annotations-5.1.2.Final.jar;"%REPO%"\org\glassfish\jaxb\jaxb-runtime\2.3.6\jaxb-runtime-2.3.6.jar;"%REPO%"\org\glassfish\jaxb\txw2\2.3.6\txw2-2.3.6.jar;"%REPO%"\com\sun\istack\istack-commons-runtime\3.0.12\istack-commons-runtime-3.0.12.jar;"%REPO%"\com\sun\activation\jakarta.activation\1.2.2\jakarta.activation-1.2.2.jar;"%REPO%"\org\springframework\data\spring-data-jpa\2.6.6\spring-data-jpa-2.6.6.jar;"%REPO%"\org\springframework\data\spring-data-commons\2.6.6\spring-data-commons-2.6.6.jar;"%REPO%"\org\springframework\spring-orm\5.3.22\spring-orm-5.3.22.jar;"%REPO%"\org\springframework\spring-tx\5.3.22\spring-tx-5.3.22.jar;"%REPO%"\org\springframework\spring-beans\5.3.22\spring-beans-5.3.22.jar;"%REPO%"\org\springframework\spring-aspects\5.3.22\spring-aspects-5.3.22.jar;"%REPO%"\org\projectlombok\lombok\1.18.24\lombok-1.18.24.jar;"%REPO%"\com\konghq\unirest-java\3.13.11\unirest-java-3.13.11.jar;"%REPO%"\org\apache\httpcomponents\httpclient\4.5.13\httpclient-4.5.13.jar;"%REPO%"\org\apache\httpcomponents\httpcore\4.4.15\httpcore-4.4.15.jar;"%REPO%"\org\apache\httpcomponents\httpmime\4.5.13\httpmime-4.5.13.jar;"%REPO%"\org\apache\httpcomponents\httpcore-nio\4.4.15\httpcore-nio-4.4.15.jar;"%REPO%"\org\apache\httpcomponents\httpasyncclient\4.1.5\httpasyncclient-4.1.5.jar;"%REPO%"\commons-codec\commons-codec\1.15\commons-codec-1.15.jar;"%REPO%"\com\google\code\gson\gson\2.8.9\gson-2.8.9.jar;"%REPO%"\org\apache\commons\commons-lang3\3.12.0\commons-lang3-3.12.0.jar;"%REPO%"\org\postgresql\postgresql\42.5.0\postgresql-42.5.0.jar;"%REPO%"\org\checkerframework\checker-qual\3.5.0\checker-qual-3.5.0.jar;"%REPO%"\jakarta\xml\bind\jakarta.xml.bind-api\2.3.3\jakarta.xml.bind-api-2.3.3.jar;"%REPO%"\jakarta\activation\jakarta.activation-api\1.2.2\jakarta.activation-api-1.2.2.jar;"%REPO%"\com\github\m1santhrop\telegrambot\1.0.0\telegrambot-1.0.0.jar

set ENDORSED_DIR=
if NOT "%ENDORSED_DIR%" == "" set CLASSPATH="%BASEDIR%"\%ENDORSED_DIR%\*;%CLASSPATH%

if NOT "%CLASSPATH_PREFIX%" == "" set CLASSPATH=%CLASSPATH_PREFIX%;%CLASSPATH%

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS%  -classpath %CLASSPATH% -Dapp.name="javarushtelegrambot" -Dapp.repo="%REPO%" -Dapp.home="%BASEDIR%" -Dbasedir="%BASEDIR%" com.github.m1santhrop.telegrambot.TelegrambotApplication %CMD_LINE_ARGS%
if %ERRORLEVEL% NEQ 0 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
