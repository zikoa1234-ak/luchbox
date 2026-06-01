@echo off
echo ===============================================
echo     PantryBox - Quick Test Build
echo ===============================================
echo.

echo Testing Gradle configuration...

echo.
echo [1/3] Checking Gradle wrapper...
call gradlew --version

echo.
echo [2/3] Testing project configuration...
call gradlew projects

echo.
echo [3/3] Testing basic build...
call gradlew :app:assembleDebug --dry-run

echo.
echo ===============================================
echo     Test Complete!
echo ===============================================
echo.
echo If you see no errors above:
echo 1. Open project in Android Studio
echo 2. Click "Sync Now" in notification
echo 3. Should sync successfully
echo.
echo If errors appear, run:
echo clean_and_build.bat
echo.
pause