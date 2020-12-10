import java.util.TimerTask;

public class QuestionTimer extends TimerTask {
    private Game game;

    public QuestionTimer(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.setRecordingPrompts(false);
        game.phase1();
    }
}
