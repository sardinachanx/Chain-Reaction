# Chain Reaction
Random offline Chain Reaction game written in Java, inspired by [Yvo Schaap](http://yvoschaap.com/chainrxn/).  Currently under development. This project uses the Java [Slick2D](http://slick.ninjacave.com) library.

##Current Features
- Three game modes: Original, Infinite, Survival
  - Original: Adapted from Yvo Schaap's [advanced version](http://yvoschaap.com/chainrxnadvanced/) with 12 levels, unlimited lives
  - Infinite: Level threshold unknown, high difficulty, unlimited lives
  - Survival: Level threshold known, medium-low difficulty, 1 life only!
- Therapeutic (maybe) music for each game mode
- Local highscore saving for original and survival modes

~~Disclaimer: I am not responsible if you get a heart attack during Survival mode. Or any other mode. Or in your life. I'm just not responsible for heart attacks in general.~~


##Game Instructions
Click anywhere to land and expand the player-controlled ball (the initial ball). When a ball collides with the initial ball or any ball that has hit the initial ball, the ball also explodes, and disappears after a while. You pass the level if enough balls expand. The further the ball is from the initial ball, the higher the score. 

*Original Only:* 
*If you don't pass the level threshold, the amount of lives you use increases by 1 and you have to attempt the level again and pass it before you can move on ~~with life~~. Your final high score depends on how many lives you use, so be careful!*

*Survival Only:*
*If you die, you go back to Level 1 (and your current score gets entered into the high score table if you go far enough).*


##Features in progress
- Easter Eggs! 


##Running the Game

*As an app:*

OSX: Download the zip in **/dist** and the **/assets** folder. Unpack the zip for the app, then **go to "Show Package Contents" and copy the /assets folder into the /Contents/Resources folder.** This is important, or else the game won't run. I'll find a better way to do this later.

*In Eclipse:*

Import the project into Eclipse. Right click on Properties, go to Build Path, find "lwjgl.jar" and choose the native library location to be the Slick folder. Run the GameEngine class. 
