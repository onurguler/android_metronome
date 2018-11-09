package com.example.onurguler.metronome;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    SeekBar sb;
    Button button;

    Handler handler;
    Runnable runnable;

    SoundPool sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int bpm = 30;
    int id_click = -1;
    long delay;

    boolean control = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_click = sp.load(this, R.raw.click1, 1);

        tv = findViewById(R.id.txt);
        tv.setText(bpm + " bpm");
        sb = findViewById(R.id.bar_bpm);
        button = findViewById(R.id.button);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress >= 30)
                    bpm = progress;
                else
                    bpm = 30;
                tv.setText(bpm + " bpm");
                delay = 60000 / bpm;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                sp.play(id_click, 1, 1, 1, 0, 1);
                handler.postDelayed(this, delay);
            }

        };
    }

    public void buttonAction(View v) {
        if(control) {
            handler.post(runnable);
            control = false;
            button.setText(R.string.stop);
        }
        else {
            handler.removeCallbacks(runnable);
            control = true;
            button.setText(R.string.start);
        }
    }

}
