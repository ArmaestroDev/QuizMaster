package model;

/**
 * Repräsentiert einen Benutzer mit Name, Score und Rechten.
 */
public class User implements Comparable<User> {
    private String username;
    private int highscore;
    private boolean isEditor;

    public User(String username, int highscore, boolean isEditor) {
        this.username = username;
        this.highscore = highscore;
        this.isEditor = isEditor;
    }

    public String getUsername() {
        return username;
    }

    public int getUserHighscore() {
        return highscore;
    }

    public boolean isEditor() {
        return isEditor;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    @Override
    public int compareTo(User o) {
        // Sortierung absteigend nach Highscore
        return Integer.compare(o.highscore, this.highscore);
    }

    @Override
    public String toString() {
        return username + " (Score: " + highscore + ")" + (isEditor ? " [Admin]" : "");
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEditor(boolean editor) {
        isEditor = editor;
    }
}