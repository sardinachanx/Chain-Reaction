# Chain Reaction
Random offline Chain Reaction game written in Java, inspired by [Yvo Schaap](http://yvoschaap.com/chainrxn/).  Currently under development. This project uses the Java [Slick2D](http://slick.ninjacave.com) library.

##Current Features
- Three game modes: Original, Infinite, Survival
  - Original: Adopted from Yvo Schaap's [advanced version](http://yvoschaap.com/chainrxnadvanced/) with 12 levels, unlimited lives
  - Infinite: Level threshold unknown, high difficulty, unlimited lives
  - Survival: Level threshold known, medium-low difficulty, 1 life only!
- Therapeutic (maybe) music for each game mode
- Local highscore saving for original and survival modes

~~Disclaimer: I am not responsible if you get a heart attack during Survival mode. Or any other mode. Or in your life. I'm just not responsible for heart attacks in general.~~


##Game Instructions
Click anywhere to land and expand the bomb. When a ball collides with a bomb or any ball that has hit the bomb, the ball also explodes, and disappears after a while. You pass the level if enough balls expand. The further the ball is from the bomb, the higher the score. 

*Original Only:* 
*If you don't pass the level threshold, the amount of lives you use increases by 1 and you have to attempt the level again and pass it before you can move on ~~with life~~. Your final high score depends on how many lives you use, so be careful!*

*Survival Only:*
*If you die, you go back to Level 1 (and your current score gets entered into the high score table if you go far enough).*


##Features in progress
- Easter Eggs! 


##Running the Game

*As an App:*
Just run the app. ~Don't kill me for not teaching you how to do that.~ 

*In Eclipse:*
Import the project into Eclipse. Right click on Properties, go to Build Path, find "lwjgl.jar" and choose the native library location to be the Slick folder. Run the GameEngine class. 
