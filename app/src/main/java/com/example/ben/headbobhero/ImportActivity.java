package com.example.ben.headbobhero;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportActivity extends Activity {
    Map<Long, String> songMap = new HashMap<Long, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        //findMusic();
        //playSong();
    }

    public Map findMusic() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // query failed, handle error.
            //this should have a popup that there was an issue trying trying to get music
        } else if (!cursor.moveToFirst()) {
            // This should have a popup that no music was found on the device
        } else {
            int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            do {
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                // below is processing the found audio
                    songMap.put(thisId, thisTitle);
            } while (cursor.moveToNext());
        }
        return songMap;
    }

    public Map getMusicMap(){
        return findMusic();
    }

    public void playSong(){
            Uri contentUri = ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 18168);

            final MediaPlayer mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
                mMediaPlayer.setDataSource(getApplicationContext(), contentUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mMediaPlayer.prepareAsync();
            mMediaPlayer.start();

        System.out.println("JOELYTESTINGFINISH");

            /*
            //TESTING
            Thread th=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mMediaPlayer.prepare();
                        //mMediaPlayer.start();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    //send message to handler
                }
            });
            th.start();

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mMediaPlayer) {
                    mMediaPlayer.start();
                }
            });
*/
            //ENDTESTING


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_import, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
