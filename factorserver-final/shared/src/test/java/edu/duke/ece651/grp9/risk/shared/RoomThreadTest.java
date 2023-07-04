package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;


class RoomThreadTest {
    @Test
    public void test_room_thread() {
        Room room1 = new Room(2);
        RoomThread thread = new RoomThread(room1);
        thread.run();
    }


}