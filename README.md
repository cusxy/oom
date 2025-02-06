# OOM (Out Of Memory) Project: A Guide to Performance Analysis

This project is designed to help developers learn how to analyze the performance of their Android projects. It demonstrates how to use tools like Build Scan, Gradle Profiler, and VisualVM with the VisualGC plugin to identify performance bottlenecks and improve build times.

## What You'll Learn

*   How to generate a complex multi-module Android project.
*   How to use Build Scan to understand your build process.
*   How to use Gradle Profiler to run performance tests.
*   How to use VisualVM and VisualGC to monitor memory usage. (in-progress)

## Getting Started

### 1. Generate the Project Modules

This project uses a script to create a set of modules. You can customize the number of modules and their complexity.

#### How to Run the Module Generation Script

1.  **Open the Project in Android Studio:**
    *   Open Android Studio and select "Open" to open this project.
2.  **Find the Script:**
    *   Go to the `scripts/src/main/kotlin/com/example/oom/scripts/Main.kt` file.
3.  **Run the `main` Function:**
    *   In the `Main.kt` file, you'll see a `main` function.
    *   Click the green "play" icon next to the `fun main(args: Array<String>)` line.
    *   Select "Run 'MainKt'" from the menu.
    *   This will run the script and create the modules.
4.  **Check the `modules` Directory:**
    *   After running the script, a new `modules` directory will be created in the root of your project. This directory will contain all the generated modules.

#### How to Change the Number of Modules

1.  **Open `Main.kt`:**
    *   Go to the `scripts/src/main/kotlin/com/example/oom/scripts/Main.kt` file.
2.  **Find the Settings:**
    *   At the top of the file, you'll see some settings:
        *   `MODULES_COUNT`: This controls how many modules will be created.
        *   `MODULES_COMPLEXITY`: This controls how complex the dependencies between modules will be.
        *   `MODELS_COUNT`: This controls how many models will be created in each module.
        *   `DAGGER_MODULES_COUNT`: This controls how many Dagger modules will be created in each module.
        *   `DAGGER_COMPONENTS_COUNT`: This controls how many Dagger components will be created in each module.
        *   `UNIT_TESTS_COUNT`: This controls how many unit tests will be created in each module.
        *   `ANDROID_TESTS_COUNT`: This controls how many android tests will be created in each module.
        *   `ANDROID_RESOURCES_STRINGS_COUNT`: This controls how many strings will be created in each module.
        *   `ANDROID_RESOURCES_COLORS_COUNT`: This controls how many colors will be created in each module.
3.  **Change the Values:**
    *   Change the values of these settings to create more or fewer modules, models, tests, etc.
    *   For example, to create 8 modules, change `const val MODULES_COUNT = 4` to `const val MODULES_COUNT = 8`.
4.  **Run the Script Again:**
    *   After changing the settings, run the `main` function again to generate the modules with the new settings.

### 2. Run Performance Scenarios with Gradle Profiler

Gradle Profiler helps you test how fast your project builds. This project includes some pre-made scenarios.

#### How to Run the Scenarios

1.  **Find the Scenarios File:**
    *   Go to the `profiler/performance.scenarios` file. This file contains the scenarios that Gradle Profiler will run.
2.  **Open a Terminal:**
    *   Open a terminal in Android Studio (View -> Tool Windows -> Terminal).
3.  **Run Gradle Profiler:**
    *   Run the following command: `gradle-profiler --benchmark --scenario-file profiler/performance.scenarios`
    *   This will start Gradle Profiler and run the scenarios.
4.  **View the Results:**
    *   Gradle Profiler will show you the results in the terminal. It will also create a report in the `profile-out` directory.

### 3. Access the Build Scan

Build Scan gives you a detailed look at your build process.

#### How to Access the Build Scan

1.  **Run a Gradle Build:**
    *   Run any Gradle build task in Android Studio (e.g., "Build Project" or "Make Module").
2.  **Find the Build Scan Link:**
    *   After the build finishes, look at the output in the "Build" window.
    *   You'll see a message like this: `Publishing build scan... https://gradle.com/s/xxxxxxxxxxxxxx`
    *   The link (`https://gradle.com/s/xxxxxxxxxxxxxx`) is your Build Scan link.
3.  **Open the Link:**
    *   Click on the link to open the Build Scan in your web browser.
4. **Explore the Build Scan:**
    * You can explore the build scan to see how long each task took, what dependencies were downloaded, and more.

## Tools Used

*   **Build Scan:** A tool to analyze and optimize your Gradle builds.
*   **Gradle Profiler:** A tool to run performance tests on your Gradle builds.
*   **VisualVM:** A tool to monitor the performance of Java applications.
*   **VisualGC:** A VisualVM plugin to monitor garbage collection.

## Contributing

If you have any ideas for improving this project, feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).
