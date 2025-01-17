import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // To store all the pipes in the game
import java.util.Random;    // To place pipes at random positions
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    // Size of the game screen
    int boardWidth = 360;
    int boardHeight = 640;

    // Pictures used in the game
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Starting position and size of the bird
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    // This class is for the bird in the game
    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img; // The bird's picture
        }
    }

    // Starting position and size of the pipes
    int pipeX = boardWidth;     // Pipes start off the screen to the right
    int pipeY = 0;             // Pipes' top starts from the top of the screen
    int pipeWidth = 64;        // Width of the pipes
    int pipeHeight = 512;      // Height of the pipes

    // This class is for the pipes in the game
    class Pipe {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; // To check if the bird has passed this pipe

        Pipe(Image img) {
            this.img = img; // The pipe's picture
        }
    }

    // Game rules and logic
    Bird bird; // The bird object
    int velocityX = -4; // How fast pipes move to the left
    int velocityY = 0;  // How fast the bird moves up or down
    int gravity = 1;    // Makes the bird fall down

    ArrayList<Pipe> pipes; // List to keep all the pipes
    Random random = new Random(); // For random pipe positions

    Timer gameLoop;      // Timer to keep the game running
    Timer placePipeTimer; // Timer to add new pipes
    boolean gameOver = false; // To check if the game is over
    double score = 0;    // The player's score

    // This is the main setup for the game
    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight)); // Set the size of the screen
        setFocusable(true); // Make the screen able to listen to key presses
        addKeyListener(this); // Add key listener for the space key

        // Load the pictures for the game
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        // Make the bird and pipes
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // Timer to add pipes every 1.5 seconds
        placePipeTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes(); // Add new pipes to the game
            }
        });
        placePipeTimer.start(); // Start the timer for pipes

        // Timer for the game to keep updating every frame
        gameLoop = new Timer(1000 / 60, this); // 60 frames per second
        gameLoop.start(); // Start the game loop
    }

    // This adds new pipes to the game
    void placePipes() {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4; // Space between the top and bottom pipes

        // Create the top pipe
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        // Create the bottom pipe
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    // Draw everything on the screen
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // Draw the bird, pipes, and background
    public void draw(Graphics g) {
        // Draw the background
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);

        // Draw the bird
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);

        // Draw the pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Show the score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + (int) score, 10, 35); // Show "Game Over" with the score
        } else {
            g.drawString(String.valueOf((int) score), 10, 35); // Show the current score
        }
    }

    // This updates the game (bird and pipes movement)
    public void move() {
        // Move the bird down due to gravity
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); // Stop the bird from going above the screen

        // Move pipes to the left
        for (Pipe pipe : pipes) {
            pipe.x += velocityX; // Move pipe left

            // Check if the bird passed the pipe
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5; // Add to the score when the bird passes pipes
                pipe.passed = true; // Mark this pipe as passed
            }

            // Check if the bird hits a pipe
            if (collision(bird, pipe)) {
                gameOver = true; // End the game if the bird hits a pipe
            }
        }

        // End the game if the bird falls below the screen
        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    // Check if the bird hits a pipe
    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   // Check if bird's left touches pipe's right
               a.x + a.width > b.x &&   // Check if bird's right touches pipe's left
               a.y < b.y + b.height &&  // Check if bird's top touches pipe's bottom
               a.y + a.height > b.y;    // Check if bird's bottom touches pipe's top
    }

    // This runs every frame (game loop)
    @Override
    public void actionPerformed(ActionEvent e) {
        move(); // Move the bird and pipes
        repaint(); // Redraw the screen
        if (gameOver) {
            placePipeTimer.stop(); // Stop adding new pipes
            gameLoop.stop();      // Stop the game
        }
    }

    // When the space key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            velocityY = -9; // Make the bird jump up

            if (gameOver) {
                // Restart the game if it is over
                bird.y = birdY;
                velocityY = 0;
                pipes.clear(); // Remove all pipes
                gameOver = false;
                score = 0;
                gameLoop.start(); // Start the game again
                placePipeTimer.start(); // Start adding pipes again
            }
        }
    }

    // These are not used
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
