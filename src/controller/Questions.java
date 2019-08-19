package controller;

import javax.swing.*;
import java.util.*;

public class Questions {

    private Random r;

    private List<String> questions;
    private List<String[]> answers;

    Questions() {
        r = new Random();

        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }

    /**
     * Adds a question.
     *
     * @param question a question
     * @param answers some answers, the first of which is correct
     */
    void add(String question, String[] answers) {
        questions.add(question);
        this.answers.add(answers);
    }

    /**
     * Asks a random question.
     *
     * @return true if the answer is correct, else false
     */
    boolean ask() {
        return (askQuestion(r.nextInt(questions.size())));
    }

    private boolean askQuestion(int i) {
        String[] answers = getAnswers(i);
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(null,
                    getQuestion(i),
                    null,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    answers,
                    null);
        } while (choice == -1);
        return isCorrect(i, answers[choice]);
    }

    private String getQuestion(int i) {
        return questions.get(i);
    }

    private String[] getAnswers(int i) {
        List<String> answers = new ArrayList<>(Arrays.asList(this.answers.get(i)));
        Collections.shuffle(answers);
        return answers.toArray(new String[0]);
    }

    private boolean isCorrect(int question, String answer) {
        return answers.get(question)[0].equals(answer);
    }

    public static void main(String[] args) {
        Questions questions = new Questions();
        questions.add("Q1", new String[]{"A1", "A2"});
        questions.add("Q2", new String[]{"A1", "A2", "A3"});
        questions.add("Q3", new String[]{"A1", "A2"});
        questions.add("Q4", new String[]{"A1", "A2", "A3", "A4"});

        System.out.println("getQuestion(3) = " + questions.getQuestion(3));

        System.out.println("\ngetAnswers(3) = " + Arrays.toString(questions.getAnswers(3)));

        System.out.println("\nisCorrect(0, \"A1\") = " + questions.isCorrect(0, "A1"));

        System.out.println("\nisCorrect(0, \"A2\") = " + questions.isCorrect(0, "A2"));

        for(int i = 0; i < questions.questions.size(); i++) {
            questions.askQuestion(i);
        }
    }

}
