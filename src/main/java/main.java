import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;

public class main {
    public static void main(String[] args) throws LoginException, IOException {
        JDABuilder builder = JDABuilder.createDefault("token");
        File file = new File("/Users/jason/IdeaProjects/discord/src/main/token.txt");
        String token = new String(Files.readAllBytes(file.toPath()));
        builder.setToken(token);
        GameListener Main = new GameListener(builder);
        builder.addEventListeners(Main);
        builder.build();
    }
}
