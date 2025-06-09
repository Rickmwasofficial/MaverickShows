# MaverickShows Movie Guide App

## Description

MaverickShows is an Android application built with Kotlin and Jetpack Compose that allows users to discover and explore movies and TV series. It fetches data from The Movie Database (TMDB) API to display comprehensive details including descriptions, trailers, images, cast, and recommendations.

## Features

*   **Browse Content:** View lists of popular, trending, and top-rated movies and TV series.
*   **Filter Content:** Easily filter content to show "All", "Movies" only, or "TV Series" only on the home screen.
*   **Detailed Information:**
    *   **Movie/Series Detail Page:** Access descriptions, watch trailers, view images, see cast members, and get recommendations.
    *   **Actor Page:** View actor biographies, images, and their filmography (movies and TV shows).
*   **Search Functionality:** Search for movies and TV series.
*   **Recent Searches:** Your recent search queries are saved locally (using Room) for quick access.
*   **Dynamic Theming:** Supports system light/dark mode and dynamic colors (Android 12+).

## Technologies Used

*   **Kotlin:** Primary programming language.
*   **Jetpack Compose:** For building the UI declaratively.
*   **Material 3:** For modern UI components and theming.
*   **ViewModel:** To store and manage UI-related data in a lifecycle-conscious way.
*   **Coroutines & Flow:** For asynchronous programming and reactive data streams.
*   **Hilt:** For dependency injection.
*   **Retrofit:** For type-safe HTTP calls to the TMDB API.
*   **Kotlinx Serialization:** For parsing JSON data from the API.
*   **Coil:** For image loading in Jetpack Compose.
*   **Navigation Compose:** For navigating between screens.
*   **Room:** For local database storage (e.g., recent searches).
*   **OkHttp:** As the HTTP client for Retrofit (including logging interceptor).

## Setup Instructions

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/maverickshows.git
    # Replace with your actual repository URL
    cd maverickshows
    ```

2.  **Get a TMDB API Key:**
    *   You need an API key from [The Movie Database (TMDB)](https://www.themoviedb.org/documentation/api).
    *   Sign up for an account, and you'll find your API key under your account settings in the API section.

3.  **Add the TMDB API Key to the project:**
    *   Create a file named `local.properties` in the root directory of the project (the same directory as `build.gradle.kts` and `settings.gradle.kts`).
    *   Add the following line to your `local.properties` file, replacing `YOUR_TMDB_API_KEY` with your actual key:
        ```properties
        TMDB_API="YOUR_TMDB_API_KEY"
        ```
    *   The project is configured to read this key from `local.properties` and make it available in the build configuration.

4.  **Build and Run:**
    *   Open the project in Android Studio.
    *   Let Gradle sync and download dependencies.
    *   Build and run the application on an emulator or a physical device.

## Screenshots

*(Placeholder for screenshots - You can add these later by editing the README.md directly)*

*   Home Screen
*   Details Screen
*   Search Screen
*   Actor Screen

## Known Issues / Future Work

*(Optional: Add any known issues or features you plan to implement)*
