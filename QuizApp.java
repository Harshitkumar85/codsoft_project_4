import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class QuizApp {
    private JFrame frame;
    private JPanel panel;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup buttonGroup;
    private JButton submitButton;
    private JLabel timerLabel;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    private static final int QUESTION_TIME_LIMIT = 20; // Time limit for each question in seconds

    private String[][] quizData = {
        {"What is the capital of France?", "London", "Berlin", "Paris", "Madrid", "Paris"},
        {"Which planet is known as the Red Planet?", "Mars", "Venus", "Jupiter", "Saturn", "Mars"},
        // Add more questions here...
    };

    public QuizApp() {
        frame = new JFrame("Quiz App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));

        questionLabel = new JLabel();
        panel.add(questionLabel);

        options = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            buttonGroup.add(options[i]);
            panel.add(options[i]);
        }

        submitButton = new JButton("Submit");
        panel.add(submitButton);

        timerLabel = new JLabel();
        panel.add(timerLabel);

        frame.add(panel);
        currentQuestionIndex = 0;
        score = 0;

        timer = new Timer(1000, new ActionListener() {
            private int timeLeft = QUESTION_TIME_LIMIT;

            @Override
            public void actionPerformed(ActionEvent e) {
                timerLabel.setText("Time Left: " + timeLeft + " seconds");
                if (timeLeft == 0) {
                    timer.stop();
                    evaluateAnswer();
                }
                timeLeft--;
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                evaluateAnswer();
            }
        });

        displayNextQuestion();
    }

    private void displayNextQuestion() {
        if (currentQuestionIndex < quizData.length) {
            questionLabel.setText(quizData[currentQuestionIndex][0]);
            for (int i = 0; i < 4; i++) {
                options[i].setText(quizData[currentQuestionIndex][i + 1]);
                options[i].setEnabled(true);
            }
            timerLabel.setText("Time Left: " + QUESTION_TIME_LIMIT + " seconds");
            timer.start();
        } else {
            showResult();
        }
    }

    private void evaluateAnswer() {
        for (int i = 0; i < 4; i++) {
            options[i].setEnabled(false);
        }
        String selectedAnswer = null;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selectedAnswer = options[i].getText();
            }
        }
        if (selectedAnswer != null && selectedAnswer.equals(quizData[currentQuestionIndex][5])) {
            score++;
        }
        currentQuestionIndex++;
        displayNextQuestion();
    }

    private void showResult() {
        questionLabel.setText("Quiz Completed!");
        timerLabel.setText("Your Score: " + score + "/" + quizData.length);
        for (int i = 0; i < 4; i++) {
            options[i].setVisible(false);
        }
        submitButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new QuizApp().frame.setVisible(true);
            }
        });
    }
}
