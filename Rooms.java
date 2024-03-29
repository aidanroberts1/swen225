import java.util.ArrayList;

public class Rooms implements Card {
    String name;
    int RoomNumber;
    Boolean MurderRoom;
    ArrayList<boardspot> corners;
    ArrayList<boardspot> notAvailable;
    ArrayList<boardspot>Doors;
    public Rooms(String n, int rn, boolean mr, ArrayList<boardspot> c, ArrayList<boardspot> na){
        name = n;
        RoomNumber = rn;
        MurderRoom = mr;
        corners = c;
        notAvailable =  na;

    }

    public Rooms(String n, int rn, boolean mr, ArrayList<boardspot> c, ArrayList<boardspot> na, ArrayList<boardspot> d){
        name = n;
        RoomNumber = rn;
        MurderRoom = mr;
        corners = c;
        notAvailable =  na;
        Doors = d;
    }

    public ArrayList<boardspot> getNotAvailable() { return notAvailable; }
    public void setNotAvailable(ArrayList<boardspot> notAvailable) { this.notAvailable = notAvailable; }
    public ArrayList<boardspot> getCorners() { return corners; }
    public void setCorners(ArrayList<boardspot> corners) { this.corners = corners; }
    ArrayList boardpos [][] = new ArrayList[24][25];
    public Boolean getMurderRoom() { return MurderRoom; }
    public void setMurderRoom(Boolean murderRoom) { MurderRoom = murderRoom; }
    public int getRoomNumber() { return RoomNumber; }
    public void setRoomNumber(int roomNumber) { RoomNumber = roomNumber; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public ArrayList<boardspot> getDoors() { return Doors; }
    public void setDoors(ArrayList<boardspot> doors) { Doors = doors; }


    @Override
    public String toString() {
        return "Rooms{}";
    }



}
