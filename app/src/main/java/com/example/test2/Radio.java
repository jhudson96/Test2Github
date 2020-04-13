package com.example.test2;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Radio extends Fragment {
    Button b_play;
    String stream = "https://www.ssaurel.com/tmp/mymusic.mp3";
    MediaPlayer mediaPlayer;

    boolean prepared = false;
    boolean started = false;

    public Radio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_radio, container, false);#

        //Not sure why findViewById is red
        b_play = (Button)findViewById(R.id.b_play);
        b_play.setEnabled(false);
        b_play.setText("Loading");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        new PlayerTask().execute(stream);

        b_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (started){
                    started = false;
                    mediaPlayer.pause();
                    b_play.setText("Play");
                }else {
                    started = true;
                    mediaPlayer.start();
                    b_play.setText("Pause");

                }
            }
        });
    }

    class PlayerTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            b_play.setEnabled(true);
            b_play.setText("Play");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (started){
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (prepared){
            mediaPlayer.release();
        }
    }

    @Override

    public void onResume() {
        super.onResume();
        if (started){
            mediaPlayer.start();
        }
    }
}
