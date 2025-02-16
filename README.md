# City Guide App

Welcome to the **City Guide App** repository! This project is a modern, feature-based Android application for guidance in a city. 
Built entirely with Jetpack Compose and following Clean Architecture principles, the app uses MVVM and MVI patterns for efficient state management and separation of concerns.

---

## 📱 Features

- **User Authentication**: Smooth and secure login and registration flows.
- **Location Catalog**: Browse and search a wide range of locations.
- **Location Details**: View detailed location specifications and reviews.
- **Weather Display**: See the weather in the city of your choosing!.
- **User Profile**: Manage account settings and view order history.
- **Dynamic Navigation**: Feature-aware navigation system.

---

## 🛠️ Tech Stack

### **Languages & Frameworks**
- **Kotlin**: Clean and concise Android development.
- **Jetpack Compose**: Declarative and fully XML-free UI development.

### **Architecture**
- **Feature-Based Clean Architecture**:  
  Each feature has its own `data`, `domain`, and `presentation` layers.
- **MVVM (Model-View-ViewModel)**: For reactive UI updates and modularized screens.
- **MVI (Model-View-Intent)**: Efficient state management and reduced callback functions.

### **Libraries & Tools**
- **Jetpack Compose**: Jetpack Compose for Declarative and Stateful UI
- **Navigation**: Compose Navigation with custom utilities for modular navigation.
- **Dependency Injection**: Dagger Hilt for scalable dependency management.
- **Networking**: Retrofit for API interactions.
- **Database**: Room for local persistence.
- **Reactive Programming**: Flow and StateFlow for consistent state management.

---

## 📂 Project Structure

### Overall Structure

```plaintext
📂 app
 ┣ 📂 common             # Shared utilities (e.g., extensions, mappers)
 ┣ 📂 features           # Feature modules
 ┃ ┣ 📂 auth             # Authentication feature (data, domain, presentation)
 ┃ ┣ 📂 favorites        # Favorites feature (data, domain, presentation)
 ┃ ┣ 📂 favorites        # Home feature (data, domain, presentation)
 ┃ ┗ 📂 profile          # Profile-related features
 ┣ 📂 main               # App-level classes (e.g., Application, entry point)
 ┣ 📂 mvi                # Base classes and utilities for MVI pattern
 ┗ 📂 navigation         # Navigation-related code (e.g., NavHost, destinations)
```
---
## ⚙️ Setup Instructions

1. **Clone this repository**:
   ```bash
   git clone https://github.com/cemoner/CityGuide.git
   cd CityGuide
2. Open the project in Android Studio.
3. Sync the Gradle files.
4. Run the app on an emulator or connected device.

🚀 Features in Development
Push Notifications: Stay updated with the latest deals and offers.
<br>
---
<br>
👨‍💻 Contributions
<br>
Contributions are welcome! 
<br>
Feel free to submit a pull request or open an issue for improvements or feature requests.
<br>

---
<br>
🙌 Acknowledgments
Special thanks to Çukurova University Computer Engineering Department for mentorship and guidance during development.
