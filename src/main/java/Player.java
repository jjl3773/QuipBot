import net.dv8tion.jda.api.entities.User;

import java.util.*;

public class Player {
    private User user;
    private String prompt;
    private List<String> firstQuestion;
    private List<String> secondQuestion;
    private String dmId;
    private Game game;

    public Player(User user) {
        this.user = user;
        this.prompt = "";
        this.firstQuestion = new ArrayList<>();
        this.secondQuestion = new ArrayList<>();
    }

    public User getUser() {
        return this.user;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
        game.addCumulativeQuestions(prompt);
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void addToFirstQuestion(String addition) {
        this.firstQuestion.add(addition);
    }

    public List<String> getFirstQuestion() {
        return this.firstQuestion;
    }

    public void addToSecondQuestion(String addition) {
        this.secondQuestion.add(addition);
    }

    public List<String> getSecondQuestion() {
        return this.secondQuestion;
    }

    public void setDmId(String Id) {
        this.dmId = Id;
    }

    public String getDmId() {
        return this.dmId;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void clear() {
        this.prompt = "";
        this.firstQuestion = new ArrayList<>();
        this.secondQuestion = new ArrayList<>();
    }
}

