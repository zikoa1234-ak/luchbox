@echo off
echo ========================================
echo    PantryBox Android App Launcher
echo ========================================
echo.

echo Checking prerequisites...

REM Check if Android Studio is installed
if exist "C:\Program Files\Android\Android Studio\bin\studio64.exe" (
    echo [✓] Android Studio found
    set "AS_PATH=C:\Program Files\Android\Android Studio\bin\studio64.exe"
) else if exist "C:\Program Files\JetBrains\Android Studio\bin\studio64.exe" (
    echo [✓] Android Studio found (JetBrains path)
    set "AS_PATH=C:\Program Files\JetBrains\Android Studio\bin\studio64.exe"
) else (
    echo [✗] Android Studio not found in default locations
    echo Please install Android Studio from: https://developer.android.com/studio
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %errorlevel% equ 0 (
    echo [✓] Java found
) else (
    echo [✗] Java not found
    echo Please install JDK 17 or later
    pause
    exit /b 1
)

REM Check project structure
if not exist "build.gradle.kts" (
    echo [✗] Not in project root directory
    echo Please run this script from the PantryBox folder
    pause
    exit /b 1
)

echo.
echo [✓] All prerequisites met
echo.
echo Options:
echo 1. Open project in Android Studio
echo 2. Build project with Gradle
echo 3. Clean project
echo 4. Exit
echo.

set /p choice="Enter choice (1-4): "

if "%choice%"=="1" (
    echo Opening project in Android Studio...
    start "" "%AS_PATH%" "%CD%"
) else if "%choice%"=="2" (
    echo Building project...
    call gradlew assembleDebug
    if %errorlevel% equ 0 (
        echo [✓] Build successful!
        echo APK location: app\build\outputs\apk\debug\app-debug.apk
    ) else (
        echo [✗] Build failed
        echo Check TROUBLESHOOTING.md for help
    )
    pause
) else if "%choice%"=="3" (
    echo Cleaning project...
    call gradlew clean
    echo [✓] Clean completed
    pause
) else if "%choice%"=="4" (
    exit /b 0
) else (
    echo Invalid choice
    pause
)

echo.
echo ========================================
echo    PantryBox Setup Complete!
echo ========================================
echo.
echo Next steps:
echo 1. Wait for Gradle sync in Android Studio
echo 2. Create an emulator (AVD Manager)
echo 3. Click the green play button to run
echo.
echo For help, see TROUBLESHOOTING.md
pause