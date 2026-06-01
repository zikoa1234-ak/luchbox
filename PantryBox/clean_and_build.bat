@echo off
echo ===============================================
echo     PantryBox - Clean and Build Script
echo ===============================================
echo.

echo [1/6] Checking Java version...
java -version 2>&1 | findstr "version" || (
    echo [✗] Java not found or not in PATH
    echo Please run check_java_version.bat first
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

echo [6/6] Building project...
call gradlew build --stacktrace

echo.
if %errorlevel% equ 0 (
    echo ===============================================
    echo     BUILD SUCCESSFUL! 🎉
    echo ===============================================
    echo.
    echo Gradle: 8.5
    echo AGP: 8.5.0
    echo Java: 21 (compatible)
    echo.
    echo Next steps:
    echo 1. Open project in Android Studio
    echo 2. Wait for Gradle sync
    echo 3. Create emulator (Tools > AVD Manager)
    echo 4. Click green play button to run
) else (
    echo ===============================================
    echo     BUILD FAILED 😔
    echo ===============================================
    echo.
    echo Common solutions:
    echo 1. Run check_java_version.bat
    echo 2. In Android Studio: File → Invalidate Caches
    echo 3. Set JAVA_HOME to JDK 17
    echo.
    echo Run with --info for more details: gradlew build --info
)

echo.
pause
