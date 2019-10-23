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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int chanceToGenerateConnection;

    private List<Room> traversalQueue;
    private List<Room> connectionQueue;

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
        connectionQueue = new ArrayList<>();
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
                origin.setId(0);
                origin.setTitle("Out");
                origin.setDescription("You're out! hopefully it's better than back inside...");
                theDungeon.add(origin); //position 0 = origin room aka outside
                origin.addConnectedRoom(generateDungeon(chanceToGenerateConnection, origin));

                addToQueue(traversalQueue, origin);
                addToTheDungeonString("ORIGINAL\n\r----------\n\r");
                outputNotationToScreen();

                addToQueue(connectionQueue, origin);
                assembleConnections();

                traversalQueue.clear();
                addToQueue(traversalQueue, origin);
                addToTheDungeonString("Post Connections\n\r----------\n\r");
                outputNotationToScreen();

                populateCardView(theDungeon.get(1));
            }
        });
    }

    private void populateCardView(Room room) {
        titlebar.setText(room.getTitle());
        descriptionBox.setText(room.getDescription());
        fillRoomSelector(room);
    }

    private void fillRoomSelector(Room room) {
        //grab the layout and clear the current button list of connections
        LinearLayout layout = findViewById(R.id.RoombuttonsLayout);
        layout.removeAllViews();

        //now let's add a button for going back the way we were before unless we're in the origin room (aka outside the dungeon)
        if (room.getId() != 0) {
            final Room wayBack = theDungeon.get(room.getParentPosition());
            Button wayBackBtn = new Button(this);
            wayBackBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            wayBackBtn.setText(wayBack.getTitle());
            wayBackBtn.setId(wayBack.getId());
            wayBackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    populateCardView(wayBack);
                }
            });

            layout.addView(wayBackBtn);
        }

        //now let's add a button for going to all the connecting rooms
        for (int r : room.getConnectedRooms()) {
            final Room thisRoom = theDungeon.get(r);
            Button roomBtn = new Button(this);
            roomBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            roomBtn.setText(thisRoom.getTitle());
            roomBtn.setId(thisRoom.getId());
            roomBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    populateCardView(thisRoom);
                }
            });

            layout.addView(roomBtn);
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
        List<Integer> roomIdEntries = new ArrayList<>();//the first entry "x |" we don't want to keep repeating x over and over.
        do {
            if(!roomIdEntries.contains(traversalQueue.get(0).getId())){
                addToTheDungeonString(traversalQueue.get(0).getId() + " | ");
                for (int i : traversalQueue.get(0).getConnectedRooms()) {
                    addToQueue(traversalQueue, theDungeon.get(i));
                    addToTheDungeonString(theDungeon.get(i).getId() + ", ");
                }
                addToTheDungeonString("\n\r");
            }
            roomIdEntries.add(traversalQueue.get(0).getId());
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
        theDungeon.clear();
    }

    private void addToQueue(List<Room> queue, Room room) {
        queue.add(queue.size(), room);
    }

    private void popQueue(List<Room> queue) {
        queue.remove(0);
    }

    public Integer generateDungeon(int chances, Room parent) {
        numberCounter++;
        int currentNumber = numberCounter;
        Room theRoom = new Room(parent.getId(), currentNumber);
        theDungeon.add(theRoom);
        Random randomNumber = new Random();
        for (int i = chances; i > 0; i--) {
            if ((randomNumber.nextInt(101) + 1) > 40) {
                theRoom.addConnectedRoom(generateDungeon(chances - 1, theRoom));
            }
        }
        return currentNumber;
    }

    private void assembleConnections() {
        do {
            //check current room for possible "normal" connections
            Room current = connectionQueue.get(0);

            //for each kid that CURRENTLY exists. (otherwise queue'll grow crazily and take forever/crash app
            for (int i : current.getConnectedRooms()) {
                addToQueue(connectionQueue, theDungeon.get(i));
            }

            checkForSiblings(current);
            checkForUnclesAndCousins(current);

            popQueue(connectionQueue);
        } while (!connectionQueue.isEmpty());
    }

    private void checkForSiblings(Room r) {
        if(!r.getConnectedToSibling()){ //if the current room is not connected to a sibling yet
            Random randomNumber = new Random(); //random #
            if (theDungeon.get(r.getParentPosition()).getConnectedRooms().size() > 1) { //if the current rooms parent has more than one child
                for (int child : theDungeon.get(r.getParentPosition()).getConnectedRooms()) { //for each kid
                    if (child != r.getId()) { //and it isn't itself
                        if (!theDungeon.get(child).getConnectedToSibling()) { //and the other room isn't already connected to another sibling
                            if ((randomNumber.nextInt(101) + 1) > 50) {
                                r.addConnectedRoom(child);//add it maybe
                                r.setConnectedToSibling(true);
                                theDungeon.get(child).setConnectedToSibling(true);
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkForUnclesAndCousins(Room r) {
        if(!r.getConnectedToUncle()) {
            Random randomNumber = new Random();
            if (theDungeon.get(theDungeon.get(r.getParentPosition()).getParentPosition()).getConnectedRooms().size() > 1) { //if the current rooms G parent has more than 1 kid
                for (int uncle : theDungeon.get(theDungeon.get(r.getParentPosition()).getParentPosition()).getConnectedRooms()) {//for each of those children
                    if (uncle != r.getParentPosition()) { //and it's not your parent
                        if(!theDungeon.get(uncle).getConnectedToNephew()){
                            if ((randomNumber.nextInt(101) + 1) > 70) {
                                r.addConnectedRoom(uncle);//maybe add it.
                                r.setConnectedToUncle(true);
                                theDungeon.get(uncle).setConnectedToNephew(true);
                            }
                        }
                        if(r.getConnectedToCousin()){
                            for(int cousin : theDungeon.get(uncle).getConnectedRooms()) {
                                if (!theDungeon.get(cousin).getConnectedToCousin()) {
                                    if ((randomNumber.nextInt(101) + 1 > 80)) {
                                        r.addConnectedRoom(cousin);
                                        r.setConnectedToCousin(true);
                                        theDungeon.get(cousin).setConnectedToCousin(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
