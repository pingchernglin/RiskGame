package edu.duke.ece651.grp9.risk.shared;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class Room {

    private String name;
    private int numPlayers;
    private ArrayList<Socket> socketList;
    private HashSet<User> userList;
    public Map map;
    public ArrayList<User> myCurrentUser;
    public boolean startOrnot;

    public Room(int numPlayers) {
        RoomFactory roomFactory = new RoomFactory();
        this.map = roomFactory.makeRoom(numPlayers).map;
        this.numPlayers = numPlayers;
        this.socketList = new ArrayList<>();
        this.userList = new HashSet<>();
        this.startOrnot = false;
    }

    public Room(String name, Map map) {
        this.name = name;
        this.map = map;
    }

    /**
     * check if the room is full
     */
    public boolean isFull(){
        if (userList.size() >= numPlayers){
            return true;
        } else{
            return false;
        }
    }

    /**
     * add a socket to a new player
     * @param socket
     */
    public void addSocket(Socket socket){
       socketList.add(socket);
    } 

    public ArrayList<Socket> getSocketList(){
      return socketList;
    }

    public void addUser( User user){
        userList.add(user);
    }

    /*
    For UI part  printing
     */
    public String roomFull(){
        String a = String.valueOf(userList.size()) + "/" + String.valueOf(numPlayers);
        System.out.println(a);
        return a;
    }

    public String getName() {
        return name;
    }
}
