import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The GUI class handles the graphical user interface for the game. It provides methods to initialize and update the
 * game's visual components.
 *
 * @author Sabrina Nunes
 */
public class GUI
{
    private static GameLogic gameLogic;

    // Main frame, panels, and labels
    private static final JFrame FRAME;
    private static final JPanel panelMain, panelInstruction, panelInputs, panelScore, panelHint;
    private static final JLabel labelInstruction, labelScore, labelHint;
    private static final JTextField guessField;
    private static final JButton    submitGuess;
    // Frame details
    private final static int        FRAME_SIZE_WIDTH;
    private final static int        FRAME_SIZE_HEIGHT;
    private final static String     FRAME_TITLE;
    // Menu bar options
    private final static String     MENU_OPTIONS;
    private final static String     MENU_OPTION_INSTRUCTIONS;
    private final static String     MENU_OPTION_FEEDBACK;
    private final static String     MENU_OPTION_RESTART;
    private final static String     MENU_OPTION_EXIT;
    // Menu bar options tooltips
    private final static String     MENU_OPTIONS_TOOLTIP_INSTRUCTIONS;
    private final static String     MENU_OPTIONS_TOOLTIP_FEEDBACK;
    private final static String     MENU_OPTIONS_TOOLTIP_RESTART;
    private final static String     MENU_OPTIONS_TOOLTIP_EXIT;
    // Window titles
    private final static String     WINDOW_TITLE_FEEDBACK;
    private final static String     WINDOW_TITLE_GUESS_HISTORY;
    // Window content
    private final static String     INSTRUCTIONS;
    private final static String     FEEDBACK_FILE_NAME;
    // Buttons
    private final static String     BUTTON_SUBMIT_GUESS;
    // UI format
    private final static int        TEXT_FIELD_SPACING;
    private final static int        INSET_TOP, INSET_LEFT, INSET_BOT, INSET_RIGHT;
    private final static int GRIDX_0;
    private final static int GRIDY_0, GRIDY_1, GRIDY_2, GRIDY_3;
    // Misc.
    private final static String EMPTY_STRING, BLANK_STRING;
    // Main window
    private final static String LABEL_INSTRUCTION;
    private final static String LABEL_SCORE;
    public static final  int    SCORE = 0;
    // Main window text // NOT private, accessed in GameLogic
    public final static  String GUESS_INVALID;

    static
    {
        // Frame details
        FRAME_SIZE_WIDTH  = 640; // VGA (NTSC) 640x480; SVGA 800x600
        FRAME_SIZE_HEIGHT = 480; // VGA (NTSC) 640x480; SVGA 800x600
        FRAME_TITLE       = "Guess the Number Game";
        // Toolbar menu options
        MENU_OPTIONS             = "Options";
        MENU_OPTION_INSTRUCTIONS = "How to Play";
        MENU_OPTION_FEEDBACK     = "Send Feedback";
        MENU_OPTION_RESTART      = "Restart Game";
        MENU_OPTION_EXIT         = "<html><left><b><u>E</u>xit</b>";
        // Toolbar menu options tooltips
        MENU_OPTIONS_TOOLTIP_INSTRUCTIONS = "Learn how to play this very simple game.";
        MENU_OPTIONS_TOOLTIP_FEEDBACK     = "We take all feedback to heart.";
        MENU_OPTIONS_TOOLTIP_RESTART      = "Restart the game.";
        MENU_OPTIONS_TOOLTIP_EXIT         = "Exit the game.";
        // Window titles
        WINDOW_TITLE_FEEDBACK      = "Enter your feedback:";
        WINDOW_TITLE_GUESS_HISTORY = "Guess History";
        // Window content
        INSTRUCTIONS = ("Instructions:\n" + "1. Enter a number between 1 and 100 in the text field.\n"
                        + "2. Click 'Submit Guess' to check your guess.\n"
                        + "3. If your guess is too low or too high, you will receive feedback.\n"
                        + "4. Keep guessing until you find the correct number.\n"
                        + "5. Your score will be displayed and updated after each guess.\n"
                        + "6. You can restart the game or exit from the 'Options' menu.");

        FEEDBACK_FILE_NAME = "feedback.txt";
        // Buttons
        BUTTON_SUBMIT_GUESS = "Submit Guess";
        // UI format
        TEXT_FIELD_SPACING = 20;
        INSET_TOP          = 0;
        INSET_LEFT         = 5;
        INSET_BOT          = 5;
        INSET_RIGHT        = 5;
        GRIDX_0            = 0;
        GRIDY_0            = 0;
        GRIDY_1            = 1;
        GRIDY_2            = 2;
        GRIDY_3            = 3;
        // Misc.
        EMPTY_STRING = "";
        BLANK_STRING = " ";
        // Main window text
        LABEL_INSTRUCTION = "Guess a number between 1 and 100:";
        LABEL_SCORE       = "Score: ";
        // Main window text -- public
        GUESS_INVALID = "Please enter a number between 1 and 100.";

        // Main frame
        FRAME = new JFrame(FRAME_TITLE);
        // Panel main
        panelMain = new JPanel(new GridBagLayout());
        // Panels
        panelInstruction = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInputs      = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelScore       = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHint        = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Labels
        labelInstruction = new JLabel(LABEL_INSTRUCTION);
        labelScore       = new JLabel(LABEL_SCORE + GameLogic.getScore());
        labelHint        = new JLabel(BLANK_STRING);

        // Guess field and button
        guessField  = new JTextField(TEXT_FIELD_SPACING);
        submitGuess = new JButton(BUTTON_SUBMIT_GUESS);
    }

    /** Initializes the graphical user interface. */
    public static void initializeGUI()
    {
        gameLogic = new GameLogic();

        final GridBagConstraints gbc;
        gbc = new GridBagConstraints();

        // Frame details
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setSize(FRAME_SIZE_WIDTH, FRAME_SIZE_HEIGHT);
        FRAME.setLayout(new BorderLayout());

        createMenuBar();
        createKeyListener();

        // Main panel
        gbc.insets = new Insets(INSET_TOP, INSET_LEFT, INSET_BOT, INSET_RIGHT); // control spacing between components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the left

        // Add KeyListener to the submitGuess field to handle Enter key press
        submitGuess.addActionListener(e->submitGuess());
        // Add KeyListener to the guessField field to handle Enter key press
        guessField.addActionListener(e->submitGuess());

        // Adding labels, field, and button to panels
        panelInstruction.add(labelInstruction);
        panelInputs.add(guessField);
        panelInputs.add(submitGuess);
        panelScore.add(labelScore);
        panelHint.add(labelHint);

        // Adding panels to main panel and formatting
        gbc.gridx = GRIDX_0;
        gbc.gridy = GRIDY_0;
        panelMain.add(panelInstruction, gbc);

        gbc.gridx = GRIDX_0;
        gbc.gridy = GRIDY_1;
        panelMain.add(panelInputs, gbc);

        gbc.gridx = GRIDX_0;
        gbc.gridy = GRIDY_2;
        panelMain.add(panelScore, gbc);

        gbc.gridx = GRIDX_0;
        gbc.gridy = GRIDY_3;
        panelMain.add(panelHint, gbc);

        // Adding main panel to frame
        FRAME.add(panelMain);

        // Set the frame visible
        FRAME.setVisible(true);
        FRAME.requestFocusInWindow();

        // Add key listener to the frame
        FRAME.addKeyListener((KeyListener) gameLogic);
    }

    /** Handles the guess input from the user and updates the game state. */
    private static void submitGuess()
    {
        try
        {
            final int guess;
            guess = Integer.parseInt(guessField.getText());
            gameLogic.processGuess(guess);
        } catch(NumberFormatException e)
        {
            labelHint.setText(GUESS_INVALID);
        }
        guessField.setText(EMPTY_STRING);

        if(GameLogic.win)
        {
            win();
        }
        setScore(GameLogic.getScore());
    }

    /**
     * Sets the hint label text.
     *
     * @param hint the hint text
     */
    public static void setHint(final String hint)
    {
        labelHint.setText(hint);
    }

    /**
     * Sets the score label text.
     *
     * @param score the score value
     */
    public static void setScore(final int score)
    {
        labelScore.setText(LABEL_SCORE + score);
    }

    /** Creates and sets the KeyListener parameters. */
    static void createKeyListener()
    {
        // Adding KeyListener to close the window with the "Escape" or "E" key
        FRAME.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_E)
                {
                    System.exit(0);
                }
                if(e.getKeyCode() == KeyEvent.VK_I)
                {
                    instructions();
                }
                if(e.getKeyCode() == KeyEvent.VK_F)
                {
                    sendFeedback();
                }
                if(e.getKeyCode() == KeyEvent.VK_R)
                {
                    restartGame();
                }
                if(e.getKeyCode() == KeyEvent.VK_W)
                {
                    win();
                }
                if(e.getKeyCode() == KeyEvent.VK_G)
                {
                    submitGuess();
                }
            }
        });

        // Ensure the frame is focusable to capture key events
        FRAME.setFocusable(true);
        FRAME.requestFocusInWindow();
    }

    /** Creates the menu bar. */
    private static void createMenuBar()
    {
        // Menu bar options
        final JMenuBar  menuBar;
        final JMenu     menuOptions;
        final JMenuItem menuOptionHowToPlay;
        final JMenuItem menuOptionSendFeedback;
        final JMenuItem menuOptionRestartGame;
        final JMenuItem menuOptionExit;

        // instances
        menuBar                = new JMenuBar();
        menuOptions            = new JMenu(MENU_OPTIONS);
        menuOptionHowToPlay    = new JMenuItem(MENU_OPTION_INSTRUCTIONS);
        menuOptionSendFeedback = new JMenuItem(MENU_OPTION_FEEDBACK);
        menuOptionRestartGame  = new JMenuItem(MENU_OPTION_RESTART);
        menuOptionExit         = new JMenuItem(MENU_OPTION_EXIT);

        // Toolbar menu options actions
        menuOptionHowToPlay.addActionListener(e->instructions());
        menuOptionSendFeedback.addActionListener(e->sendFeedback());
        menuOptionRestartGame.addActionListener(e->restartGame());
        menuOptionExit.addActionListener(e->{System.exit(0);});

        // Toolbar menu options tooltips
        menuOptionHowToPlay.setToolTipText(MENU_OPTIONS_TOOLTIP_INSTRUCTIONS);
        menuOptionSendFeedback.setToolTipText(MENU_OPTIONS_TOOLTIP_FEEDBACK);
        menuOptionRestartGame.setToolTipText(MENU_OPTIONS_TOOLTIP_RESTART);
        menuOptionExit.setToolTipText(MENU_OPTIONS_TOOLTIP_EXIT);

        // Adding elements to menu bar
        menuBar.add(menuOptions);
        menuOptions.add(menuOptionHowToPlay);
        menuOptions.add(menuOptionSendFeedback);
        menuOptions.add(menuOptionRestartGame);
        menuOptions.add(menuOptionExit);

        // Add menu bar to frame
        FRAME.setJMenuBar(menuBar);
    }

    /** Displays an instructions message pop up message. */
    private static void instructions()
    {
        JOptionPane.showMessageDialog(FRAME, INSTRUCTIONS, MENU_OPTION_INSTRUCTIONS, JOptionPane.INFORMATION_MESSAGE);
    }

    /** Displays a feedback dialog to the user, collects user feedback, and stores it in a text file. */
    private static void sendFeedback()
    {
        // Create a JTextField for user input
        final JTextField feedbackField;
        final int        option;

        feedbackField = new JTextField();

        // Show the dialog with the JTextField and OK button
        option = JOptionPane.showConfirmDialog(FRAME, feedbackField, WINDOW_TITLE_FEEDBACK,
                                               JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        // Check if the OK button was pressed and the text field is not empty
        if(option == JOptionPane.OK_OPTION)
        {
            String feedback;
            feedback = feedbackField.getText().trim();

            if(!feedback.isEmpty())
            {
                try(BufferedWriter writer = new BufferedWriter(new FileWriter(FEEDBACK_FILE_NAME, true)))
                {
                    writer.write(feedback);
                    writer.newLine();
                } catch(IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }

    }

    /** Restarts the game by resetting the game logic and UI components. */
    private static void restartGame()
    {
        final GameLogic logic;
        logic = new GameLogic();
        setHint(BLANK_STRING);
        setScore(SCORE);
    }

    /** Displays the guess history to the user. */
    static void win()
    {
        final ArrayList<String>        guesses;
        final DefaultListModel<String> listModel;
        final JList<String>            guessList;
        final JScrollPane              scrollPane;

        guesses    = GameLogic.getGuesses();
        listModel  = new DefaultListModel<>();
        guessList  = new JList<>(listModel);
        scrollPane = new JScrollPane(guessList);

        for(String guess : guesses)
        {
            listModel.addElement(guess);
        }

        JOptionPane.showMessageDialog(FRAME, scrollPane, WINDOW_TITLE_GUESS_HISTORY, JOptionPane.INFORMATION_MESSAGE);
    }
}