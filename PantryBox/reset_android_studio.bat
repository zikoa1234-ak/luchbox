@echo off
echo ===============================================
echo     Android Studio Reset Script
echo ===============================================
echo.

echo [1/5] Stopping Android Studio...
taskkill /F /IM studio64.exe 2>nul
taskkill /F /IM studio.exe 2>nul
taskkill /F /IM java.exe 2>nul

echo [2/5] Clearing Android Studio cache...
if exist "%APPDATA%\Google\AndroidStudio*" (
    echo Removing %APPDATA%\Google\AndroidStudio*
    rmdir /s /q "%APPDATA%\Google\AndroidStudio*"
)

if exist "%LOCALAPPDATA%\Google\AndroidStudio*" (
    echo Removing %LOCALAPPDATA%\Google\AndroidStudio*
    rmdir /s /q "%LOCALAPPDATA%\Google\AndroidStudio*"
)

echo [3/5] Clearing JetBrains cache...
if exist "%APPDATA%\JetBrains" (
    echo Removing %APPDATA%\JetBrains
    rmdir /s /q "%APPDATA%\JetBrains"
)

if exist "%LOCALAPPDATA%\JetBrains" (
    echo Removing %LOCALAPPDATA%\JetBrains
    rmdir /s /q "%LOCALAPPDATA%\JetBrains"
)

echo [4/5] Clearing project cache...
if exist .idea rmdir /s /q .idea
if exist .gradle rmdir /s /q .gradle

echo [5/5] Starting Android Studio clean...
echo.
echo Android Studio cache cleared!
echo.
echo Next steps:
echo 1. Start Android Studio normally
echo 2. Select "Do not import settings"
echo 3. Choose "Standard" setup
echo 4. Open PantryBox project
echo.
pause