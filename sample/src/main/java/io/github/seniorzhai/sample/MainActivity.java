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

    public void start(View v) {
        startView.start();
        startView1.start();
        startView2.start();
    }

    public void change(View v) {
        Random random = new Random();
        startView.change(random.nextInt(100));
        startView1.change(random.nextInt(100));
        startView2.change(random.nextInt(100));
    }

}
