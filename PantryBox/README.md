# PantryBox / Smart LunchBox

A native Android app built with Kotlin and Jetpack Compose that helps users manage their pantry inventory and suggests recipes based on available ingredients.

## Features

- 📦 Pantry inventory management
- 🍳 Recipe suggestions based on available ingredients
- 📝 Recipe details with ingredients, steps, and nutrition
- 🛒 Automatic stock deduction when cooking recipes
- ⚠️ Low stock alerts
- 📋 Shopping list generation
- 📅 Weekly meal planning
- 🌙 Dark mode support

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM Architecture
- Navigation Compose
- Room Database
- Material 3 Design
- Coroutines & StateFlow
- Coil for image loading

## Project Structure

```
PantryBox/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/pantrybox/
│   │   │   │   ├── data/
│   │   │   │   │   ├── database/
│   │   │   │   │   ├── entities/
│   │   │   │   │   ├── repositories/
│   │   │   │   │   └── sample/
│   │   │   │   ├── di/
│   │   │   │   ├── domain/
│   │   │   │   │   ├── models/
│   │   │   │   │   └── usecases/
│   │   │   │   ├── presentation/
│   │   │   │   │   ├── navigation/
│   │   │   │   │   ├── screens/
│   │   │   │   │   ├── theme/
│   │   │   │   │   ├── ui/
│   │   │   │   │   └── viewmodels/
│   │   │   │   └── utils/
│   │   │   └── res/
└── build.gradle.kts
```