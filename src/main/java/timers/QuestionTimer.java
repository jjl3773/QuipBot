import java.util.TimerTask;

public class QuestionTimer extends TimerTask {
    private Game game;

    //Constructor of the "QuestionTimer" class
    //takes in a Game "game"
    public QuestionTimer(Game game) {
        this.game = game;
    }

    //post: initiates "phase 1" of the game
    @Override
    public void run() {
        game.setRecordingPrompts(false);
        game.phase1();
    }
}
