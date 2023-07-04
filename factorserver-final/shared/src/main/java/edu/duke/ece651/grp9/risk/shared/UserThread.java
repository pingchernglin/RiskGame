package edu.duke.ece651.grp9.risk.shared;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class UserThread extends Thread {
    ArrayList<RoomThread> ActiveroomThreadList = new ArrayList<>();
    // private static HashMap<String, String> userPassPairs;
    User user;
    Room room_1;
    Room room_2;
    Room room_3;
    Room room_4;
    RoomThread roomThread1;
    RoomThread roomThread2;
    RoomThread roomThread3;
    RoomThread roomThread4;
    Socket socket;
    GamePlay gameplay;


    //  Socket socket;


    //user只选了一个room 去玩， 但是它可以选多个
    public UserThread(Socket socket, Room room_1, Room room_2, Room room_3, Room room_4, RoomThread roomThread1, RoomThread roomThread2, RoomThread roomThread3, RoomThread roomThread4, GamePlay gameplay) {
        ActiveroomThreadList = new ArrayList<>();
        //this.socket = new Socket();
        this.roomThread1 = roomThread1;
        this.roomThread2 = roomThread2;
        this.roomThread3 = roomThread3;
        this.roomThread4 = roomThread4;
        this.room_1 = room_1;
        this.room_2 = room_2;
        this.room_3 = room_3;
        this.room_4 = room_4;
        this.socket = socket;
        this.gameplay = gameplay;

    }

    @Override
    public void run() {

//        try(ServerSocket ss = new ServerSocket(8080)) {
//          for (int i = 0; i < 2; i++) {
//            Socket s = ss.accept();
//          }
//
//          System.out.println("In the UserThread");
//          Socket socket = ss.accept();

        System.out.println("In the UserThread");

        try {

            //Step1: 请输入你的username 和 password --  回传给client的东西是 account_check
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);


            String password_check = gameplay.storeUserNameAndPassword(objectInputStream);
            String password_correct = "true";
            // add the checker
            //if everything is good, we will send "true" to the client
            // 检验我们输入的内容或者password是否是对的
            while (password_check == null) {

                objectOutputStream.writeObject("false");
                //read the new username/password from the client
                password_check = gameplay.storeUserNameAndPassword(objectInputStream);
            }
            if (password_check != null) {
                password_correct = "true";
                System.out.println("Everything good in the login part");
                objectOutputStream.writeObject(password_correct);
                //不管它之前有没有登陆过，我们都一定会返回这个user
                this.user = new User(password_check, gameplay.userPassPairs.get(password_check));

            }
            //questions 如何确定两个user 的相等 还需要写一个hashFunction

            if (gameplay.UserList.containsKey(user)) {
                this.socket = gameplay.UserList.get(user);
            } else {
                gameplay.UserList.put(user, this.socket);
            }

            /**
             *
             *if( gameplay.userPassPairs.containsKey(username){
             *  this.socket =
             * })
             * 在 端新建一个global 的变量，userLIst
             * 如果这个user曾经有进来过，代表它存在在userlist 当中，应该放相同的Socket.
             * 如果这是一个新加入的user的话，我们就build一个新的userlist,然后把它放到userlist
             */

            /**********************************************************************************/
            ArrayList<Room> rooms = new ArrayList<>();
            ArrayList<RoomThread> roomThreads = new ArrayList<>();
            rooms.add(room_1);
            rooms.add(room_2);
            rooms.add(room_3);
            rooms.add(room_4);
            roomThreads.add(roomThread1);
            roomThreads.add(roomThread2);
            roomThreads.add(roomThread3);
            roomThreads.add(roomThread4);

            for (Room room : rooms) {
                if (room.isFull()) {
                    room.startOrnot = true;
                    objectOutputStream.reset();
                    objectOutputStream.writeObject("true");
                } else {
                    objectOutputStream.reset();
                    objectOutputStream.writeObject("false");
                }
            }

            int room_id = (int) objectInputStream.readObject();
            for (int i = 1; i <= 4; i++) {
                if (room_id == i) {
                    Room room = rooms.get(room_id-1);
                    RoomThread roomThread = roomThreads.get(room_id-1);
                    room.addUser(user);
                    room.addSocket(socket);
                    roomThread.InputList.add(objectInputStream);
                    roomThread.OutputList.add(objectOutputStream);
                    ActiveroomThreadList.add(roomThread);

                    if (room.isFull() && room.startOrnot == false) {
                        roomThread.start();
                        System.out.println("Room_" + i + " already start");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e);

        }

    }


}


