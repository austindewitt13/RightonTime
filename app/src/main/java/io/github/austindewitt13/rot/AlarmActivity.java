//package io.github.austindewitt13.rot;
//
//import android.app.Activity;
//import android.content.Context;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//import io.github.austindewitt13.rot.controller.MainActivity;
//import io.github.austindewitt13.rot.manager.AlarmManagerReceiver;
//
//import java.io.IOException;
//
//public class AlarmActivity extends AppCompatActivity {
//
//    MediaPlayer mediaPlayer;
//    TextView info;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_alarm_manager);
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Uri myUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE);
//        mediaPlayer = new MediaPlayer();
//
//        class Listener implements MediaPlayer.OnPreparedListener {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.start();
//            }
//        }
//        mediaPlayer.setOnPreparedListener(new Listener());
//
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        try {
//            mediaPlayer.setDataSource(getApplicationContext(), myUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.prepareAsync();
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
//}
