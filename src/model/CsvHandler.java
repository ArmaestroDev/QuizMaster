package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Liest und schreibt CSV-Dateien für Fragen und User.
 */
public class CsvHandler {
    private static final String QUESTIONS_FILE = "questions.csv";
    private static final String USERS_FILE = "users.csv";
    private static final String DELIMITER = ";";

    public List<Question> loadQuestions() {
        List<Question> list = new ArrayList<>();
        File file = new File(QUESTIONS_FILE);
        if (!file.exists())
            createDummyQuestions();

        try (BufferedReader br = new BufferedReader(new FileReader(QUESTIONS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 7) {
                    String[] answers = { parts[2], parts[3], parts[4], parts[5] };
                    list.add(new Question(parts[0], parts[1], answers, Integer.parseInt(parts[6])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists())
            createDummyUsers();

        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(DELIMITER);
                if (parts.length >= 3) {
                    boolean isEditor = "1".equals(parts[2]);
                    list.add(new User(parts[0], Integer.parseInt(parts[1]), isEditor));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) {
                String editorBit = u.isEditor() ? "1" : "0";
                bw.write(u.getUsername() + DELIMITER + u.getUserHighscore() + DELIMITER + editorBit);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveQuestion(Question q) {
        // Append mode
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(QUESTIONS_FILE, true))) {
            bw.write(q.getId() + DELIMITER + q.getText() + DELIMITER +
                    q.getAnswers()[0] + DELIMITER + q.getAnswers()[1] + DELIMITER +
                    q.getAnswers()[2] + DELIMITER + q.getAnswers()[3] + DELIMITER +
                    q.getCorrectIndex());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDummyQuestions() {
        // Fallback, falls Datei fehlt
        try (PrintWriter pw = new PrintWriter(new File(QUESTIONS_FILE))) {
            pw.println("1;Java ist eine...;Insel;Programmiersprache;Kaffeesorte;Auto;1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createDummyUsers() {
        try (PrintWriter pw = new PrintWriter(new File(USERS_FILE))) {
            pw.println("admin;0;1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void rewriteUsers(List<User> users) {
        saveUsers(users);
    }

    public void rewriteQuestions(List<Question> questions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(QUESTIONS_FILE))) {
            for (Question q : questions) {
                bw.write(q.getId() + DELIMITER + q.getText() + DELIMITER +
                        q.getAnswers()[0] + DELIMITER + q.getAnswers()[1] + DELIMITER +
                        q.getAnswers()[2] + DELIMITER + q.getAnswers()[3] + DELIMITER +
                        q.getCorrectIndex());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}