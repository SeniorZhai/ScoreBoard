package io.github.seniorzhai.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.seniorzhai.scoreboard.ScoreBoard;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    ScoreBoard startView, startView1, startView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startView = (ScoreBoard) findViewById(R.id.startView);
        startView1 = (ScoreBoard) findViewById(R.id.startView1);
        startView2 = (ScoreBoard) findViewById(R.id.startView2);
    }

    public void begin(View v) {
        startView.to();
        startView1.to();
        startView2.to();
    }

    public void changer(View v) {
        Random random = new Random();
        startView.changer(random.nextInt(100));
        startView1.changer(random.nextInt(100));
        startView2.changer(random.nextInt(100));
    }

}
