package com.KoreyMacGill.randomdungeongenerator.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private String id;
    private Room parent;
    private String description;
    private List<Room> connectedRooms;

    public Room() {
        this.id = "Generate a room";
        this.description = "Click the generate Dungeon button to begin";
        this.connectedRooms = new ArrayList<>();
    }

    public Room(Room par, String id) {
        this.parent = par;
        this.id = id;
        this.connectedRooms = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Boolean hasKids() {
        return this.connectedRooms.isEmpty();
    }

    public Room getParent() {
        return this.parent;
    }

    public void setParent(Room parent) {
        this.parent = parent;
    }

    public void setId(String id) {
        this.id = id;
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
