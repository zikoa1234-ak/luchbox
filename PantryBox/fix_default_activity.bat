@echo off
echo ===============================================
echo     Fix "Default Activity not found" Error
echo ===============================================
echo.

echo This error means Android Studio can't find MainActivity.
echo Common causes:
echo 1. Wrong package name in AndroidManifest.xml
echo 2. MainActivity in wrong location
echo 3. Missing @AndroidEntryPoint annotation
echo 4. Corrupted build cache
echo.

echo [1/6] Checking MainActivity location...
if exist "app\src\main\java\com\pantrybox\MainActivity.kt" (
    echo ✓ MainActivity found at: com.pantrybox.MainActivity
) else (
    echo ✗ MainActivity NOT found!
    echo Expected: app\src\main\java\com\pantrybox\MainActivity.kt
    pause
    exit /b 1
)

echo [2/6] Checking AndroidManifest.xml...
findstr "MainActivity" app\src\main\AndroidManifest.xml
if %errorlevel% equ 0 (
    echo ✓ MainActivity declared in AndroidManifest.xml
) else (
    echo ✗ MainActivity NOT in AndroidManifest.xml
)

echo [3/6] Checking package declaration...
type "app\src\main\java\com\pantrybox\MainActivity.kt" | findstr "package com.pantrybox"
if %errorlevel% equ 0 (
    echo ✓ Correct package: com.pantrybox
) else (
    echo ✗ Wrong package in MainActivity.kt
)

echo [4/6] Cleaning build cache...
call gradlew clean
rmdir /s /q app\build 2>nul

echo [5/6] Rebuilding...
call gradlew :app:assembleDebug

if %errorlevel% neq 0 (
    echo.
    echo ✗ Build failed. Checking common issues...
    echo.
    call gradlew :app:compileDebugKotlin --stacktrace
    pause
    exit /b 1
)

echo [6/6] Verifying APK structure...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✓ APK created successfully
    echo.
    echo To run on device/emulator:
    echo adb install app\build\outputs\apk\debug\app-debug.apk
    echo adb shell am start -n com.pantrybox/.MainActivity
) else (
    echo ✗ APK not created
)

echo.
echo ===============================================
echo     Fix Applied!
echo ===============================================
echo.
echo If Android Studio still shows error:
echo 1. File → Invalidate Caches → Invalidate and Restart
echo 2. Build → Clean Project
echo 3. Build → Rebuild Project
echo 4. Run → Run 'app'
echo.
echo If using emulator:
echo 1. Start emulator first (AVD Manager)
echo 2. Wait for emulator to fully boot
echo 3. Then run the app
echo.
pause