package com.KoreyMacGill.randomdungeongenerator.room;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id; //the position in the list this room resides in
    private String title; //fancy text stuff
    private int parentId; //the position of the "parent" room of this room.
    private String description; //more fancy text stuff
    private List<Integer> connectedRooms; //list of positions this room is connected to.

    Boolean connectedToCousin;
    Boolean connectedToUncle;
    Boolean connectedToSibling;
    Boolean connectedToNephew;

    public Room() {
        this.description = "Click the generate Dungeon button to begin";
        this.connectedRooms = new ArrayList<>();
        connectedToCousin = false;
        connectedToUncle = false;
        connectedToSibling = false;
        connectedToNephew = false;
    }

    public Room(int par, int id) {
        this.parentId = par;
        this.title = "Room " + id;
        this.id = id;
        this.connectedRooms = new ArrayList<>();
        connectedToCousin = false;
        connectedToUncle = false;
        connectedToSibling = false;
        connectedToNephew = false;
    }

    public Integer getId() {
        return id;
    }

    public int getParentPosition() {
        return this.parentId;
    }

    public void setTitle(String text){
        this.title = text;
    }

    public String getTitle(){
        return this.title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getConnectedRooms() {
        return connectedRooms;
    }

    public void addConnectedRoom(int room) {
        this.connectedRooms.add(room);
    }

    public Boolean getConnectedToCousin() {
        return connectedToCousin;
    }

    public void setConnectedToCousin(Boolean connectedToCousin) {
        this.connectedToCousin = connectedToCousin;
    }

    public Boolean getConnectedToUncle() {
        return connectedToUncle;
    }

    public void setConnectedToUncle(Boolean connectedToUncle) {
        this.connectedToUncle = connectedToUncle;
    }

    public Boolean getConnectedToSibling() {
        return connectedToSibling;
    }

    public void setConnectedToSibling(Boolean connectedToSibling) {
        this.connectedToSibling = connectedToSibling;
    }

    public Boolean getConnectedToNephew() {
        return connectedToNephew;
    }

    public void setConnectedToNephew(Boolean status){
        this.connectedToNephew = status;
    }
}
