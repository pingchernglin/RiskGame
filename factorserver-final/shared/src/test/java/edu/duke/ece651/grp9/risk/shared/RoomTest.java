package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @Test
    void isFull() throws IOException {
        // make a room for 2 players
        Room room = new Room(2);
        room.addUser(new User("a", "a"));

        assertEquals(false, room.isFull());
        assertEquals("1/2", room.roomFull());

        room.addUser(new User("b", "b"));
        assertEquals(true, room.isFull());
        assertEquals("2/2", room.roomFull());

        assertEquals(0, room.getSocketList().size());

    }

    @Test
    void test_roomfactory() {
        RoomFactory roomFactory = new RoomFactory();
        Room room1 = roomFactory.makeRoom(2);
        assertEquals( "Room for 2", room1.getName());

        Room room2 = roomFactory.makeRoom(3);
        assertEquals( "Room for 3", room2.getName());

        Room room3 = roomFactory.makeRoom(4);
        assertEquals( "Room for 4", room3.getName());

        Room room4 = roomFactory.makeRoom(5);
        assertEquals( "Room for 5", room4.getName());

        Room room5 = roomFactory.makeRoom(-1);
        assertEquals(null, room5);
    }


}