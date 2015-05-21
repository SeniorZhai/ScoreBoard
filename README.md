##Inspired by
![](./screenshot/inspired.gif)

##Effect
![](./screenshot/screenshot.gif)

##Usage
In your app's `build.gradle` file,add it to your dependencies:
```
dependencies {
	compile 'org.seniorzhai.scoreboard:scoreboard:1.0.0'
}
```
And in your code
```java
ScoreBoard scoreBoard = (ScoreBoard) findViewById(R.id.startView);
// start animation
scoreBoard.start();
// change number
scoreBoard.change(99);
```

##Sample app
Clone and build this repo in Android Studio to see an example of a sample app. 

##Contact
SeniorZhai:<developer.zhaitao@gmail.com>

