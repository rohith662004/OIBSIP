import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class DifferentNumberGuessingGame extends JFrame {
    private int randomNumber;
    private int attemptsLeft = 10;
    private int score = 0;

    private JTextField userInputField;
    private JButton guessButton;
    private JLabel resultLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public DifferentNumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        generateRandomNumber();

        setVisible(true);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        JPanel southPanel = new JPanel(new FlowLayout());

        userInputField = new JTextField(10);
        guessButton = new JButton("Guess");
        resultLabel = new JLabel("Guess a number between 1 and 100");
        attemptsLabel = new JLabel("Attempts left: " + attemptsLeft);
        scoreLabel = new JLabel("Score: " + score);

        centerPanel.add(userInputField);
        centerPanel.add(guessButton);
        centerPanel.add(resultLabel);

        southPanel.add(attemptsLabel);
        southPanel.add(scoreLabel);

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });
    }

    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
    }

    private void checkGuess() {
        int userGuess;
        try {
            userGuess = Integer.parseInt(userInputField.getText());
        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter a valid number.");
            userInputField.setText("");
            return;
        }

        attemptsLeft--;
        attemptsLabel.setText("Attempts left: " + attemptsLeft);

        if (userGuess == randomNumber) {
            resultLabel.setText("Congratulations! You've guessed the number.");
            score += attemptsLeft * 10; // Adjust score based on attempts left
            scoreLabel.setText("Score: " + score);
            newRound();
        } else if (userGuess < randomNumber) {
            resultLabel.setText("Higher!");
        } else {
            resultLabel.setText("Lower!");
        }

        if (attemptsLeft == 0) {
            resultLabel.setText("Sorry, you've run out of attempts. The correct number was: " + randomNumber);
            newRound();
        }

        userInputField.setText("");
    }

    private void newRound() {
        attemptsLeft = 10;
        attemptsLabel.setText("Attempts left: " + attemptsLeft);
        resultLabel.setText("Guess a number between 1 and 100"); // Provide instruction for the new round
        generateRandomNumber();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DifferentNumberGuessingGame();
            }
        });
    }
}
