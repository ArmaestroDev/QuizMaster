package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * Verwaltet den Spielzustand und die Logik (Datenhaltung, Punkte, Ablauf).
 */
public class GameModel extends Observable {
    private CsvHandler csvHandler;
    private List<Question> allQuestions;
    private List<User> allUsers;

    // Aktueller Spielzustand
    private User currentUser;
    private User player2;
    public boolean isMultiplayer = false;
    private int currentPlayerIndex = 0;

    private List<Question> gameQuestions;
    private int currentQuestionIndex;
    private int[] currentScores;

    public GameModel() {
        csvHandler = new CsvHandler();
        allQuestions = csvHandler.loadQuestions();
        allUsers = csvHandler.loadUsers();
    }

    public boolean userExists(String username) {
        return allUsers.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    public void register(String username) {
        if (!userExists(username)) {
            currentUser = new User(username, 0, false);
            allUsers.add(currentUser);
            csvHandler.saveUsers(allUsers);
            login(username);
        }
    }

    public void login(String username) {
        currentUser = allUsers.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);

        if (currentUser != null) {
            setChanged();
            notifyObservers("LOGIN_SUCCESS");
        }
    }

    public void logout() {
        currentUser = null;
        setChanged();
        notifyObservers("LOGOUT");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public void startSinglePlayer() {
        isMultiplayer = false;
        prepareGame();
    }

    public void startMultiplayer(String player2Name) {
        isMultiplayer = true;
        if (!player2Name.isEmpty() && userExists(player2Name)) {
            login(player2Name);
        }
        player2 = new User(player2Name, 0, false);
        currentPlayerIndex = 0;
        prepareGame();
    }

    private void prepareGame() {
        gameQuestions = new ArrayList<>(allQuestions);
        Collections.shuffle(gameQuestions);
        if (gameQuestions.size() > 10) {
            gameQuestions = gameQuestions.subList(0, 10);
        }
        currentQuestionIndex = 0;
        currentScores = new int[] { 0, 0 };

        setChanged();
        notifyObservers("GAME_START");
        setChanged();
        notifyObservers(getCurrentQuestion());
    }

    public Question getCurrentQuestion() {
        if (currentQuestionIndex < gameQuestions.size()) {
            return gameQuestions.get(currentQuestionIndex);
        }
        return null;
    }

    public void answerQuestion(int answerIndex) {
        Question q = getCurrentQuestion();
        boolean correct = (q.getCorrectIndex() == answerIndex);

        // Punkte berechnen (+10 / -5)
        int points = correct ? 10 : -5;
        currentScores[currentPlayerIndex] += points;

        setChanged();
        notifyObservers(correct ? "ANSWER_CORRECT" : "ANSWER_WRONG");

        if (isMultiplayer) {
            currentPlayerIndex = (currentPlayerIndex == 0) ? 1 : 0;
            currentQuestionIndex++;
        } else {
            currentQuestionIndex++;
        }

        if (currentQuestionIndex >= gameQuestions.size()) {
            finishGame();
        } else {
            setChanged();
            notifyObservers(getCurrentQuestion());
            setChanged();
            notifyObservers("UPDATE_SCORES");
        }
    }

    private void finishGame() {
        if (currentScores[0] > currentUser.getUserHighscore()) {
            currentUser.setHighscore(currentScores[0]);
            csvHandler.saveUsers(allUsers);
            setChanged();
            notifyObservers("NEW_HIGHSCORE");
        }
        setChanged();
        notifyObservers("GAME_OVER");
    }

    public String getScoreString() {
        if (isMultiplayer) {
            return currentUser.getUsername() + ": " + currentScores[0] + " | " + player2.getUsername() + ": "
                    + currentScores[1];
        }
        return "Punkte: " + currentScores[0];
    }

    public String getCurrentPlayerName() {
        if (!isMultiplayer)
            return currentUser.getUsername();
        return (currentPlayerIndex == 0) ? currentUser.getUsername() : player2.getUsername();
    }

    public void addQuestion(String text, String[] answers, int correctIdx) {
        String newId = String.valueOf(allQuestions.size() + 1);
        Question q = new Question(newId, text, answers, correctIdx);
        allQuestions.add(q);
        csvHandler.saveQuestion(q);
        setChanged();
        notifyObservers("QUESTION_ADDED");
    }

    public void deleteQuestion(Question q) {
        allQuestions.remove(q);
        csvHandler.rewriteQuestions(allQuestions);
        setChanged();
        notifyObservers("DATA_CHANGED");
    }

    public void updateQuestion(int indexInList, String txt, String[] ans, int corrIdx) {
        Question q = allQuestions.get(indexInList);
        Question newQ = new Question(q.getId(), txt, ans, corrIdx);
        allQuestions.set(indexInList, newQ);
        csvHandler.rewriteQuestions(allQuestions);
        setChanged();
        notifyObservers("DATA_CHANGED");
    }

    public void addUser(String name, int score, boolean isEditor) {
        allUsers.add(new User(name, score, isEditor));
        csvHandler.rewriteUsers(allUsers);
        setChanged();
        notifyObservers("DATA_CHANGED");
    }

    public void deleteUser(User u) {
        allUsers.remove(u);
        csvHandler.rewriteUsers(allUsers);
        setChanged();
        notifyObservers("DATA_CHANGED");
    }

    public void updateUser(int indexInList, String name, int score, boolean isEditor) {
        User u = allUsers.get(indexInList);
        u.setUsername(name);
        u.setHighscore(score);
        u.setEditor(isEditor);
        csvHandler.rewriteUsers(allUsers);
        setChanged();
        notifyObservers("DATA_CHANGED");
    }

    public String getProgressString() {
        if (gameQuestions == null || gameQuestions.isEmpty()) {
            return "";
        }
        return "Frage " + (currentQuestionIndex + 1) + " / " + gameQuestions.size();
    }

    public List<Question> getQuestionList() {
        return allQuestions;
    }

    public List<User> getUserList() {
        return allUsers;
    }
}