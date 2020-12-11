import net.dv8tion.jda.api.entities.User;

import java.util.*;

public class Player {
    private User user;
    private String prompt;
    private List<String> firstQuestion;
    private List<String> secondQuestion;
    private String dmId;
    private Game game;

    //constructor of the "Player" class
    //takes in a User "user"
    public Player(User user) {
        this.user = user;
        this.prompt = "";
        this.firstQuestion = new ArrayList<>();
        this.secondQuestion = new ArrayList<>();
    }

    //post: returns the user associated with this player
    public User getUser() {
        return this.user;
    }

    //pre: a String "prompt"
    //post: sets this player's prompt as the inputted prompt
    public void setPrompt(String prompt) {
        this.prompt = prompt;
        game.addCumulativeQuestions(prompt);
    }

    //post: returns the prompt
    public String getPrompt() {
        return this.prompt;
    }

    //pre: a String "addition"
    //post: makes an addition to this players first question list
    public void addToFirstQuestion(String addition) {
        this.firstQuestion.add(addition);
    }

    //post: returns the first question list
    public List<String> getFirstQuestion() {
        return this.firstQuestion;
    }

    //pre: a String "addition"
    //post: makes an addition to this players second question list
    public void addToSecondQuestion(String addition) {
        this.secondQuestion.add(addition);
    }

    //post: returns the second question list
    public List<String> getSecondQuestion() {
        return this.secondQuestion;
    }

    //pre: a String "Id"
    //post: sets this players dmId as "Id"
    public void setDmId(String Id) {
        this.dmId = Id;
    }

    //post: returns this players dmId
    public String getDmId() {
        return this.dmId;
    }

    //pre: a Game "game"
    //post: associates this player with a game
    public void setGame(Game game) {
        this.game = game;
    }

    //post: clears all information regarding the current game
    public void clear() {
        this.prompt = "";
        this.firstQuestion = new ArrayList<>();
        this.secondQuestion = new ArrayList<>();
    }
}
