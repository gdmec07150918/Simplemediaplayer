package com.example.administrator.simplemediaplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="SimpleMediaPlayer";
    private MediaPlayer mMediaPlayer;
    private String mPth;
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        if(uri != null){
            mPth = uri.getPath();
            setTitle(mPth);
            if (intent.getType().contains("audio")){//判断媒体类为音频
                setContentView(android.R.layout.simple_list_item_1);
                //创建MediaPlayer
                mMediaPlayer = new MediaPlayer();
                try {
                    mMediaPlayer.setDataSource(mPth);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else if (intent.getType().contains("video")){//判断媒体类为视屏
                mVideoView = new VideoView(this);
                mVideoView.setVideoPath(mPth);
                mVideoView.setMediaController(new MediaController(this));
                mVideoView.start();
                setContentView(mVideoView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,1,0,"暂停");
        menu.add(0,1,0,"开始");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1://暂停
                if (mMediaPlayer==null || !mMediaPlayer.isPlaying()){
                    Toast.makeText(this,"没有音乐文件，不需要暂停",
                            Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        mMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if (mMediaPlayer==null){
                    Toast.makeText(this,"没有选中音频文件，请到文件夹中点击音频文件后再播放",
                            Toast.LENGTH_SHORT).show();
                }else {
                    mMediaPlayer.start();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer!=null){
            mMediaPlayer.stop();
        }
        if (mVideoView!=null){
            mVideoView.stopPlayback();
        }
    }
}
