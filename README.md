# Cake Store App (Android)

An Android e-commerce application for browsing cakes, managing a cart, and completing orders with a clean checkout and payment flow.  
Built using **Jetpack Compose**, **MVVM**, and **Firebase**, following **Clean Architecture** principles.


## Features

- Browse cake products with images and details  
- Add cakes to cart with size and quantity selection  
- Manage shipping address  
- Select shipping options (Standard / Express)  
- Mock payment method (Card / Cash on Delivery)  
- Order summary with price calculation  
- Place orders and store them in Firebase Firestore  
- Payment success feedback (Toast / Snackbar)



## Tech Stack

### Android
- Kotlin
- Jetpack Compose (Material 3)
- Navigation Compose
- Coil (image loading)
- Coroutines & Flow

### Architecture
- MVVM (Model–View–ViewModel)
- Clean Architecture
  - Presentation layer
  - Domain layer
  - Data layer
- StateFlow for UI state management
- Koin for Dependency Injection

### Backend & Cloud
- Firebase Authentication
- Firebase Firestore (users, checkout, orders)
- Firebase Realtime Database (products)


