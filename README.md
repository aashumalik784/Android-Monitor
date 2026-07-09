# Android Monitor 📱

A native Android application built with Java and C++ (JNI) for monitoring system activities and processes.

##  Features

- Real-time system monitoring
- Native code integration using JNI
- Process and activity tracking
- Lightweight and efficient
- Built for ARM64 architecture

## ️ Tech Stack

- **Language:** Java (64.7%), C++ (35.3%)
- **Build Tool:** Gradle
- **Min SDK:** Android 7.0 (API 24)
- **Target SDK:** Android 14 (API 34)
- **Architecture:** ARM64-v8a

## 📦 Prerequisites

- Android Studio Arctic Fox or later
- JDK 17
- Android SDK 34
- Android NDK (for native code)

##  Installation

### Clone the Repository
```bash
git clone https://github.com/aashumalik784/Android-Monitor.git
cd Android-Monitor
```

### Build from Source
```bash
# Using Gradle Wrapper
./gradlew clean assembleDebug

# Or using Gradle directly
gradle clean assembleDebug
```

## 📱 Usage

1. Install the APK on your Android device
2. Open the application
3. Grant necessary permissions
4. Start monitoring system activities

## 🏗️ Project Structure
# Create .github/workflows directory
mkdir -p .github/workflows

cat << 'EOF' > .github/workflows/android-ci.yml
name: Android CI

on:
  push:
    branches: [ main, master ]
  pull_request:
    branches: [ main, master ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v3
      
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: gradle
        
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
      
    - name: Build with Gradle
      run: ./gradlew clean build
      
    - name: Run tests
      run: ./gradlew test
      
    - name: Build Debug APK
      run: ./gradlew assembleDebug
      
    - name: Upload APK Artifact
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk
        
    - name: Upload Build Reports
      if: failure()
      uses: actions/upload-artifact@v3
      with:
        name: build-reports
        path: app/build/reports/

  lint:
    runs-on: ubuntu-latest
    
    steps:
    - name: Checkout code
      uses: actions/checkout@v3
      
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        
    - name: Run Lint
      run: ./gradlew lint
      
    - name: Upload Lint Reports
      if: always()
      uses: actions/upload-artifact@v3
      with:
        name: lint-reports
        path: app/build/reports/lint-results.html
