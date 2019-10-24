package com.KoreyMacGill.randomdungeongenerator.room;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id; //the position in the list this room resides in
    private String title; //fancy text stuff
    private int parentId; //the position of the "parent" room of this room.
    private String description; //more fancy text stuff

    private List<Integer> normalConnectedRooms; //list of positions this room is connected to.
    private List<Integer> extraConnectedRooms; //list of additional connections
    private List<Integer> secretConnectedRooms; //list of secret ones

    private Boolean connectedToCousin;
    private Boolean connectedToUncle;
    private Boolean connectedToSibling;
    private Boolean connectedToNephew;

    public Room() {
        this.description = "Click the generate Dungeon button to begin";
        this.normalConnectedRooms = new ArrayList<>();
        this.extraConnectedRooms = new ArrayList<>();
        this.secretConnectedRooms = new ArrayList<>();
        connectedToCousin = false;
        connectedToUncle = false;
        connectedToSibling = false;
        connectedToNephew = false;
    }

    public Room(int par, int id) {
        this.parentId = par;
        this.title = "Room " + id;
        this.id = id;
        this.normalConnectedRooms = new ArrayList<>();
        this.extraConnectedRooms = new ArrayList<>();
        this.secretConnectedRooms = new ArrayList<>();
        connectedToCousin = false;
        connectedToUncle = false;
        connectedToSibling = false;
        connectedToNephew = false;
    }

    public int getParentPosition() {
        return this.parentId;
    }

    public void setParentPosition(int passedId) {
        this.parentId = passedId;
    }

    public void setTitle(String text){
        this.title = text;
    }

    public String getTitle(){
        return this.title;
    }

    public Integer getId() {
        return id;
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

    public List<Integer> getAllConnections(){
        List<Integer> returnThisone = new ArrayList<>();
        returnThisone.addAll(this.normalConnectedRooms);
        returnThisone.addAll(this.extraConnectedRooms);
        returnThisone.addAll(this.secretConnectedRooms);
        return returnThisone;
    }

    public List<Integer> getNormalConnectedRooms() {
        return normalConnectedRooms;
    }

    public void addNormalConnection(int roomId) {
        this.normalConnectedRooms.add(roomId);
    }

    public void setNormalConnectedRooms(List<Integer> normalConnectedRooms) {
        this.normalConnectedRooms = normalConnectedRooms;
    }

    public List<Integer> getExtraConnectedRooms() {
        return extraConnectedRooms;
    }

    public void addExtraConnection(int roomId){
        this.extraConnectedRooms.add(roomId);
    }

    public void setExtraConnectedRooms(List<Integer> extraConnectedRooms) {
        this.extraConnectedRooms = extraConnectedRooms;
    }

    public List<Integer> getSecretConnectedRooms() {
        return secretConnectedRooms;
    }

    public void addSecretConnectedRooms(int roomId){
        this.secretConnectedRooms.add(roomId);
    }

    public void setSecretConnectedRooms(List<Integer> secretConnectedRooms) {
        this.secretConnectedRooms = secretConnectedRooms;
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
