@echo off
echo ===============================================
echo     PantryBox - Clean and Build Script
echo ===============================================
echo.

echo [1/5] Stopping Gradle daemon...
call gradlew --stop

echo [2/5] Cleaning project...
if exist .gradle (
    echo Removing .gradle folder...
    rmdir /s /q .gradle
)

if exist .idea (
    echo Removing .idea folder...
    rmdir /s /q .idea
)

if exist app\build (
    echo Removing app/build folder...
    rmdir /s /q app\build
)

if exist build (
    echo Removing build folder...
    rmdir /s /q build
)

echo [3/5] Running clean command...
call gradlew clean

echo [4/5] Downloading Gradle wrapper...
call gradlew wrapper

echo [5/5] Building project...
call gradlew build --stacktrace

echo.
if %errorlevel% equ 0 (
    echo ===============================================
    echo     BUILD SUCCESSFUL! 🎉
    echo ===============================================
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
    echo Check TROUBLESHOOTING.md for help
    echo Or try: gradlew build --info
)

echo.
pause