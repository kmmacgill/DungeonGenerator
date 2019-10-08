package com.KoreyMacGill.randomdungeongenerator.room;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int ID;
    private String title;
    private String description;
    private List<Room> connectedRooms;

    public Room() {
        this.connectedRooms = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Room> getConnectedRooms() {
        return connectedRooms;
    }

    public void setConnectedRooms(List<Room> connectedRooms) {
        this.connectedRooms = connectedRooms;
    }

    public Room getConnectedRoom(int place) {
        return this.connectedRooms.get(place);
    }
}
