package com.doommes.learn.A5_RomteView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.doommes.learn.MainActivity;
import com.doommes.learn.R;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RemoteActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RemoteActivity";
    /**
     * notification
     */
    private Button mBtnNotification;
    /**
     * notification
     */
    private Button mBtnNotificatio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        initView();
    }

    private void initView() {
        mBtnNotification = (Button) findViewById(R.id.btn_notification);
        mBtnNotification.setOnClickListener(this);
        mBtnNotificatio = (Button) findViewById(R.id.btn_notificatio);
        mBtnNotificatio.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_notification:
                Toast.makeText(this, "asdf", Toast.LENGTH_SHORT).show();
                //showNotification();
                play();
                resleas();
                break;
            case R.id.btn_notificatio:
                play2();
                break;
        }
    }

    private void resleas(){
        MediaPlayer player = new MediaPlayer();
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = getAssets().openFd("aldebaran.ogg");
            player.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(), fileDescriptor.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setAudioStreamType(AudioManager.STREAM_RING);
        AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mAudioManager.setStreamVolume(AudioManager.STREAM_RING, mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_PLAY_SOUND);
        player.setVolume(1f, 1f);

        player.setOnPreparedListener(mp -> mp.start());
        player.prepareAsync();
    }

    private void play() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int CALL = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
        int SYSTEM = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        int RING = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        int MUSIC = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int ALARM = audioManager.getStreamVolume(AudioManager.STREAM_ALARM);
        int NOTIFICATION = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);
        int DTMF = audioManager.getStreamVolume(AudioManager.STREAM_DTMF);
        Log.d(TAG, "CALL: " + CALL);
        Log.d(TAG, "SYSTEM: " + SYSTEM);
        Log.d(TAG, "RING: " + RING);
        Log.d(TAG, "MUSIC: " + MUSIC);
        Log.d(TAG, "ALARM: " + ALARM);
        Log.d(TAG, "NOTIFICATION: " + NOTIFICATION);
        Log.d(TAG, "DTMF: " + DTMF);
    }

    private void play2() {
        MediaPlayer player = MediaPlayer.create(this, R.raw.charlie);
        player.start();


    }

    private void jetPlayStream(){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                // 获取最小缓冲区
                int bufSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
                // 实例化AudioTrack(设置缓冲区为最小缓冲区的2倍，至少要等于最小缓冲区)
                AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO,
                        AudioFormat.ENCODING_PCM_16BIT, bufSize*2, AudioTrack.MODE_STREAM);
                // 设置音量
                audioTrack.setVolume(10f) ;
                // 设置播放频率
                audioTrack.setPlaybackRate(10) ;
                audioTrack.play();
                // 获取音乐文件输入流
                InputStream is = getResources().openRawResource(R.raw.aldebaran);
                byte[] buffer = new byte[bufSize*2] ;
                int len ;
                try {
                    while((len=is.read(buffer,0,buffer.length)) != -1){
                        System.out.println("读取数据中...");
                        // 将读取的数据，写入Audiotrack
                        audioTrack.write(buffer,0,buffer.length) ;
                    }
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default";
            String channelName = "默认通知";
            notificationManager.createNotificationChannel(new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH));
        }
        Notification builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("test")
                .setContentText("testassts")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)

                .build();

        notificationManager.notify(1, builder);


        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .build();
        //Response response = okHttpClient.newCall(request).enqueue();
        String id = "123";
        final String code = id.substring(0, id.length() - 3);
        Log.d(TAG, "showNotification: " + code);


    }

}
