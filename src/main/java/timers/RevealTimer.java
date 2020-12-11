import net.dv8tion.jda.api.entities.MessageChannel;

import java.util.TimerTask;

public class RevealTimer extends TimerTask {
    private MessageChannel channel;
    private String id;
    private String name;

    //constructor of the "RevealTimer" class
    //takes in a MessageChannel "channel", String "id", and String "name"
    public RevealTimer(MessageChannel channel, String id, String name) {
        this.channel = channel;
        this.id = id;
        this.name = name;
    }

    //reveals the author of the anonymous prompt
    @Override
    public void run() {
        channel.retrieveMessageById(id).queue((message1) -> {
            String oldMessage = message1.getContentRaw();
            message1.editMessage(oldMessage.substring(0, 10) + " by **" + name + "**: "
                                + oldMessage.substring(12)).queue();
                }
        );
    }
}
