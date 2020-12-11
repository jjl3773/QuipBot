# QuipBot

## Configuration:
QuipBot can be added to a Discord server through the website https://top.gg/, which can be done by searching for QuipBot on the site, and clicking the “invite” button, and selecting a specific server from the pop-up page’s dropdown menu.

Making a server in Discord is simple: go to the left hand side of the screen, find a button with a “+”, and click “create my own”.

**Note: Permissions need to be authorized for the bot, and don’t forget to accept the reCaptcha pop-up**

Check the server, and QuipBot should be active (use `!qb help` to check what commands are available).

*Note: There may be issues surrounding QuipBot staying online 24/7*
 
### Bot Commands:
Typing in commands (words that begin with an `!qb`) into the server chat is how the user interacts with QuipBot
Main Commands:
- `!qb help` : Brings up the list of usable commands
- `!qb game` : Initializes a QuipBot game
- `!qb join` : Allows other players to join the game

**Note: The game will not let players join multiple times**
- `!qb start` : Begins the QuipBot game

**Note: The game will not start with only one player**

### Sub-Commands:
- `!qb prompts` : Can be used in two ways

 1. When used 15 seconds after the end of a game, the bot will pull up a list of all prompts entered that game, and will allow the players to "save" them
 2. When used outside of a running instance of a game, the bot pulls up the list of saved prompts on the specific server

- `!qb random` : Gives the player a random prompt that they can use instead of creating their own

## Gameplay:

### Setup:

The game operates in a text channel within the server 
A player must use `!qb` game to initialize a game. Other players who want to join can use the `!qb` join command to join the game.
Once everyone has entered the game, a player can begin the game by entering `!qb start`

#### Phase 0: Prompts
Each player will receive a private message from QuipBot that they will have to navigate into
The player will have 20 seconds to either:

1. Type in their own response within the private message channel that the bot will record
2. Use `!qb random` to get the bot to create a random prompt for them instead.

Once 20 seconds has passed, the prompts phase ends

#### Phase 1: Responses
The player will remain in the private message channel. Each player will then receive two prompts to respond to in 40 seconds. Once the time is over, players will head back to the initial text channel where the game was initaited for voting phase.

#### Phase 2: Voting
Each prompt that the players created will be judged with their two corresponding responses by players, where the responses will initially be anonymous.

Each voting period for each response will be 10 seconds. During this period, players can vote by clicking on the appropriate reaction button.

After each propmt + response pair, scores will be shown. The players who created the responses will now be revealed and the round specific winner will be declared.

#### Phase 3: Scoreboard
After each prompt was judged, a scoreboard will be shown. 

If the current round was not the final round, then the players will be prompted to return to their dm's for another round of prompts and responses.

If the current round was the final round, players have the option to use `!qb prompts` to save the prompts generated during the game. After this, another game can be initiated without issue.
 
## Recommendations:
We believe that the game is the most entertaining when players use a voice chat simultaneously when playing.

A group of 3-8 is an optimal amount of players, although there is no player max limit.

Discord emojis and emotes can be used in prompt/response creation, although ones used by players with Discord Nitro/Classic will not render properly.

#### Final Note:
If using this code for your own bot, make sure to replace all references to local folders with the appropriate *full path*. You will need to download the `GeneralPrompts.csv`, create a new `ServerPrompts.csv`, and a `token.txt` (in which to store your bot's token). These references are located within the `GameListener.java` and `SaveTimer.java` files.
