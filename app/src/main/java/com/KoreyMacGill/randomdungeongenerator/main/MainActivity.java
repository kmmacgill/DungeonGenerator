package com.KoreyMacGill.randomdungeongenerator.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.KoreyMacGill.randomdungeongenerator.R;
import com.KoreyMacGill.randomdungeongenerator.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Room> traversalQueue;
    private List<Room> connectionQueue;
    private List<Room> theDungeon;

    private String theDungeonString;

    EditText notationView;
    TextView titleBar;
    TextView descriptionBox;
    HorizontalScrollView roomScroller;
    RelativeLayout settingsView;

    EditText numbOfLevels;
    EditText maxNumberOfConnectionsPerRoom;
    EditText secretPaths;

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

        notationView = findViewById(R.id.outPut);
        titleBar = findViewById(R.id.roomTitle);
        descriptionBox = findViewById(R.id.roomDescription);
        roomScroller = findViewById(R.id.roomsScroller);
        settingsView = findViewById(R.id.settingsView);


        numbOfLevels = findViewById(R.id.numberOfLevels);
        numbOfLevels.setText("3");
        maxNumberOfConnectionsPerRoom = findViewById(R.id.numberOfConnections);
        maxNumberOfConnectionsPerRoom.setText("4");
        secretPaths = findViewById(R.id.numberOfSecretConnections);
        secretPaths.setText("5");

        findViewById(R.id.swapView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleView(notationView);
                toggleView(titleBar);
                toggleView(descriptionBox);
                toggleView(roomScroller);
            }
        });

        findViewById(R.id.settingsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleView(settingsView);
                if(settingsView.getVisibility()==View.VISIBLE) {
                    findViewById(R.id.swapView).setClickable(false);
                    findViewById(R.id.generateDungeonButton).setClickable(false);
                    if (notationView.getVisibility() == View.VISIBLE) {
                        toggleView(notationView);
                    }
                    if(titleBar.getVisibility()==View.VISIBLE){
                        toggleView(titleBar);
                        toggleView(descriptionBox);
                        toggleView(roomScroller);
                    }
                } else {
                    findViewById(R.id.swapView).setClickable(true);
                    findViewById(R.id.generateDungeonButton).setClickable(true);
                    if(titleBar.getVisibility()==View.GONE){
                        toggleView(titleBar);
                        toggleView(descriptionBox);
                        toggleView(roomScroller);
                    }
                }
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
                origin.addNormalConnection(generateDungeon(Integer.parseInt(maxNumberOfConnectionsPerRoom.getText().toString()), origin, Integer.parseInt(numbOfLevels.getText().toString())));

                addToQueue(connectionQueue, origin);
                setUpExtraConnections();

                setUpSecretConnections();

                addToQueue(traversalQueue, origin);
                addToTheDungeonString("-----THE DUNGEON-----\n\r");
                updateScreenText();

                addToOutput(theDungeonString);
                populateCardView(theDungeon.get(1));
            }
        });
    }

    private void populateCardView(Room room) {
        titleBar.setText(room.getTitle());
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
        for (int r : room.getNormalConnectedRooms()) {
            System.out.println("Making Buttons For Normal");
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
        for (int s : room.getExtraConnectedRooms()) {
            System.out.println("Making Buttons For Extra");
            final Room thisRoom = theDungeon.get(s);
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
        for (int t : room.getSecretConnectedRooms()) {
            System.out.println("Making Buttons For secret");
            final Room thisRoom = theDungeon.get(t);
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

    private void updateScreenText() {
        List<Integer> roomIdEntries = new ArrayList<>();//the first entry "x |" we don't want to keep repeating x over and over.
        do {
            if (!roomIdEntries.contains(traversalQueue.get(0).getId())) {
                addToTheDungeonString(traversalQueue.get(0).getId() + " | ");
                System.out.println(traversalQueue.get(0).getId() + " | ");
                addToTheDungeonString("P-" + theDungeon.get(traversalQueue.get(0).getId()).getParentPosition() + ", ");
                System.out.println("P-" + theDungeon.get(traversalQueue.get(0).getId()).getParentPosition() + ", ");
                for (int i : traversalQueue.get(0).getNormalConnectedRooms()) {
                    addToQueue(traversalQueue, theDungeon.get(i));
                    addToTheDungeonString("N-" + theDungeon.get(i).getId() + ", ");
                    System.out.println("N-" + theDungeon.get(i).getId() + ", ");
                }
                for (int j : traversalQueue.get(0).getExtraConnectedRooms()) {
                    addToTheDungeonString("E-" + theDungeon.get(j).getId() + ", ");
                    System.out.println("E-" + theDungeon.get(j).getId() + ", ");
                }
                for (int k : traversalQueue.get(0).getSecretConnectedRooms()) {
                    addToTheDungeonString("S-" + theDungeon.get(k).getId() + ", ");
                    System.out.println("S-" + theDungeon.get(k).getId() + ", ");
                }
                addToTheDungeonString("\n\r\n\r");
                System.out.println();System.out.println();
            }
            roomIdEntries.add(traversalQueue.get(0).getId());
            popQueue(traversalQueue);
        } while (!traversalQueue.isEmpty());
    }

    private void addToTheDungeonString(String stuff) {
        theDungeonString = theDungeonString + stuff;
    }

    private void addToOutput(String stuffToAdd) {
        String current = notationView.getText().toString();
        current = current + stuffToAdd;
        notationView.setText(current);
    }

    private void clearOutput() {
        theDungeonString = "";
        notationView.getText().clear();
        theDungeon.clear();
    }

    private void addToQueue(List<Room> queue, Room room) {
        queue.add(queue.size(), room);
    }

    private void popQueue(List<Room> queue) {
        queue.remove(0);
    }

    public Integer generateDungeon(int chances, Room parent, int level) {
        numberCounter++;
        System.out.println("Generating Number " + numberCounter);
        int currentNumber = numberCounter;
        Room theRoom = new Room(parent.getId(), currentNumber);
        theDungeon.add(theRoom);
        Random randomNumber = new Random();
        if(level > 0) {
            for (int i = chances; i > 0; i--) {
                if ((randomNumber.nextInt(101) + 1) > 50) {
                    theRoom.addNormalConnection(generateDungeon(chances, theRoom,level - 1));
                }
            }
        }
        return currentNumber;
    }

    private void setUpExtraConnections() {
        do {
            System.out.println("Making Extra Connections for Room " + connectionQueue.get(0).getId());
            //check current room for possible "normal" connections
            Room current = connectionQueue.get(0);
            //for each kid that CURRENTLY exists. (otherwise queue'll grow crazily and take forever/crash app
            for (int i : current.getNormalConnectedRooms()) {
                addToQueue(connectionQueue, theDungeon.get(i));
            }
            if(maxConnectionsNotMet(current)) {
                checkForSiblings(current);
            }
            if(maxConnectionsNotMet(current)){
                checkForUnclesAndCousins(current);
            }
            popQueue(connectionQueue);
        } while (!connectionQueue.isEmpty());
    }

    private Boolean maxConnectionsNotMet(Room room) {
        return room.getExtraConnectedRooms().size() + room.getNormalConnectedRooms().size() < Integer.parseInt(maxNumberOfConnectionsPerRoom.getText().toString());
    }

    private void checkForSiblings(Room r) {
        System.out.println("Checking for siblings for room " + r.getId());
        if (!r.getConnectedToSibling()) { //if the current room is not connected to a sibling yet
            Random randomNumber = new Random(); //random #
            if (theDungeon.get(r.getParentPosition()).getNormalConnectedRooms().size() > 1) { //if the current rooms parent has more than one child
                for (int child : theDungeon.get(r.getParentPosition()).getNormalConnectedRooms()) { //for each kid
                    if (child != r.getId()) { //and it isn't itself
                        if ((!theDungeon.get(child).getConnectedToSibling()) && (!r.getConnectedToSibling())) { //check the room hasn't connected yet, and the other room isn't already connected to another sibling
                            if ((randomNumber.nextInt(101) + 1) > 50) {
                                if (maxConnectionsNotMet(r) && maxConnectionsNotMet(theDungeon.get(child))) { //don't add if max connections met for either
                                    r.addExtraConnection(child);
                                    theDungeon.get(child).addExtraConnection(r.getId());
                                    r.setConnectedToSibling(true);
                                    theDungeon.get(child).setConnectedToSibling(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkForUnclesAndCousins(Room r) {
        System.out.println("Checking For Cousins and Uncles for room " + r.getId());
        if (!r.getConnectedToUncle()) {
            Random randomNumber = new Random();
            if (theDungeon.get(theDungeon.get(r.getParentPosition()).getParentPosition()).getNormalConnectedRooms().size() > 1) { //if the current rooms G parent has more than 1 kid
                for (int uncle : theDungeon.get(theDungeon.get(r.getParentPosition()).getParentPosition()).getNormalConnectedRooms()) {//for each of those children
                    if (uncle != r.getParentPosition()) { //and it's not your parent
                        if ((!theDungeon.get(uncle).getConnectedToNephew()) && (!r.getConnectedToUncle())) {
                            if ((randomNumber.nextInt(101) + 1) > 50) {
                                if (maxConnectionsNotMet(r) && maxConnectionsNotMet(theDungeon.get(uncle))) { //don't add if max connections met for either
                                    r.addExtraConnection(uncle);//maybe add it.
                                    theDungeon.get(uncle).addExtraConnection(r.getId());//add to both so they're aware of each other
                                    r.setConnectedToUncle(true);
                                    theDungeon.get(uncle).setConnectedToNephew(true);
                                }
                            }
                        }
                        if (!r.getConnectedToCousin()) {
                            for (int cousin : theDungeon.get(uncle).getNormalConnectedRooms()) {
                                if ((!theDungeon.get(cousin).getConnectedToCousin()) && (!r.getConnectedToCousin())) {
                                    if ((randomNumber.nextInt(101) + 1 > 50)) {
                                        if (maxConnectionsNotMet(r) && maxConnectionsNotMet(theDungeon.get(cousin))) { //don't add if max connections met for either
                                            r.addExtraConnection(cousin);
                                            theDungeon.get(cousin).addExtraConnection(r.getId());
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

    private void setUpSecretConnections() {
        Random randomNumber = new Random();
        for (int i = Integer.parseInt(secretPaths.getText().toString()); i > 0; i--) {
            System.out.println("Making Secret connection " + i);
            //pick two random rooms from the dungeon
            Room theChosenOne;
            Room theChosenTwo;
            //make sure they're not the same...
            do {
                theChosenOne = theDungeon.get(randomNumber.nextInt(theDungeon.size()));
                theChosenTwo = theDungeon.get(randomNumber.nextInt(theDungeon.size()));
            } while (theChosenOne.getId().equals(theChosenTwo.getId()));
            //join them together if there's not already a connection.
            if (!theChosenOne.getSecretConnectedRooms().contains(theChosenTwo.getId())) {
                theChosenOne.addSecretConnectedRooms(theChosenTwo.getId());
                theChosenTwo.addSecretConnectedRooms(theChosenOne.getId());
            } else {
                i += 1; //so we don't waste our time and can just repeat again.
            }
        }
    }
}
