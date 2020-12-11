# QuipBot

Configuration:
QuipBot can be added to a Discord server through the website https://top.gg/.

This can be done by searching for QuipBot on the site, and clicking the “invite” button

A specific server needs to be selected from the pop-up page’s dropdown menu

Making a server in Discord is simple: go to the left hand side of the screen, find a button with a “+”, and click “create my own”

Note: Permissions need to be authorized for the bot, and don’t forget to accept the reCaptcha pop-up
Check the server, and QuipBot should be active (use !help to check what commands are available).

Note: There may be issues surrounding QuipBot staying online 24/7
 
Bot Commands:
Typing in commands (words that begin with an !qb) into the server chat is how the user interacts with QuipBot
Main Commands:
- !qb help : Brings up the list of usable commands
- !qb game : Initializes a QuipBot game
- !qb join : Allows other players to join the game
*Note: The game will not let players join multiple times*
- !qb start : Begins the QuipBot game
*Note: The game will not start with only one player*

Sub-Commands:
- !qb prompts : Can be used in two ways

1. When used 15 seconds after the end of a game, the bot will pull up a list of all prompts entered that game, and will allow the players to "save" them
2. When used outside of a running instance of a game, the bot pulls up the list of saved prompts on the specific server
!qb random : Gives the player a random prompt that they can use instead of creating their own
Gameplay:
Setup:
The game operates in a text channel within the server 
A player must use !game to initialize a game
Players will use the !join command to join the game
Once everyone has entered the game, a user will enter !start

Phase 1: Prompts
Each player will receive a private message from QuipBot that they will have to navigate into
The player will have 20 seconds to either:

1. Type in their own response within the private message channel that the bot will record
2. Use !random to get the bot to create a random prompt for them instead
Once 20 seconds has passed, the prompts phase ends
Phase 2: Responses
The player will remain in the private message channel
Each player will receive two prompts to respond to in 40 seconds
They will type it in as a text reply
Once the time is over, players will head back to the initial text channel where the game was running for voting

Phase 3: Voting
Each prompt that the players created will be judged with their two corresponding responses by players

Responses will be initially anonymous 

Each voting period for each response will be 10 seconds

Players vote by clicking on the appropriate reaction button

After each “dual” of prompt+responses, scores will be shown. 
The players who created the responses will now be revealed and the round specific winner will be declared

Phase 4: Scoreboard
After each prompt was judged, a final scoreboard will be shown
Users have the option to use !prompts to save the prompts generated during the game
Another game can be created after this
 
Recommendations:
We believe that the game is the most entertaining when players use a voice chat simultaneously when playing

A group of 3-8 is an optimal amount of players, although there is no player max limit

Discord emojis and emotes can be used in prompt/response creation
