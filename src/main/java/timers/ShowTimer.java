import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowTimer extends TimerTask {
    private List<String> questions;
    private Game game;
    private String previousId;
    private MessageChannel channel;
    private Player[] previousUsers;

    public ShowTimer(Game game, MessageChannel channel, List<String> questions) {
        this(game, channel, questions, "", null);
    }

    public ShowTimer(Game game, MessageChannel channel, List<String> questions, String previousId, Player[] previousUsers) {
        this.questions = questions;
        this.game = game;
        this.previousId = previousId;
        this.channel = channel;
        this.previousUsers = previousUsers;
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        if (!previousId.equals("")) {
            List<Integer> reactionCount = new ArrayList<>();
            Message message = channel.retrieveMessageById(previousId).complete();
            for (MessageReaction reaction : message.getReactions()) {
                reactionCount.add(reaction.getCount());
            }
            game.addPoints(previousUsers[0], reactionCount.get(0) - 1);
            game.addPoints(previousUsers[1], reactionCount.get(1) - 1);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Player 1: " + previousUsers[0].getUser().getName() + " got " + (reactionCount.get(0) - 1) + " point(s)\nPlayer 2: "
                    + previousUsers[1].getUser().getName() + " got " + (reactionCount.get(1) - 1) + " point(s)");
            if (reactionCount.get(0) > reactionCount.get(1)) {
                eb.setDescription(previousUsers[0].getUser().getName() + " wins this round!");
            } else if (reactionCount.get(0) < reactionCount.get(1)) {
                eb.setDescription(previousUsers[1].getUser().getName() + " wins this round!");
            } else {
                eb.setDescription("Both players tied!");
            }

            eb.setColor(new Color(62, 153, 173));
            channel.sendMessage(eb.build()).queue();
        }

        if (questions.size() != 0) {
            Player player1 = null;
            Player player2 = null;

            for (Player player: game.getGamePlayers()) {
                if (player.getFirstQuestion().get(0).equals(questions.get(0))) {
                    player1 = player;
                } else if (player.getSecondQuestion().get(0).equals(questions.get(0))) {
                    player2 = player;
                }
            }

            User user2 = player2.getUser();

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Prompt: " + questions.get(0));
            eb.setColor(new Color(93, 176, 235));
            channel.sendMessage(eb.build()).completeAfter(5600, TimeUnit.MILLISECONDS);

            Message sentMessage = channel.sendMessage("Response 1: " + player1.getFirstQuestion().get(1) + "\n").completeAfter(5800, TimeUnit.MILLISECONDS);
            timer.schedule(new RevealTimer(channel, sentMessage.getId(), player1.getUser().getName()), 10000);
            Player finalPlayer = player1;
            Player finalPlayer1 = player2;
            channel.sendMessage("Response 2: " + player2.getSecondQuestion().get(1) + "\n\nYou have 10 seconds to pick your favorite answer!\n").queue(
                    (message1) -> {
                        message1.addReaction("\u0031\u20E3").queue();
                        message1.addReaction("\u0032\u20E3").queue();
                        timer.schedule(new ShowTimer(game, channel, questions.subList(1, questions.size())
                                , message1.getId(), new Player[] {finalPlayer, finalPlayer1}), 10000);
                        timer.schedule(new RevealTimer(channel, message1.getId(), user2.getName()), 10000);
                    }
            );
        } else {
            channel.sendMessage("That's a wrap on the questions! Let's see those scores.").queue();
            game.phase3();
        }
    }
}
