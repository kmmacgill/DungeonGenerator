package com.KoreyMacGill.randomdungeongenerator.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.KoreyMacGill.randomdungeongenerator.R;
import com.KoreyMacGill.randomdungeongenerator.room.Room;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public Room generateDungeon(int NumbOfKids){
        return new Room();
    }
}
