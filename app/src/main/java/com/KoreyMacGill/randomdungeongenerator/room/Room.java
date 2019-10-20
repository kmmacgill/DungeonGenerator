package com.KoreyMacGill.randomdungeongenerator.room;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private String id;
    private Room parent;
    private String description;
    private List<Room> connectedRooms;

    private Boolean connectedToSibling;
    private Boolean connectedToUncle;
    private Boolean connectedtoCousin;

    public Room() {
        this.id = "Generate a room";
        this.description = "Click the generate Dungeon button to begin";
        this.connectedRooms = new ArrayList<>();
        this.connectedToSibling = false;
        this.connectedToUncle = false;
        this.connectedtoCousin = false;
    }

    public Room(Room par, String id) {
        this.parent = par;
        this.id = id;
        this.connectedRooms = new ArrayList<>();
        this.connectedToSibling = false;
        this.connectedToUncle = false;
        this.connectedtoCousin = false;
    }

    public Boolean getConnectedToSibling() {
        return connectedToSibling;
    }

    public void setConnectedToSibling(Boolean connectedToSibling) {
        this.connectedToSibling = connectedToSibling;
    }

    public Boolean getConnectedToUncle() {
        return connectedToUncle;
    }

    public void setConnectedToUncle(Boolean connectedToUncle) {
        this.connectedToUncle = connectedToUncle;
    }

    public Boolean getConnectedtoCousin() {
        return connectedtoCousin;
    }

    public void setConnectedtoCousin(Boolean connectedtoCousin) {
        this.connectedtoCousin = connectedtoCousin;
    }

    public String getId() {
        return id;
    }

    public Boolean hasKids() {
        return !this.connectedRooms.isEmpty();
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

    public void addConnectedRoom(Room room) {
        this.connectedRooms.add(room);
        room.setParent(this);
    }

    public void setConnectedRooms(List<Room> connectedRooms) {
        this.connectedRooms = connectedRooms;
    }

    public Room getConnectedRoom(int place) {
        return this.connectedRooms.get(place);
    }

    public Boolean hasParent(){
        return this.getParent() != null;
    }

    public Boolean hasSiblings(){
        return this.getParent().getConnectedRooms().size() > 1;
    }

    public List<Room> getSiblings() {
        List<Room> siblings = new ArrayList<>();
        for(Room r : this.getParent().getConnectedRooms()) {
            if (!r.getId().equals(this.id)) {
                siblings.add(r);
                break;
            }
        }
        return siblings;
    }

    public Boolean hasUncles() {
        if(this.getParent().hasParent()){
            return this.getParent().getParent().getConnectedRooms().size() > 1;
        }
        return false;
    }

    public List<Room> getUncles() {
        List<Room> uncles = new ArrayList<>();
        for(Room r : this.getParent().getParent().getConnectedRooms()){
            if(!r.getId().equals(this.getParent().getId())) {
                uncles.add(r);
                break;
            }
        }
        return uncles;
    }

    public Boolean hasCousins() {
        if(this.hasUncles()) {
            for (Room r : this.getUncles()) {
                if (r.hasKids()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Room> getCousins(){
        List<Room> cousins = new ArrayList<>();
        for (Room r : this.getUncles()){
            if(r.hasKids()){
                for(Room i : r.getConnectedRooms()){
                    cousins.add(i);
                    break;
                }
            }
        }
        return cousins;
    }
}
