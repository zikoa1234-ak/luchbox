# 🛠️ PantryBox - Android Studio Troubleshooting Guide

## Common Build Errors and Solutions

### **Error 1: "Plugin was not found"**
```
Plugin [id: 'com.android.application', version: '8.3.0'] was not found
```

**Solution:**
1. **Update Android Studio** to latest version
2. **OR** Use compatible versions (already fixed):
   - Android Gradle Plugin: `8.2.2`
   - Kotlin: `1.9.20`
   - Hilt: `2.48`

**To manually fix:**
```kotlin
// In build.gradle.kts (project level)
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.20" apply false
}
```

### **Error 2: "Unsupported Kotlin Plugin"**
```
The project uses Gradle 8.2 which is incompatible with Kotlin...
```

**Solution:**
1. Open Android Studio
2. Go to: `File → Settings → Languages & Frameworks → Kotlin`
3. Set Kotlin version to: `1.9.20`

### **Error 3: Gradle Sync Failed**
```
Could not resolve all dependencies
```

**Solution:**
```bash
# 1. Clean and rebuild
Build → Clean Project
Build → Rebuild Project

# 2. Invalidate caches
File → Invalidate Caches → Invalidate and Restart

# 3. Delete gradle cache
rm -rf ~/.gradle/caches/
```

### **Error 4: "Room cannot find entity"**
```
Symbol not found: PantryItem
```

**Solution:**
1. Make sure all Kotlin files are in correct packages
2. Add `@Entity` annotation to data classes
3. Add `kapt` plugin in build.gradle:
```kotlin
plugins {
    id("kotlin-kapt")
}
```

## 🚀 Quick Fix Script

Create `fix_build.sh` in project root:

```bash
#!/bin/bash
echo "Fixing PantryBox build issues..."

# Clean gradle
./gradlew clean

# Stop gradle daemon
./gradlew --stop

# Delete caches
rm -rf .gradle
rm -rf ~/.gradle/caches/

# Build with stacktrace
./gradlew build --stacktrace

echo "Build fix complete!"
```

Or for Windows (`fix_build.bat`):
```batch
@echo off
echo Fixing PantryBox build issues...

gradlew clean
gradlew --stop
rmdir /s /q .gradle
gradlew build --stacktrace

echo Build fix complete!
```

## 📋 Android Studio Setup Checklist

### **Before Opening Project:**
1. [ ] Android Studio **Flamingo (2022.2.1)** or **Giraffe (2023.2.1)** or newer
2. [ ] **Android SDK 34** installed (SDK Manager → Android 14)
3. [ ] **Java 17** (File → Project Structure → SDK Location → JDK)

### **When Opening Project:**
1. [ ] Wait for Gradle sync (5-10 minutes first time)
2. [ ] If errors appear, click "Try Again" or "Sync Now"
3. [ ] Accept all licenses if prompted

### **If Still Not Working:**

#### **Method A: Use Gradle Wrapper**
```bash
# In terminal:
./gradlew assembleDebug
```

#### **Method B: Update Gradle**
1. Open `gradle/wrapper/gradle-wrapper.properties`
2. Change to:
```properties
distributionUrl=https\://services.gradle.org/distributions/gradle-8.2-bin.zip
```

#### **Method C: Downgrade Dependencies**
If still having issues, use even older versions:

**Update `app/build.gradle.kts`:**
```kotlin
// Change these:
compileSdk = 33  // from 34
targetSdk = 33   // from 34

// And dependencies:
implementation("androidx.core:core-ktx:1.10.1")
implementation("androidx.compose:compose-bom:2023.08.00")
```

## 🔧 Manual Fix Steps

### **Step 1: Check Android Studio Version**
```
Help → About
```
Should show: **Android Studio Flamingo** or newer

### **Step 2: Install Missing Components**
```
Tools → SDK Manager
```
Install:
- Android SDK Platform 34
- Android SDK Build-Tools 34
- Intel x86 Emulator Accelerator (HAXM)

### **Step 3: Configure Gradle JDK**
```
File → Settings → Build, Execution, Deployment → Build Tools → Gradle
```
Set: **Gradle JDK: Embedded JDK**

### **Step 4: Enable Hilt Annotation Processing**
```
File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
```
Check: **Enable annotation processing**

## 📱 Emulator Issues

### **Emulator Won't Start:**
1. Open **AVD Manager**
2. Click **▼** (dropdown) next to device
3. Select **Cold Boot Now**
4. OR: **Wipe Data** → **Cold Boot**

### **Emulator Too Slow:**
1. Edit AVD → Show Advanced Settings
2. RAM: **2048 MB** (not more)
3. VM Heap: **256 MB**
4. Graphics: **Software** (not Hardware)

## 📊 Verify Build Success

**Signs of successful build:**
```
BUILD SUCCESSFUL in 2m 30s
```

**Check Logcat after running:**
```
I/Application: PantryBoxApplication initialized
D/Room: Database created
```

## 🆘 Emergency Fixes

### **Complete Reset:**
```bash
# Backup your code first!
git stash

# Delete all generated files
rm -rf build/
rm -rf app/build/
rm -rf .gradle/
rm -rf .idea/

# Reopen in Android Studio
```

### **Alternative: Use VS Code**
1. Install "Android" extension in VS Code
2. Open PantryBox folder
3. Click "Run" → "Start Debugging"

## 📞 Getting Help

### **Check Logs:**
1. **Build Log**: View → Tool Windows → Build
2. **Gradle Console**: View → Tool Windows → Gradle
3. **Logcat**: View → Tool Windows → Logcat (filter by `tag:^Pantry`)

### **Common Solutions Recap:**
1. **Wrong Android Studio version** → Update to latest
2. **Missing SDK** → Install via SDK Manager
3. **Gradle cache corrupted** → Delete `.gradle` folder
4. **Kotlin version mismatch** → Set to 1.9.20
5. **Hilt not initialized** → Check `@HiltAndroidApp` annotation

### **Minimum Requirements:**
- **Android Studio**: 2022.2.1 (Flamingo)
- **Java**: JDK 17
- **RAM**: 8 GB minimum
- **Disk Space**: 10 GB free

## ✅ Final Verification

Run this command to verify setup:
```bash
./gradlew tasks --all
```

You should see:
- `assembleDebug` task available
- No errors about missing plugins
- All dependencies resolved

**If everything fails**, you can:
1. Clone the project fresh from GitHub
2. Use the pre-configured versions
3. Contact for help with specific error messages

**Remember:** First build always takes longest. Be patient! 🕐