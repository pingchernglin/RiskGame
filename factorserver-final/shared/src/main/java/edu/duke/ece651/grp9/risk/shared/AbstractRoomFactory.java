package edu.duke.ece651.grp9.risk.shared;

public interface AbstractRoomFactory {
    public Room makeRoomForTwo();
    public Room makeRoomForThree();
    public Room makeRoomForFour();
    public Room makeRoomForFive();
}

