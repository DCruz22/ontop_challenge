## Ontop Android Technical challenge

This is the code for the technical challenge of Ontop Android. It consists of 3 modules, each of them corresponding to a challenge to solve using Android/Kotlin.

### Module 1: List - Details Android App
This module consists of an Android app that using PokeApi. I saw the API fitting for the requirements of the challenge and wanted to explore it since I have already tried TMDB.
The app uses the layers of Clean Architecture, with MVVM for the data flow as requested and all non Compose code is properly tested.

Libraries used: OkHttp, Retrofit, Coroutines, Hilt, Jetpack Compose, Coil, Navigation Compose, Mockk, JUnit, Turbine.

- data: Contains the infrastructure layer, including the Retrofit API service (`datasource`), API response models, the PagingSource implementation for paginated lists, and the Repository implementations that mediate between data and domain.
- domain: The core layer containing pure business logic. It defines domain models, mappers, and use cases (interactors) that are independent of the UI and data frameworks.
- ui: The presentation layer built with Jetpack Compose. It follows the MVVM pattern with ViewModels managing UI state through Flows. It also includes navigation logic and reusable UI components.

[Pokemon App Recording](Screen%20Recording%202026-05-22%20at%201.15.53%E2%80%AFAM.mov)

To run the app, simply open the project in Android Studio and run the `module_1` module.

### Module 2: Remove Chars
The algorithmic challenge consists in removing characters from a string based on a set provided on the same method. 
This made the removal logic easier to implement, since we can just check if the character is in the set and remove it if it is. 
The implementation is done using a StringBuilder to efficiently build the resulting string.

This module has no UI, you can run the tests at `module_2/src/test/java/com/example/module_2/RemoveCharsTest.kt` to see the implementation.

### Module 3: Password Generator
This challenge consists in creating a method that generates a random password based on the criteria provided by the user. Of the entropy options
provided, I chose to implement the `SecureRandom` cause, of the options, it is the most cryptographically secure for authentication credentials (even the
requirements admitted it was the better option for production).  

[Password Generator Recording](Screen%20Recording%202026-05-22%20at%2012.27.37%E2%80%AFAM.mov)

To run the app, simply open the project in Android Studio and run the `module_3` module.

