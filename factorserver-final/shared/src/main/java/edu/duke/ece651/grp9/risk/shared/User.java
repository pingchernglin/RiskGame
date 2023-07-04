package edu.duke.ece651.grp9.risk.shared;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class User {

    String userName;
    String password;
    //For each player, its room and its corresponding colors.
    HashMap<Room,String> myRoleList;
    //just one hashmap
    Room myCurrentRoom;
    String myCurrentColor;
    ObjectInputStream objectInputStream;
    ObjectOutputStream objectOutputStream;
    public String status; //log in 和  Log out //switch

    public User(String userName,String password){
        this.userName = userName;
        this.password = password;
    }

    public boolean activeUser(){
        if(status.equals("out")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public int hashCode() {
        return this.userName.hashCode();
    }



}
