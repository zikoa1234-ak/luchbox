# PantryBox / Smart LunchBox - Project Summary

## 🎯 Project Overview
A native Android app built with Kotlin and Jetpack Compose that helps users manage their pantry inventory and suggests recipes based on available ingredients.

## 📱 Core Features
1. **Pantry Management**: Add, edit, and track ingredients with quantities and units
2. **Recipe Suggestions**: Get recipe recommendations based on available pantry items
3. **Automatic Stock Deduction**: Automatically deduct used ingredients when cooking recipes
4. **Low Stock Alerts**: Get notified when ingredients are running low
5. **Shopping List**: Generate shopping lists from missing ingredients
6. **Weekly Meal Planner**: Plan meals for the week
7. **Nutrition Tracking**: View recipe nutrition information
8. **Dark Mode**: Full Material 3 theming support

## 🏗️ Architecture
- **MVVM Pattern** with clean separation of concerns
- **Room Database** for local data persistence
- **Jetpack Compose** for modern declarative UI
- **Dagger Hilt** for dependency injection
- **Navigation Compose** for screen navigation
- **Coroutines & StateFlow** for asynchronous operations

## 📁 Project Structure

### Data Layer
```
data/
├── database/
│   ├── PantryDatabase.kt     # Room database
│   └── Converters.kt         # Type converters
├── entities/                  # Room entities
│   ├── PantryItem.kt
│   ├── Recipe.kt
│   ├── RecipeIngredient.kt
│   ├── RecipeStep.kt
│   ├── MealPlan.kt
│   └── ShoppingItem.kt
├── dao/                      # Data Access Objects
│   ├── PantryItemDao.kt
│   ├── RecipeDao.kt
│   ├── RecipeIngredientDao.kt
│   ├── RecipeStepDao.kt
│   ├── MealPlanDao.kt
│   └── ShoppingItemDao.kt
├── repositories/             # Repository pattern
│   ├── PantryRepository.kt
│   └── RecipeRepository.kt
└── sample/                   # Sample data
    └── SampleDataProvider.kt
```

### Domain Layer
```
domain/
├── models/                   # Domain models
└── usecases/                 # Business logic
```

### Presentation Layer
```
presentation/
├── navigation/               # Navigation
│   ├── Destinations.kt
│   └── NavGraph.kt
├── screens/                  # UI Screens
│   ├── pantry/
│   │   ├── PantryScreen.kt
│   │   └── AddEditPantryItemScreen.kt
│   └── recipes/
│       ├── RecipesScreen.kt
│       └── RecipeDetailsScreen.kt
├── theme/                    # UI Theme
│   ├── Theme.kt
│   └── Typography.kt
├── ui/                       # Reusable components
├── viewmodels/               # ViewModels
│   ├── PantryViewModel.kt
│   └── RecipeViewModel.kt
└── utils/                    # Utility classes
```

### App Entry Points
- `MainActivity.kt` - Main entry point
- `PantryBoxApplication.kt` - Hilt Application class

## 🔧 Key Technical Components

### Database (Room)
- **PantryDatabase**: Main database with 6 entities
- **Type Converters**: For Date serialization
- **DAOs**: Complete CRUD operations for all entities
- **Relationships**: Foreign keys and indices

### Business Logic
- **Recipe Matching Algorithm**: Calculates match percentage based on pantry stock
- **Stock Deduction**: Automatically updates pantry when recipes are cooked
- **Low Stock Detection**: Alerts when items fall below threshold
- **Shopping List Generation**: Creates lists from missing ingredients

### UI Components
- **Material 3 Design System**: Modern theming
- **Composable Screens**: Fully implemented pantry and recipe screens
- **Navigation**: Complete navigation graph
- **Reusable Components**: Card, List, Form components

## 🚀 How to Run

### Prerequisites
1. Android Studio Giraffe (2023.2.1) or higher
2. JDK 17 or higher
3. Android SDK 34

### Steps
1. Open the project in Android Studio
2. Sync Gradle files
3. Build the project (Build > Make Project)
4. Run on emulator or physical device (Run > Run 'app')

## 📊 Sample Data
The app includes sample data for:
- 10 pantry items (tomatoes, eggs, rice, chicken, etc.)
- 5 sample recipes (Tomato Egg Stir Fry, Chicken Fried Rice, etc.)
- Recipe ingredients and steps
- Sample shopping list items

## 🎨 UI Screens Implemented
1. **Pantry Screen** - View and manage pantry items
2. **Add/Edit Pantry Item Screen** - Add or edit ingredients
3. **Recipes Screen** - Browse recipes with match percentages
4. **Recipe Details Screen** - View full recipe with ingredients, steps, nutrition

## 🔜 Next Steps to Complete
1. Implement remaining screens:
   - Home dashboard
   - Weekly planner
   - Shopping list
   - Progress/nutrition
   - Settings
2. Add image loading with Coil
3. Implement data synchronization (optional)
4. Add unit tests
5. Polish UI animations and transitions
6. Add search functionality
7. Implement filters and sorting

## 🛠️ Dependencies
- Jetpack Compose
- Room Database
- Dagger Hilt
- Navigation Compose
- Material 3
- Coil (for images)
- Coroutines

## 📈 Business Logic Highlights
1. **Match Percentage Calculation**: Recipes are scored based on available ingredients
2. **Smart Stock Management**: Prevents negative stock and handles unit conversions
3. **Automatic Updates**: Real-time updates using StateFlow
4. **Error Handling**: Comprehensive error handling in ViewModels

This project provides a solid foundation for a production-ready pantry management app with clean architecture and modern Android development practices.