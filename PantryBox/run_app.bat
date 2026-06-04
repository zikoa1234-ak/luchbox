@echo off
echo ===============================================
echo     RUN PANTYBOX APP - Complete Guide
echo ===============================================
echo.

echo [STEP 1] Checking project structure...
if not exist "app\build.gradle.kts" (
    echo ✗ ERROR: Not in PantryBox project directory
    echo Please navigate to: C:\Users\ak-47\Downloads\luchbox-main\luchbox-main\PantryBox
    echo.
    echo Current directory: %CD%
    pause
    exit /b 1
)

echo ✓ Project structure OK
echo.

echo [STEP 2] Choose your method to run the app:
echo.
echo [1] Build APK and install manually (Recommended for first time)
echo [2] Build and run via Android Studio
echo [3] Install on connected device
echo [4] Run on emulator
echo [5] Exit
echo.

set /p choice="Enter choice (1-5): "

if "%choice%"=="1" goto :build_apk
if "%choice%"=="2" goto :android_studio
if "%choice%"=="3" goto :install_device
if "%choice%"=="4" goto :run_emulator
if "%choice%"=="5" exit /b 0

echo Invalid choice
pause
exit /b 1

:build_apk
echo.
echo =========== BUILDING APK ===========
echo.
echo [1/3] Cleaning previous builds...
call gradlew clean

echo [2/3] Building debug APK...
call gradlew :app:assembleDebug --stacktrace

if %errorlevel% neq 0 (
    echo ✗ Build failed!
    echo Check error messages above
    echo Try: gradlew :app:assembleDebug --info
    pause
    exit /b 1
)

echo [3/3] APK created successfully! 🎉
echo.
echo APK location: %CD%\app\build\outputs\apk\debug\app-debug.apk
echo.
echo Next steps:
echo 1. Connect Android device with USB debugging enabled
echo 2. Or start Android emulator
echo 3. Run option 3 or 4 from this menu
pause
goto :eof

:android_studio
echo.
echo =========== ANDROID STUDIO ===========
echo.
echo To run via Android Studio:
echo 1. Open Android Studio
echo 2. Click "Open" and select the PantryBox folder
echo 3. Wait for Gradle sync (5-10 minutes first time)
echo 4. Create emulator: Tools > AVD Manager > Create Virtual Device
echo 5. Select Pixel 5 with API 34 (Android 14)
echo 6. Click green play button ▶️
echo.
echo If you see "Default Activity not found":
echo 1. Run → Edit Configurations
echo 2. Set Launch to "Specified Activity"
echo 3. Enter: com.pantrybox.MainActivity
echo 4. Click OK
echo.
pause
goto :eof

:install_device
echo.
echo =========== INSTALL ON DEVICE ===========
echo.
echo [1/4] Checking connected devices...
adb devices

echo [2/4] Building APK if needed...
if not exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo Building APK...
    call gradlew :app:assembleDebug
)

echo [3/4] Installing APK...
adb install -r app\build\outputs\apk\debug\app-debug.apk

if %errorlevel% neq 0 (
    echo ✗ Installation failed!
    echo.
    echo Make sure:
    echo 1. Phone is connected via USB
    echo 2. USB debugging is enabled
    echo 3. You accepted the debugging prompt on phone
    echo.
    echo To enable USB debugging:
    echo Settings > About Phone > Tap "Build Number" 7 times
    echo Settings > Developer Options > USB Debugging (ON)
    pause
    exit /b 1
)

echo [4/4] Launching app...
adb shell am start -n com.pantrybox/.MainActivity

echo.
echo ✅ PantryBox launched on your device! 🎉
echo Check your phone screen for the app.
pause
goto :eof

:run_emulator
echo.
echo =========== RUN ON EMULATOR ===========
echo.
echo [1/5] Starting emulator...
echo Note: If emulator is not created yet:
echo 1. Open Android Studio
echo 2. Tools > AVD Manager > Create Virtual Device
echo 3. Select Pixel 5, API 34 (Android 14)
echo.
timeout /t 5 /nobreak

echo [2/5] Checking emulator status...
adb devices | findstr emulator
if %errorlevel% neq 0 (
    echo ✗ No emulator detected
    echo.
    echo Start emulator manually:
    echo 1. Open Android Studio
    echo 2. Click AVD Manager (📱 icon)
    echo 3. Click play button next to your emulator
    echo 4. Wait for emulator to fully boot
    echo 5. Run this script again
    pause
    exit /b 1
)

echo [3/5] Building APK...
if not exist "app\build\outputs\apk\debug\app-debug.apk" (
    call gradlew :app:assembleDebug
)

echo [4/5] Installing on emulator...
adb install -r app\build\outputs\apk\debug\app-debug.apk

echo [5/5] Launching app...
adb shell am start -n com.pantrybox/.MainActivity

echo.
echo ✅ PantryBox launched on emulator! 🎉
echo Check the emulator window.
pause
goto :eof