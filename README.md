# TimelineCursors

A JavaFX application that renders an animated timeline cursor — a vertical line that sweeps across the screen on a fixed cycle, with real-time FPS and position diagnostics.

## What it does

- Draws a vertical cursor line that travels left-to-right across the canvas on a **6-second / 30 fps** cycle
- Renders north and south boundary lines to frame the timeline region
- Displays a live status bar showing elapsed frame time (ms), FPS, canvas dimensions, cursor X position, and pixels-per-frame
- Canvas resizes with the window; layout reflows automatically

## Requirements

- Java 17+
- Maven 3.6+ (or use the included `mvnw` wrapper)
- JavaFX 22 (pulled in automatically via Maven)

## Running

```bash
./mvnw clean javafx:run
```

Or with a system Maven install:

```bash
mvn clean javafx:run
```

## Building a fat JAR

```bash
./mvnw clean package
```

The output is `target/TimelineCursors-1.0-SNAPSHOT-jar-with-dependencies.jar`.

> **Note:** JavaFX modules are not included in the standard JRE. Running the fat JAR directly requires a JDK with JavaFX on the module path. Using `mvn javafx:run` is the recommended way to launch.

## Project structure

```
src/
└── main/
    ├── java/
    │   ├── module-info.java
    │   └── stepheng/timelinecursors/
    │       ├── HelloApplication.java   # Main app + animation loop
    │       └── HelloController.java    # FXML controller (stub)
    └── resources/
        └── stepheng/timelinecursors/
            └── hello-view.fxml         # FXML layout (stub)
```

## Tech stack

| Layer      | Technology          |
|------------|---------------------|
| Language   | Java 17             |
| UI toolkit | JavaFX 22           |
| Build      | Maven + javafx-maven-plugin 0.0.8 |
| Tests      | JUnit Jupiter 5.10.2 |