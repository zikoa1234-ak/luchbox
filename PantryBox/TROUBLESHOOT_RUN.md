# 🚀 How to Run PantryBox App - Troubleshooting Guide

## 📱 **Method 1: Quick Run (Recommended)**
```cmd
cd C:\Users\ak-47\Downloads\luchbox-main\luchbox-main\PantryBox
quick_run.bat
```

## 🛠️ **Method 2: Step-by-Step Manual**

### **Option A: Build APK Only**
```cmd
cd C:\Users\ak-47\Downloads\luchbox-main\luchbox-main\PantryBox
gradlew :app:assembleDebug
```
**Output**: `app/build/outputs/apk/debug/app-debug.apk`

### **Option B: Install on Connected Device**
```cmd
# 1. Enable USB debugging on phone
# 2. Connect phone via USB
# 3. Run:
adb install app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.pantrybox/.MainActivity
```

### **Option C: Run on Emulator**
```cmd
# 1. Start emulator from Android Studio
# 2. Run:
adb install app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.pantrybox/.MainActivity
```

## 🔧 **Troubleshooting Common Issues**

### **Issue 1: "gradlew is not recognized"**
**Solution**:
```cmd
# Use gradlew.bat instead
gradlew.bat :app:assembleDebug
```

### **Issue 2: "Java not found"**
**Solution**: Install JDK 17 or 21 from:
- https://adoptium.net/temurin/releases/

### **Issue 3: "adb not found"**
**Solution**: Add Android SDK to PATH:
```cmd
set PATH=%PATH%;C:\Users\%USERNAME%\AppData\Local\Android\Sdk\platform-tools
```

### **Issue 4: "Default Activity not found" in Android Studio**
**Solution**:
1. Run → Edit Configurations
2. Set Launch to "Specified Activity"
3. Enter: `com.pantrybox.MainActivity`
4. Click OK

### **Issue 5: Build fails with dependency errors**
**Solution**:
```cmd
# Clean and rebuild
gradlew clean
gradlew :app:assembleDebug --stacktrace
```

## 📋 **Checklist Before Running**

### **Prerequisites:**
- [ ] **Java JDK 17+** installed
- [ ] **Android SDK** installed
- [ ] **Project path** correct: `C:\Users\ak-47\Downloads\luchbox-main\luchbox-main\PantryBox`
- [ ] **Device/Emulator** ready

### **Files Required:**
- [ ] `AndroidManifest.xml` with MainActivity
- [ ] `MainActivity.kt` in correct package
- [ ] `build.gradle.kts` configured
- [ ] All dependencies downloaded

## 🎯 **Verification Steps**

### **Step 1: Build Verification**
```cmd
gradlew :app:assembleDebug
```
**Expected**: `BUILD SUCCESSFUL`

### **Step 2: APK Verification**
```cmd
dir app\build\outputs\apk\debug\app-debug.apk
```
**Expected**: File exists with size > 1MB

### **Step 3: Installation Verification**
```cmd
adb install app\build\outputs\apk\debug\app-debug.apk
```
**Expected**: `Success`

### **Step 4: Launch Verification**
```cmd
adb shell am start -n com.pantrybox/.MainActivity
adb logcat | findstr "MainActivity"
```
**Expected**: `MainActivity created`

## 🆘 **Emergency Fixes**

### **If nothing works:**
1. **Delete cache**:
   ```cmd
   rmdir /s /q .gradle
   rmdir /s /q .idea
   rmdir /s /q app\build
   ```

2. **Fresh build**:
   ```cmd
   gradlew clean
   gradlew :app:assembleDebug --refresh-dependencies
   ```

3. **Check manifest**:
   ```cmd
   type app\src\main\AndroidManifest.xml | findstr "MainActivity"
   ```

## 📞 **Quick Commands Reference**

| Command | Purpose |
|---------|---------|
| `quick_run.bat` | One-click build and run |
| `gradlew :app:assembleDebug` | Build APK |
| `adb devices` | Check connected devices |
| `adb install app.apk` | Install APK |
| `adb logcat` | View app logs |
| `gradlew clean` | Clean project |

## 🎉 **Success Indicators**

When the app runs successfully, you'll see:

1. **On device/emulator**: PantryBox app opens
2. **Logcat output**: `MainActivity created`
3. **App shows**: Pantry management screen
4. **No errors**: No crash reports

## ⏱️ **First Run Timing**

- **First build**: 5-10 minutes (downloads dependencies)
- **Subsequent builds**: 30-60 seconds
- **APK size**: ~5-10MB
- **Launch time**: 2-5 seconds

**Now run the app using:**
```cmd
cd C:\Users\ak-47\Downloads\luchbox-main\luchbox-main\PantryBox
quick_run.bat
```