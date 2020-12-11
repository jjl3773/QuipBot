import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.util.TimerTask;

public class StartTimer extends TimerTask {
    private MessageChannel channel;
    private Game game;

    //constructor of the "StartTimer" class
    //takes in a Game "game" and MessageChannel "channel"
    public StartTimer(Game game, MessageChannel channel) {
        this.channel = channel;
        this.game = game;
    }

    //initiates "phase2" of the current game
    @Override
    public void run() {
        channel.sendMessage("http://www.lilynow.com/wp-content/uploads/2020/12/votingTime2.png").queue();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Phase 2: Time to vote!**");
        eb.setDescription("Use the **reaction icons** to cast your vote\n_You have 10 seconds to pick your favorite answer_");
        eb.setColor(new Color(93, 176, 235));
        channel.sendMessage(eb.build()).queue();
        game.setRecordingResponses(false);
        game.phase2();
    }
}
