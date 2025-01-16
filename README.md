# Flappy Bird in Java (AWT/Swing)

## Overview
This project is a simple recreation of the popular game "Flappy Bird" using Java's AWT and Swing libraries. It includes a game loop, graphical rendering, random pipe generation, collision detection, and a scoring system.

## Features
- **Game Loop**: A timer-driven game loop updates the game state and redraws the screen at a steady frame rate.
- **Graphics**: Background, bird, and pipes are drawn on a JPanel using images.
- **Player Interaction**: The bird jumps when the space key is pressed.
- **Pipe Generation**: Pipes are randomly positioned and move across the screen.
- **Collision Detection**: The game ends if the bird collides with a pipe or the ground.
- **Scoring System**: The player scores points for successfully passing through pipes.

## Getting Started
Follow these steps to run the game on your local machine.

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- An IDE like IntelliJ IDEA, Eclipse, or a text editor with Java support

### Installation
1. Clone or download this repository to your local machine.
2. Open the project in your IDE.
3. Ensure the images for the background, bird, and pipes are in the same directory as the `FlappyBird.java` file. The required images are:
   - `flappybirdbg.png` (background)
   - `flappybird.png` (bird)
   - `toppipe.png` (top pipe)
   - `bottompipe.png` (bottom pipe)
4. Compile and run the `FlappyBird` class.

## How the Code Works

### 1. Game Loop
A `javax.swing.Timer` is used to update the game state and repaint the screen at regular intervals (60 times per second).

### 2. JFrame and JPanel
- A `JFrame` is created to hold the game panel.
- The `FlappyBird` class extends `JPanel` and handles rendering and game logic.

### 3. Drawing Images
The `paintComponent(Graphics g)` method is overridden to draw the background, bird, and pipes on the panel.

### 4. Player Interaction
The `KeyListener` interface is implemented to detect when the space key is pressed, making the bird "jump" by adjusting its vertical velocity.

### 5. Random Pipe Generation
Pipes are generated at random vertical positions with a fixed gap between the top and bottom pipes. A `java.util.Timer` adds new pipes every 1.5 seconds.

### 6. Moving Pipes
Pipes move left across the screen, simulating forward motion. If a pipe moves out of the screen, it is removed from memory.

### 7. Collision Detection
The `collision()` method checks if the bird's bounding box intersects with any pipe or if it hits the ground.

### 8. Scoring System
The score increases when the bird successfully passes a set of pipes.

## How to Play
1. Press the space key to make the bird jump.
2. Avoid colliding with pipes or the ground.
3. Try to pass through as many pipes as possible to achieve a high score.

## Future Improvements
- Add sound effects for jumps and collisions.
- Implement multiple difficulty levels.
- Add a restart button instead of pressing space after a game over.

## Tools Used
This project was developed using Visual Studio Code as the primary code editor.

## Contributing
Contributions are welcome! If you have ideas for improving the game or fixing bugs, feel free to submit a pull request.

## License
This project is open source and available under the MIT License.
