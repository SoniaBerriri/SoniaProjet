package com.example.soniaprojet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerViewAccessibilityDelegate;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


RecyclerView recyclerView;
TextView noMusicTextView;
ArrayList<AudioModel> songsList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView =findViewById(R.id.recycler_View);
        noMusicTextView = findViewById(R.id.no_songs_text);

        if ( checkPermission() == false){
            requestPermission();
            return;
        }
        String[] projection ={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };
        String selection = MediaStore.Audio.Media.IS_MUSIC +"!=0"

        Cursor cursor = getContentResolver().query(MediaStore.EXTERNAL_CONTENT_URI,projection,selection,null,null);
    while (Cursor.moveToNext()){
        AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
        
    }
    }

    /**Methode permission**/
    boolean CheckPermission(){
     int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
     if(result == PackageManager.PERMISSION_GRANTED){
    return true;
    }else {
    return false;
    }
    }

    void requestPermission(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this,"READ PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTINGS",Toast.LENGTH_SHORT).show();
        } else
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123 );
    }


}