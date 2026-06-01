@echo off
echo ===============================================
echo     PantryBox - Clean and Build Script
echo ===============================================
echo.

echo [1/6] Checking environment...
java -version 2>&1 | findstr "version" || (
    echo [✗] Java not found or not in PATH
    echo Please install JDK 17 or 21
    pause
    exit /b 1
)

echo [2/6] Stopping Gradle daemon...
call gradlew --stop 2>nul

echo [3/6] Cleaning old cache folders...
if exist .gradle rmdir /s /q .gradle
if exist .idea rmdir /s /q .idea
if exist app\build rmdir /s /q app\build
if exist build rmdir /s /q build

echo [4/6] Downloading Gradle 8.5...
call gradlew wrapper --gradle-version 8.5

echo [5/6] Running clean...
call gradlew clean

echo [6/6] Syncing project...
call gradlew :app:assembleDebug --stacktrace

echo.
if %errorlevel% equ 0 (
    echo ===============================================
    echo     SYNC SUCCESSFUL! 🎉
    echo ===============================================
    echo.
    echo Configuration:
    echo - Gradle: 8.5
    echo - AGP: 8.5.0
    echo - Kotlin: 1.9.22
    echo - Java: 21 compatible
    echo.
    echo Next steps:
    echo 1. Open project in Android Studio
    echo 2. Click "Sync Now" if prompted
    echo 3. Create emulator (Tools > AVD Manager)
    echo 4. Click green play button to run
) else (
    echo ===============================================
    echo     SYNC FAILED 😔
    echo ===============================================
    echo.
    echo Run this command manually:
    echo gradlew :app:assembleDebug --stacktrace --info
    echo.
    echo Then check the output for specific errors.
)

echo.
pause

