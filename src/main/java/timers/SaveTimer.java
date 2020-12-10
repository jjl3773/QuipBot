import com.opencsv.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageReaction;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class SaveTimer extends TimerTask {
    private Game game;
    private MessageChannel channel;
    private GameListener Main;
    private String serverId;

    public SaveTimer(GameListener Main, Game game, MessageChannel channel, String serverId) {
        this.Main = Main;
        this.game = game;
        this.channel= channel;
        this.serverId = serverId;
    }

    @Override
    public void run() {
        List<String> promptsToSave = new ArrayList<>();
        for (String questionId: game.getQuestionsSave()) {
            Message message = channel.retrieveMessageById(questionId).complete();
            int reactionCount = 0;
            for (MessageReaction reaction : message.getReactions()) {
                reactionCount += reaction.getCount();
            }
            if (reactionCount > 1) {
                promptsToSave.add(message.getContentRaw());
            }
        }
        if (promptsToSave.size() != 0) {
            writeCSV(promptsToSave, serverId);
            channel.sendMessage("The prompts you marked were saved!").queue();
        } else {
            channel.sendMessage("It seems like no prompts were marked for saving").queue();
        }
        Main.removeGame(game);
    }

    public List<ArrayList<String>> readCSV(File file) {
        List<ArrayList<String>> fullData = new ArrayList<>();
        try {
            FileReader filereader = new FileReader(file);
            CSVParser parser = new CSVParserBuilder().withSeparator('ἠ').withIgnoreQuotations(true).build();
            CSVReader csvReader = new CSVReaderBuilder(filereader).withCSVParser(parser)
                    .build();
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                ArrayList<String> current = new ArrayList<>();
                for (String cell : nextRecord) {
                    current.add(cell);
                }
                fullData.add(current);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullData;
    }

    public void writeCSV(List<String> prompts, String serverId) {
        boolean added = false;
        List<ArrayList<String>> fullData = readCSV(new File("/Users/jason/IdeaProjects/discord/src/main/ServerPrompts.csv"));
        for (ArrayList<String> ServerList : fullData) {
            if (ServerList.get(0).equals(serverId)) {
                ServerList.addAll(prompts);
                added = true;
            }
        }
        if (!added) {
            ArrayList<String> newServer = new ArrayList<>();
            newServer.add(serverId);
            newServer.addAll(prompts);
            fullData.add(newServer);
        }

        List<String[]> printFullData = new ArrayList<>();
        for (ArrayList<String> list: fullData) {
            String[] arrayForm = new String[list.size()];
            arrayForm = list.toArray(arrayForm);
            printFullData.add(arrayForm);
        }

        try {
            FileWriter outputfile = new FileWriter(new File("/Users/jason/IdeaProjects/discord/src/main/ServerPrompts.csv"));

            //distinctive seperation character
            CSVWriter writer = new CSVWriter(outputfile,
                    'ἠ',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.NO_ESCAPE_CHARACTER,
                    CSVWriter.RFC4180_LINE_END);

            writer.writeAll(printFullData);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
