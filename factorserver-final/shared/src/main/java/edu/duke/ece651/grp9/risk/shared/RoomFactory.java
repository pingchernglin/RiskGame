package edu.duke.ece651.grp9.risk.shared;

public class RoomFactory implements AbstractRoomFactory{


    /**
     * make a game room for num players
     * @param num is the player number for game
     * @return a room
     */
    public Room makeRoom(int num){
        if (num == 2){
            return makeRoomForTwo();
        } else if (num == 3){
            return makeRoomForThree();
        } else if (num == 4){
            return makeRoomForFour();
        } else if (num == 5){
            return makeRoomForFive();
        } else{
            return null;
        }
    }

    MapFactory mapFactory = new MapFactory();

    public Room makeRoomForTwo(){
        Map map = mapFactory.makeMap(2);
        return new Room("Room for 2", map);
    }
    public Room makeRoomForThree(){
        Map map = mapFactory.makeMap(3);
        return new Room("Room for 3", map);

    }

    public Room makeRoomForFour(){
        Map map = mapFactory.makeMap(4);
        return new Room("Room for 4", map);

    }

    public Room makeRoomForFive(){
        Map map = mapFactory.makeMap(5);
        return new Room("Room for 5", map);

    }
}
