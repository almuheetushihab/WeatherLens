<div align="center">

  <img src="https://user-images.githubusercontent.com/74038190/212257468-1e9a91f1-b6bc-46e5-8ad9-95240c0c707d.gif" width="100px">

  # 🌦️ WeatherLens

  **A Professional-Grade Weather App built with Modern Android Development (MAD)**
  
  [![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple.svg?style=for-the-badge&logo=kotlin)](https://kotlinlang.org)
  [![Compose](https://img.shields.io/badge/Jetpack%20Compose-Material3-blue.svg?style=for-the-badge&logo=android)](https://developer.android.com/jetpack/compose)
  [![Architecture](https://img.shields.io/badge/Architecture-MVVM-green.svg?style=for-the-badge)](https://developer.android.com/topic/architecture)
  [![API](https://img.shields.io/badge/API-OpenWeatherMap-orange.svg?style=for-the-badge)](https://openweathermap.org/)
  [![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](LICENSE)

  <p align="center">
    <a href="#-screenshots">📱 Screenshots</a> •
    <a href="#-key-features">✨ Features</a> •
    <a href="#-tech-stack">🛠️ Tech Stack</a> •
    <a href="#-project-architecture">🏗️ Architecture</a> •
    <a href="#-documentation">📖 Documentation</a>
  </p>
</div>

---

## 🚀 Project Overview

**WeatherLens** is not just another weather app. It is an engineering effort to build a seamless, offline-first capable weather application. Built entirely from scratch using **Jetpack Compose**, it challenges static UI designs by implementing a **Dynamic Gradient Engine** that adapts the app's visual theme based on real-time weather conditions (Sunny, Rainy, Night, etc.).

Unlike basic tutorials, WeatherLens incorporates **Jetpack DataStore** to handle user preferences (Unit Conversion) persistently, ensuring a premium user experience.

---

## 📱 Screenshots

| **Splash Screen** | **Home Screen (Night)** | **Settings Screen** |
|:---:|:---:|:---:|
| <img src="" width="250"> | <img src="" width="250"> | <img src="" width="250"> |

> *The UI background changes dynamically based on weather conditions and time of day!*

---

## ✨ Key Features

* **🎨 Dynamic UI:** The background gradient and theme adapt visually to the current weather status (Clear, Clouds, Rain, Thunderstorm).
* **⚙️ Smart Settings:** Implemented **Jetpack DataStore** to save user preferences. Users can toggle between **Celsius (°C)** and **Fahrenheit (°F)**, and the app remembers the choice permanently.
* **🔍 Global Search:** Search for any city worldwide and get instant weather updates.
* **📊 Accurate Metrics:** Displays real-time **Pressure**, **Visibility**, **Humidity**, and **Wind Speed** integration.
* **📅 5-Day Forecast:** A detailed scrollable forecast section with toggleable tabs for Temperature, Wind, and Precipitation.
* **⚡ Reactive Experience:** Built with **Kotlin Flows**, ensuring the UI updates instantly when settings change without reloading the API.

---

## 🛠️ Tech Stack

* **Language:** [Kotlin](https://kotlinlang.org/) (100%)
* **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material Design 3)
* **Networking:** [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
* **Local Storage:** [Jetpack DataStore (Preferences)](https://developer.android.com/topic/libraries/architecture/datastore)
* **Concurrency:** [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
* **Navigation:** [Navigation Compose](https://developer.android.com/guide/navigation/navigation-compose)
* **Architecture:** MVVM (Model-View-ViewModel)

---

## 🏗️ Project Architecture

The app follows the **Guide to App Architecture** by Google.

1.  **UI Layer:** Composable screens that observe the `UiState`.
2.  **ViewModel Layer:** Handles business logic, converts data units (C to F), and exposes StateFlows to the UI.
3.  **Data Layer:**
    * **Remote:** Retrofit calls to OpenWeatherMap.
    * **Local:** DataStore reads/writes user preferences.

```mermaid
graph TD
    UI[WeatherScreen (Compose)] -->|Observes| VM[WeatherViewModel]
    VM -->|Fetches Data| Rep[Repository / Retrofit]
    VM -->|Observes Settings| DS[DataStore Preferences]
    Rep -->|API Call| Net[OpenWeatherMap API]
    DS -->|Saves Unit| Local[Local Storage]
