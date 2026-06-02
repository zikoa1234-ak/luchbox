@echo off
echo ===============================================
echo     Build PantryBox WITHOUT Android Studio
echo ===============================================
echo.

echo This script builds the APK directly using Gradle.
echo No Android Studio needed!
echo.

setlocal

REM Check Java
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo [ERROR] Java not found in PATH
    echo Please install JDK 17 or 21
    pause
    exit /b 1
)

REM Check if in project directory
if not exist "app\build.gradle.kts" (
    echo [ERROR] Not in PantryBox project directory
    echo Please run from: C:\Users\ak-47\Downloads\luchbox-main\luchbox-main\PantryBox
    pause
    exit /b 1
)

echo [1/4] Cleaning previous builds...
call gradlew clean

echo [2/4] Downloading dependencies...
call gradlew :app:dependencies

echo [3/4] Building debug APK...
call gradlew :app:assembleDebug --stacktrace

if %errorlevel% equ 0 (
    echo.
    echo [SUCCESS] APK built successfully! 🎉
    echo.
    echo APK location: app\build\outputs\apk\debug\app-debug.apk
    echo Size: 
    for %%i in ("app\build\outputs\apk\debug\app-debug.apk") do echo   %%~zi bytes
    echo.
    echo To install on device:
    echo 1. Enable USB debugging on phone
    echo 2. Connect phone via USB
    echo 3. Run: adb install app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo To install on emulator:
    echo 1. Start Android emulator
    echo 2. Run: adb install app\build\outputs\apk\debug\app-debug.apk
) else (
    echo.
    echo [ERROR] Build failed
    echo Check error messages above
)

echo.
echo [4/4] Building release APK (optional)...
echo Run this if you want release build:
echo gradlew :app:assembleRelease
echo.

pause