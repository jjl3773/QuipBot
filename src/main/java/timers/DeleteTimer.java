import java.util.TimerTask;

public class DeleteTimer extends TimerTask {
    private GameListener Main;
    private Game game;

    public DeleteTimer(GameListener Main, Game game) {
        this.Main = Main;
        this.game = game;
    }

    @Override
    public void run() {
        System.out.println("game deleted");
        Main.removeGame(game);
    }
}
