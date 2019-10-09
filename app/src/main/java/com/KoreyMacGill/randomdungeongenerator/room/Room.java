package com.KoreyMacGill.randomdungeongenerator.room;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String id;
    private String parentID;
    private String title;
    private String description;
    private List<Room> connectedRooms;

    public Room() {
        this.connectedRooms = new ArrayList<>();
    }

    public Room(String parentID) {
        this.parentID = parentID;
        this.connectedRooms = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Boolean hasKids() {
        return this.connectedRooms.isEmpty();
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        parentID = parentID;
    }

    public void setId(String id) {
        this.id = id;
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

    public void addChildRoom(Room room) {
        this.connectedRooms.add(room);
    }

    public void setConnectedRooms(List<Room> connectedRooms) {
        this.connectedRooms = connectedRooms;
    }

    public Room getConnectedRoom(int place) {
        return this.connectedRooms.get(place);
    }
}
