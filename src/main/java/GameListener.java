import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.List;
import java.util.Timer;


public class GameListener extends ListenerAdapter {
    private List<Game> currentGames;

    //constructor of the "GameListener" class
    public GameListener() {
        this.currentGames = new ArrayList<>();
    }

    //pre: a MessageReceivedEvent event
    //post: if the event represents an applicable command, the command
    //will be executed
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Timer startTimer = new Timer();
        boolean addingPlayers = false;
        Game currentGame = this.getGame(channel.getId());

        EmbedBuilder IntroEmbed = new EmbedBuilder();
        IntroEmbed.setTitle("Initiating a new **QUIPBOT** game...");
        IntroEmbed.setThumbnail("http://www.lilynow.com/wp-content/uploads/2020/12/higherquality.png");
        IntroEmbed.setColor(new Color(200, 50, 100));
        IntroEmbed.setDescription("Type **!qb join** to join this game! If all players have joined, type **!qb start** to begin the game!");
        IntroEmbed.setColor(new Color(93, 176, 235));

        if (event.getAuthor().isBot()) {
            return;
        }

        for (Game games: currentGames) {
            if (games.getChannelId().equals(event.getChannel().getId())) {
                if (games.isAdding()) {
                    addingPlayers = true;
                }
            }
        }
        if (!addingPlayers) {
            boolean isRecordingResponses = false;
            boolean isRecordingPrompts = false;
            boolean isGameFinished = false;
            Player thisPlayer = null;

            for (Game games: currentGames) {
                if (games.isRecordingResponses() || games.isRecordingPrompts() || games.isGameFinished()) {
                    for (Player player: games.getGamePlayers()) {
                        if (event.getAuthor().getId().equals(player.getUser().getId())) {
                            thisPlayer = player;
                            if (games.isRecordingResponses()) {
                                isRecordingResponses = true;
                            } else if (games.isRecordingPrompts()) {
                                isRecordingPrompts = true;
                            } else {
                                isGameFinished = true;
                            }
                            currentGame = games;
                        }
                    }
                }
            }

            if (!isRecordingResponses && !isRecordingPrompts && !isGameFinished) {
                if (event.getMessage().getContentRaw().equals("!qb help")) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("QuipBot");
                    eb.setColor(new Color(200, 50, 100));
                    eb.setDescription("Bot designed by the Kevin Lin Fan Club");
                    eb.addField("Commands", "!qb game\n!qb prompts", true);
                    eb.addField("Descriptions", "Initiates a new **QuipBot** game\nPulls up a list of prompts saved on this server", true);
                    eb.setFooter("Jason Liu, Nicholas Chin, Lillian Zhu");
                    eb.setColor(new Color(214, 173, 37));
                    eb.setImage("http://www.lilynow.com/wp-content/uploads/2020/12/higherquality.png");
                    channel.sendMessage(eb.build()).queue();
                }


                if (event.getMessage().getContentRaw().equals("!qb prompts")) {
                    List<String[]> promptList = readCSV(new File("/Users/jason/IdeaProjects/discord/src/main/ServerPrompts.csv"));
                    boolean isIn = false;
                    for (String[] servers: promptList) {
                        if (servers[0].equals(event.getGuild().getId())) {
                            isIn = true;
                            channel.sendMessage("Here is a list of the prompts saved!").queue();
                            for (int i = 1; i < servers.length; i++) {
                                channel.sendMessage("```fix\n" + servers[i] + "```").queue();
                            }
                        }
                    }
                    if (!isIn) {
                        channel.sendMessage("No prompts have been saved in this server!").queue();
                    }
                }

                if (event.getMessage().getContentRaw().equals("!qb game")) {
                    IntroEmbed.addField("Current Players:", event.getAuthor().getName(), true);
                    channel.sendMessage(IntroEmbed.build()).queue(
                            t -> addNewGame(t.getId(), event.getChannel(), event.getAuthor())
                    );

                    channel.sendMessage("Type `!qb join` to join this game! If all players have joined, type `!qb start` to begin the game!").queue();
                    //                startTimer.schedule(new StartTimer(this, channel), 10000);
                }
            } else if (isRecordingResponses) {
                if (event.getChannel().getId().equals(thisPlayer.getDmId())) {
                    if (thisPlayer.getFirstQuestion().size() == 1) {
                        thisPlayer.addToFirstQuestion(event.getMessage().getContentRaw());

                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("**Here's your second question (2/2)**:");
                        eb.setDescription(thisPlayer.getSecondQuestion().get(0));
                        eb.setColor(new Color(214, 173, 37));
                        channel.sendMessage(eb.build()).queue();
                    } else if (thisPlayer.getSecondQuestion().size() == 1) {
                        thisPlayer.addToSecondQuestion(event.getMessage().getContentRaw());

                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Responses recorded! Sit tight, we're waiting for others...");
                        eb.setColor(new Color(214, 173, 37));
                        channel.sendMessage(eb.build()).queue();
                    }
                }
            } else if (isRecordingPrompts) {
                if (channel.getId().equals(thisPlayer.getDmId())) {
                    if (!currentGame.hasInputtedQuestion(event.getAuthor().getId())) {
                        if (event.getMessage().getContentRaw().equals("!qb random")) {
                            Random random = new Random(System.currentTimeMillis());
                            List<String[]> prompts = readCSV(new File("/Users/jason/IdeaProjects/discord/src/main/GeneralPrompts.csv"));
                            thisPlayer.setPrompt(prompts.get(random.nextInt(prompts.size()))[0]);
                        } else {
                            String question = event.getMessage().getContentRaw();
                            thisPlayer.setPrompt(question);
                        }
                        channel.sendMessage("Prompt recorded!").queue();
                    }
                }
            } else {
                if (event.getMessage().getContentRaw().equals("!qb prompts")) {
                    channel.sendMessage("Here are the prompts used this game! React to any of the prompts" +
                            " you would like to save").queue();
                    channel.sendMessage("```css\n[Note, the saving mechanism will terminate after 20 seconds]```").queue();
                    currentGame.displaySavablePrompts();
                    startTimer.schedule(new SaveTimer(this, currentGame, channel, event.getGuild().getId()), 20000);
                }
            }
        } else {
            if (event.getMessage().getContentRaw().equals("!qb join")) {
                for (Game games: currentGames) {
                    if (event.getChannel().getId().equals(games.getChannelId())) {
                        boolean alreadyIn = false;
                        for (Player player: games.getGamePlayers()) {
                            if (player.getUser().getId().equals(event.getAuthor().getId())) {
                                alreadyIn = true;
                            }
                        }
                        if (!alreadyIn) {
                            channel.retrieveMessageById(games.getMessageId()).queue((message) -> {
                                String oldPlayers = message.getEmbeds().get(0).getFields().get(0).getValue();
                                EmbedBuilder builder = new EmbedBuilder(IntroEmbed);
                                builder.addField("Current Players:", oldPlayers + "\n" + event.getAuthor().getName(), true);
                                message.editMessage(builder.build()).queue();
                            });
                            games.addPlayer(event.getAuthor());
                        } else {
                            channel.sendMessage("You are already in this game!").queue();
                        }
                    }
                }
            }

            if (event.getMessage().getContentRaw().equals("!qb start")) {
                if (currentGame.getGamePlayers().size() >= 2) {
                    currentGame.setAdding(false);
                    currentGame.phase0();
                } else {
                    channel.sendMessage("You need at least two players to start a game!").queue();
                }
            }
        }
    }

    //pre: a String "messageId", a MessageChannel "channel", and a User "user"
    //post: initiates a new game
    public void addNewGame(String messageId, MessageChannel channel, User user) {
        Game newGame = new Game(this, messageId, channel);
        newGame.setAdding(true);
        newGame.addPlayer(user);
        this.currentGames.add(newGame);
    }

    //pre: a String "id"
    //post: if that "id" corresponds to a current game, that game will be returned
    public Game getGame(String id) {
        for (Game games: currentGames) {
            if (games.getChannelId().equals(id)) {
                return games;
            }
        }
        return null;
    }

    //pre: takes in a File "file" that should be of CSV format
    //post: returns the contents of the file as a list
    public List<String[]> readCSV(File file) {
        try {
            FileReader filereader = new FileReader(file);

            CSVParser parser = new CSVParserBuilder().withSeparator('ἠ').withIgnoreQuotations(true).build();
            CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser)
                    .build();
            return csvReader.readAll();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeGame(Game game) {
        this.currentGames.remove(game);
    }
}
