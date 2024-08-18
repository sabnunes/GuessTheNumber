import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/**
 * The GameLogic class contains the core logic for the game, including managing the score, generating random numbers,
 * and providing hints based on the player's guesses.
 *
 * @author Sabrina Nunes
 */
public class GameLogic
        implements KeyListener
{
    private static int               number;
    private static int               score;
    private static int               guess;
    private static String            hint;
    private static ArrayList<String> guesses;
    static         boolean           win;

    private final static int    SCORE;
    // Random int method
    private final static int    NUMBER_MAX;
    private final static int    NUMBER_MIN;
    // Guess result text
    private final static String GUESS_TOO_LOW;
    private final static String GUESS_TOO_HIGH;
    private final static String GUESS_CORRECT;
    // Guess result text for ArrayList
    private final static String LIST_GUESS_TOO_LOW;
    private final static String LIST_GUESS_TOO_HIGH;
    private final static String LIST_GUESS_CORRECT;
    // Misc.
    private final static String EMPTY_STRING;

    static
    {
        SCORE = 0;
        // Random int method
        NUMBER_MAX = 100;
        NUMBER_MIN = 1;
        // Guess result text
        GUESS_TOO_LOW  = "Too low! Try again.";
        GUESS_TOO_HIGH = "Too high! Try again.";
        GUESS_CORRECT  = "Correct! The number was ";
        // Guess result text for ArrayList
        LIST_GUESS_TOO_LOW  = "Guessed too low: ";
        LIST_GUESS_TOO_HIGH = "Guessed too high: ";
        LIST_GUESS_CORRECT  = "Guessed correctly: ";
        // Misc.
        EMPTY_STRING = "";
    }

    // instances
    {
        score = SCORE;
    }

    /**
     * Default constructor for GameLogic. Initializes the game state.
     */
    public GameLogic()
    {
        reset();
    }

    /** Resets the game by generating a new random number and clearing guesses. */
    private void reset()
    {
        guesses = new ArrayList<>();
        score   = SCORE;
        win     = false;
        hint    = EMPTY_STRING;
        randomInteger();
    }

    /** Generates a random number between NUMBER_MIN and NUMBER_MAX. */
    private void randomInteger()
    {
        number = ThreadLocalRandom.current()
                                  .nextInt(NUMBER_MIN, NUMBER_MAX + 1);
    }

    /**
     * Returns the list of guesses made by the player.
     *
     * @return the list of guesses
     */
    public static ArrayList<String> getGuesses()
    {
        return guesses;
    }

    /**
     * Checks the player's guess against the generated number and updates the game state accordingly. Also updates the
     * score if correct or incorrect.
     *
     * @param guess the player's guess
     *
     * @return a hint indicating if the guess was too low, too high, or correct
     */
    public void processGuess(final int guess)
    {
        GameLogic.guess = guess;
        if(guess < NUMBER_MIN || guess > NUMBER_MAX)
        {
            hint = GUI.GUESS_INVALID;
        }
        else if(guess < number)
        {
            hint = GUESS_TOO_LOW;
            score++;
            guesses.add(LIST_GUESS_TOO_LOW + guess);
        }
        else if(guess > number)
        {
            hint = GUESS_TOO_HIGH;
            score++;
            guesses.add(LIST_GUESS_TOO_HIGH + guess);
        }
        else
        {
            hint = GUESS_CORRECT + number;
            guesses.add(LIST_GUESS_CORRECT + guess);
            win = true;
        }

        GUI.setHint(hint);
    }

    /**
     * Returns the current score.
     *
     * @return the score
     */
    static int getScore()
    {
        return score;
    }

    /**
     * Invoked when a key has been typed.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * Invoked when a key has been pressed.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    /**
     * Invoked when a key has been released.
     *
     * @param e the KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}