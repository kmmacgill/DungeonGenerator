package com.KoreyMacGill.randomdungeongenerator.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Scroller;

import com.KoreyMacGill.randomdungeongenerator.R;
import com.KoreyMacGill.randomdungeongenerator.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int chanceToGenerateConnection;

    private List<Room> queue;

    private String theDungeonString;

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theDungeonString = "";
        queue = new ArrayList<>();
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        et = findViewById(R.id.outPut);

        chanceToGenerateConnection = 5;
        //set this up in the onCreate so when the activity is loaded
        //it maps the button to the function call.
        findViewById(R.id.GenerateDungeonButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearOutput();
                Room origin = new Room();
                //TODO: set cool origin things like entrance, etc.
                origin.setId("Origin");
                Room theDungeon = generateDungeon(chanceToGenerateConnection, origin);
                origin.addChildRoom(theDungeon);
                addToQueue(origin);
                do{
                    addToTheDungeonString(queue.get(0).getId() + " | ");
                    for (Room i : queue.get(0).getConnectedRooms()){
                        addToQueue(i);
                        addToTheDungeonString(i.getId()+", ");
                    }
                    addToTheDungeonString("\n\r");
                    popQueue();
                } while (!queue.isEmpty());
                addToOutput(theDungeonString);
            }
        });
    }

    private void addToTheDungeonString(String stuff){
        theDungeonString = theDungeonString + stuff;
    }

    private void addToOutput(String stuffToAdd) {
        String current = et.getText().toString();
        current = current + stuffToAdd;
        et.setText(current);
    }

    private void clearOutput(){
        theDungeonString="";
        et.getText().clear();
    }

    private void addToQueue(Room room){
        queue.add(queue.size(), room);
    }

    private void popQueue(){
        queue.remove(0);
    }

    public Room generateDungeon(int chances, Room parent){
        Room theRoom = new Room(parent);
        Random randomNumber = new Random();
        for(int i = chances; i > 0; i--) {
            if((randomNumber.nextInt(101) + 1) > 50) {
                theRoom.addChildRoom(generateDungeon(chances -1, theRoom));
            }
        }
        return theRoom;
    }
}
