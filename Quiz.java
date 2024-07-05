package codsoft;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    String questionText;
    String[] options;
    int correctAnswerIndex;

    public Question(String questionText, String[] options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}

class Quiz {
    private Question[] questions;
    private int score;
    private int currentQuestionIndex;
    private boolean answerSubmitted;
    private int[] userAnswers;
    private Timer timer;
    private final int TIME_LIMIT = 10; // time limit for each question in seconds

    public Quiz(Question[] questions) {
        this.questions = questions;
        this.score = 0;
        this.currentQuestionIndex = 0;
        this.userAnswers = new int[questions.length];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1; // Initialize with -1 indicating no answer
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (currentQuestionIndex < questions.length) {
            displayQuestion(currentQuestionIndex);
            answerSubmitted = false;

            // Start timer for the question
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!answerSubmitted) {
                        System.out.println("\nTime's up!");
                        moveToNextQuestion();
                    }
                }
            }, TIME_LIMIT * 1000);

            // Take user's answer
            int userAnswer = scanner.nextInt();
            answerSubmitted = true;
            timer.cancel(); // Cancel the timer if answer is submitted
            userAnswers[currentQuestionIndex] = userAnswer;

            if (userAnswer == questions[currentQuestionIndex].correctAnswerIndex) {
                score = score + 1;
            }
            moveToNextQuestion();
        }
        displayResult();
        scanner.close();
    }

    private void displayQuestion(int questionIndex) {
        Question question = questions[questionIndex];
        System.out.println("Question " + (questionIndex + 1) + ": " + question.questionText);
        for (int i = 0; i < question.options.length; i++) {
            System.out.println((i + 1) + ". " + question.options[i]);
        }
        System.out.print("Your answer (1-" + question.options.length + "): ");
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
    }

    private void displayResult() {
        System.out.println("\nQuiz Over!");
        System.out.println("Your score: " + score + "/" + questions.length);
        System.out.println("Summary:");
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i].questionText);
            System.out.println("Your answer: " + (userAnswers[i] == -1 ? "No answer" : questions[i].options[userAnswers[i] - 1]));
            System.out.println("Correct answer: " + questions[i].options[questions[i].correctAnswerIndex]);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Question[] questions = {
            new Question("What is the capital of France?", new String[]{"Paris", "London", "Berlin", "Madrid"}, 1),
            new Question("Who wrote 'To Kill a Mockingbird'?", new String[]{"Harper Lee", "Mark Twain", "Ernest Hemingway", "F. Scott Fitzgerald"}, 1),
            new Question("What is the largest planet in our solar system?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 3)
        };

        Quiz quiz = new Quiz(questions);
        quiz.start();
    }
}

