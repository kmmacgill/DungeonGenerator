package com.KoreyMacGill.randomdungeongenerator.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.KoreyMacGill.randomdungeongenerator.R;
import com.KoreyMacGill.randomdungeongenerator.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int chanceToGenerateConnection;

    private List<Room> traversalQueue;
    //private List<Room> connectionQueue;

    private List<Room> theDungeon;

    private String theDungeonString;

    EditText et;
    TextView titlebar;
    TextView descriptionBox;
    HorizontalScrollView roomScroller;

    int numberCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theDungeonString = "";
        numberCounter = 0;

        theDungeon = new ArrayList<>();

        traversalQueue = new ArrayList<>();
        //connectionQueue = new ArrayList<>();
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        et = findViewById(R.id.outPut);
        titlebar = findViewById(R.id.roomTitle);
        descriptionBox = findViewById(R.id.roomDescription);
        roomScroller = findViewById(R.id.roomsScroller);

        chanceToGenerateConnection = 5;

        findViewById(R.id.swapView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleView(et);
                toggleView(titlebar);
                toggleView(descriptionBox);
                toggleView(roomScroller);
            }
        });

        findViewById(R.id.generateDungeonButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberCounter = 0;
                clearOutput();
                Room origin = new Room();
                //TODO: set cool origin things like entrance, etc.
                origin.setId("Out");
                origin.setDescription("You're out! hopefully it's better than back inside...");
                Room theDungeon = generateDungeon(chanceToGenerateConnection, origin);
                //addToQueue(connectionQueue, theDungeon);
                //assembleConnections();
                origin.addConnectedRoom(theDungeon);
                addToQueue(traversalQueue, origin);
                outputNotationToScreen();
                populateCardView(theDungeon);
            }
        });
    }

    private void populateCardView(Room room) {
        titlebar.setText(room.getId());
        descriptionBox.setText(room.getDescription());
        fillRoomSelector(room);
    }

    private void fillRoomSelector(Room room) {
        //grab the layout and clear the current button list of connections
        LinearLayout layout = findViewById(R.id.RoombuttonsLayout);
        layout.removeAllViews();

        //now let's add a button for going back the way we were before unless we're in the origin room (aka outside the dungeon)
        if (!room.getId().equals("Out")) {
            final Room wayBack = room;
            Button wayBackBtn = new Button(this);
            wayBackBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            wayBackBtn.setText(room.getParent().getId());
            wayBackBtn.setId(0);
            wayBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    populateCardView(wayBack.getParent());
                }
            });

            layout.addView(wayBackBtn);
        }

        //now let's add a button for going to all the connecting rooms
        int counter = 1;
        for (Room r : room.getConnectedRooms()) {
            final Room thisRoom = r;
            Button roomBtn = new Button(this);
            roomBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            roomBtn.setText(r.getId());
            roomBtn.setId(counter);
            roomBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    populateCardView(thisRoom);
                }
            });

            layout.addView(roomBtn);
            counter++;
        }
    }

    private void toggleView(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }

    private void outputNotationToScreen() {
        do {
            addToTheDungeonString(traversalQueue.get(0).getId() + " | ");
            for (Room i : traversalQueue.get(0).getConnectedRooms()) {
                addToQueue(traversalQueue, i);
                addToTheDungeonString(i.getId() + ", ");
            }
            addToTheDungeonString("\n\r");
            popQueue(traversalQueue);
        } while (!traversalQueue.isEmpty());
        addToOutput(theDungeonString);
    }

    private void addToTheDungeonString(String stuff) {
        theDungeonString = theDungeonString + stuff;
    }

    private void addToOutput(String stuffToAdd) {
        String current = et.getText().toString();
        current = current + stuffToAdd;
        et.setText(current);
    }

    private void clearOutput() {
        theDungeonString = "";
        et.getText().clear();
    }

    private void addToQueue(List<Room> queue, Room room) {
        queue.add(queue.size(), room);
    }

    private void popQueue(List<Room> queue) {
        queue.remove(0);
    }

    public Room generateDungeon(int chances, Room parent) {
        numberCounter++;
        String id = "Room " + numberCounter;
        Room theRoom = new Room(parent, id);
        Random randomNumber = new Random();
        for (int i = chances; i > 0; i--) {
            if ((randomNumber.nextInt(101) + 1) > 50) {
                theRoom.addConnectedRoom(generateDungeon(chances - 1, theRoom));
            }
        }
        return theRoom;
    }

//    private void assembleConnections(){
//        do {
//            //check current room for possible "normal" connections
//            Room current = connectionQueue.get(0);
//            checkForSiblings(current);
//            checkForUncles(current);
//            checkForCousins(current);
//
//            //for each kid
//            for (Room i : current.getConnectedRooms()) {
//                addToQueue(connectionQueue, i);
//            }
//            popQueue(connectionQueue);
//        } while (!connectionQueue.isEmpty());
//    }
//
//    private void checkForSiblings(Room r){
//        if(!r.getConnectedToSibling()){
//            Random randomNumber = new Random();
//            if(r.hasSiblings()) {
//                for (Room sibling : r.getSiblings()){
//                    if ((randomNumber.nextInt(101) + 1) > 90) {
//                        r.addConnectedRoom(sibling);
//                    }
//                }
//            }
//        }
//    }
//
//    private void checkForUncles(Room r) {
//        if(!r.getConnectedToUncle()) {
//            Random randomNumber = new Random();
//            if (r.hasUncles()) {
//                for (Room sibling : r.getUncles()) {
//                    if ((randomNumber.nextInt(101) + 1) > 90) {
//                        r.addConnectedRoom(sibling);
//                    }
//                }
//            }
//        }
//    }
//
//    private void checkForCousins(Room r) {
//        if(!r.getConnectedtoCousin()) {
//            Random randomNumber = new Random();
//            if (r.hasCousins()) {
//                for (Room sibling : r.getCousins()) {
//                    if ((randomNumber.nextInt(101) + 1) > 90) {
//                        r.addConnectedRoom(sibling);
//                    }
//                }
//            }
//        }
//    }
}
