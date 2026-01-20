package model;

/**
 * Repräsentiert eine Quizfrage mit 4 Antwortmöglichkeiten.
 */
public class Question {
    private String id;
    private String text;
    private String[] answers;
    private int correctIndex;

    public Question(String id, String text, String[] answers, int correctIndex) {
        this.id = id;
        this.text = text;
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getCorrectIndex() {
        return correctIndex;
    }

    @Override
    public String toString() {
        return id + ": " + text;
    }
}