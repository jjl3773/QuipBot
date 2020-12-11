import java.util.TimerTask;

public class DeleteTimer extends TimerTask {
    private GameListener Main;
    private Game game;

    //constructor of the "DeleteTimer" class
    //takes in a GameListener "Main" and a Game "game"
    public DeleteTimer(GameListener Main, Game game) {
        this.Main = Main;
        this.game = game;
    }

    //post: deletes the inputted game
    @Override
    public void run() {
        Main.removeGame(game);
    }
}
