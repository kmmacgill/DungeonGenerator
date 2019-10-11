package com.KoreyMacGill.randomdungeongenerator.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.KoreyMacGill.randomdungeongenerator.R;
import com.KoreyMacGill.randomdungeongenerator.room.Room;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set this up in the onCreate so when the activity is loaded
        //it maps the button to the function call.
        findViewById(R.id.generateDungeonButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Room roomtoShowOnScreen = generateDungeon(2, new Room());
            }
        });
    }

    public Room generateDungeon(int possibleChancesToGenerateAConnection, Room par){
        //phase 0. get a room
        Room currentRoom = new Room(par);
        //phase 1. possibly generate the other rooms this room is connected to
        for (int i = 0; i < possibleChancesToGenerateAConnection; i++){
            if ((Math.random() * 100) < 50) //50% chance of adding a child.
            currentRoom.addChildRoom(this.generateDungeon(possibleChancesToGenerateAConnection -1, currentRoom));
        }
        //the above sets up the entire tree. getting all of the nodes built out. now it's time to make other connections besides parent/child.

        //phase 2. create uncle/sibling connections


        //phase 3. create random "secret" connections to other rooms, this is more at random.
        //--------------
        //notes for myself.
        //break this into 3 phases. 3 functions. call those functions within this one.
        //phases have stayed the same more or less.
        //phase 1. use random number as "chance" and have it fluctuate based on koko's suggestion to make it more natural
        //phase 2 do the same more or less, experiment with it. but use that same fluctuating method to ensure we have
        //connections built.
        //phase 3 is about the same.
        //USE BREADTH FIRST SEARCH! not depth. skews the connections too much otherwise.
        //TODO: READ ABOVE AND DO IT
        return currentRoom;
    }
}
