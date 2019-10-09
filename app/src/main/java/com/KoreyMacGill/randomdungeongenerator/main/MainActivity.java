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
                Room roomtoShowOnScreen = generateDungeon(2, "Origin");//was thinking we could use a key word for knowing we're at the start.
            }
        });
    }

    public Room generateDungeon(int numbOfKids, String parentId){
        //phase 0. get a room
        Room currentRoom = new Room(parentId);
        //phase 1. generate the other rooms this room is connected to
        for (int i = 0; i < numbOfKids; i++){
            if ((Math.random() * 100 + numbOfKids) < 25) //25% ish chance of adding a child.
            currentRoom.addChildRoom(this.generateDungeon(numbOfKids -1, currentRoom.getId()));
        }
        //phase 2. establish uncle connections
        //todo:make awesomeness
        return currentRoom;
    }
}
