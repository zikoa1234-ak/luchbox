@echo off
echo ===============================================
echo     Java Version Checker for PantryBox
echo ===============================================
echo.

echo Checking Java versions...

where java >nul 2>nul
if %errorlevel% neq 0 (
    echo [✗] Java not found in PATH
    goto :install_java
)

echo.
echo Available Java versions:
echo ------------------------

for /f "tokens=*" %%i in ('where java') do (
    echo Found: %%i
    "%%i" -version 2>&1 | findstr "version"
)

echo.
echo Current JAVA_HOME: %JAVA_HOME%
echo.

echo Recommended setup for PantryBox:
echo 1. Install JDK 17 (recommended) OR use JDK 21
echo 2. Set JAVA_HOME to JDK 17 folder
echo 3. Ensure Android Studio uses correct JDK
echo.

echo To set JAVA_HOME (example):
echo setx JAVA_HOME "C:\Program Files\Java\jdk-17"
echo.

echo To check Android Studio JDK:
echo File → Project Structure → SDK Location → JDK Location
echo.

echo Press any key to continue...
pause >nul

:install_java
echo.
echo Java installation options:
echo 1. Install JDK 17 (Recommended) - https://adoptium.net/temurin/releases/?version=17
echo 2. Install JDK 21 (Current) - https://adoptium.net/temurin/releases/?version=21
echo 3. Use Android Studio Embedded JDK
echo.

echo After installing, set JAVA_HOME environment variable:
echo SETX JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-17.0.10.7-hotspot"
echo.

echo Then restart Android Studio.
pause