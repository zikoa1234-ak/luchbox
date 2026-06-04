@echo off
echo ===============================================
echo     QUICK RUN - PantryBox App
echo ===============================================
echo.

echo This script will:
echo 1. Build the APK
echo 2. Install on connected device/emulator
echo 3. Launch the app
echo.

echo Checking prerequisites...

REM Check if in project directory
if not exist "app\build.gradle.kts" (
    echo ERROR: Not in PantryBox project directory!
    echo Please navigate to: C:\Users\ak-47\Downloads\luchbox-main\luchbox-main\PantryBox
    echo Current location: %CD%
    pause
    exit /b 1
)

REM Check Java
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: Java not found!
    echo Please install JDK 17 or higher
    pause
    exit /b 1
)

REM Check ADB
where adb >nul 2>nul
if %errorlevel% neq 0 (
    echo WARNING: ADB not found in PATH
    echo ADB is needed to install on device
    echo You can still build the APK manually
)

echo.
echo Starting build process...
echo =========================

echo [1/5] Cleaning previous build...
call gradlew clean 2>nul

echo [2/5] Building APK...
call gradlew :app:assembleDebug

if %errorlevel% neq 0 (
    echo.
    echo ✗ BUILD FAILED!
    echo.
    echo Common solutions:
    echo 1. Make sure you're in the correct directory
    echo 2. Check Java version (should be 17 or 21)
    echo 3. Run: gradlew :app:assembleDebug --stacktrace
    echo.
    pause
    exit /b 1
)

echo [3/5] APK built successfully! ✓
echo Location: %CD%\app\build\outputs\apk\debug\app-debug.apk

echo [4/5] Checking for connected devices...
adb devices > devices.txt
type devices.txt | findstr /i "device" >nul

if %errorlevel% equ 0 (
    echo Device/emulator detected!
    echo [5/5] Installing and launching...
    adb install -r app\build\outputs\apk\debug\app-debug.apk
    adb shell am start -n com.pantrybox/.MainActivity
    echo.
    echo ✅ PantryBox launched successfully! 🎉
    echo Check your device/emulator screen.
) else (
    echo No device/emulator detected.
    echo.
    echo ✅ APK built successfully!
    echo.
    echo To run the app:
    echo Option A - Connect Android phone:
    echo 1. Enable USB debugging
    echo 2. Connect via USB
    echo 3. Run this script again
    echo.
    echo Option B - Use emulator:
    echo 1. Start emulator from Android Studio
    echo 2. Run this script again
    echo.
    echo Option C - Manual install:
    echo adb install app\build\outputs\apk\debug\app-debug.apk
    echo adb shell am start -n com.pantrybox/.MainActivity
)

del devices.txt 2>nul
echo.
pause