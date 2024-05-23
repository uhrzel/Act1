package margarette;

import java.util.ArrayList;

public class House_22033701 {
private Address_22033701 address;
    private double sqMetersPerHour;
    private double sqMetersPerLiter;
    private String notes;
    private String houseID;
    private ArrayList<Room_22033701> rooms;

    // New constructor
    public House_22033701(String address, double sqMetersPerHour, double sqMetersPerLiter, String notes, String houseID) {
   this.address = new Address_22033701(address, houseID, houseID, houseID); 
   
        this.sqMetersPerHour = sqMetersPerHour;
        this.sqMetersPerLiter = sqMetersPerLiter;
        this.notes = notes;
        this.houseID = houseID;
        this.rooms = new ArrayList<>();
    }

    // Existing constructor (can be retained for other uses)
    public House_22033701(String address) {
        this(address, 0, 0, "", ""); // Initialize with default values
    }

 // Getter method for the address
public Address_22033701 getAddress() {
    return address;
}

    
    public ArrayList<Room_22033701> getRooms() { return rooms; }
    public void addRoom(Room_22033701 room) { this.rooms.add(room); }
    public double getSqMetersPerHour() { return sqMetersPerHour; }
    public double getSqMetersPerLiter() { return sqMetersPerLiter; }
    public String getNotes() { return notes; }
    public String getHouseID() { return houseID; }

    public double calculateTotalPaintCost() {
        double totalCost = 0;
        for (Room_22033701 room : rooms) {
            totalCost += room.calculatePaintNeeded() * room.getPaint().getPricePerLitre();
        }
        return totalCost;
    }

    public double calculateTotalPaintTime() {
        double totalTime = 0;
        for (Room_22033701 room : rooms) {
            totalTime += room.calculateSurfaceArea() * room.getCoats();
        }
        return totalTime / sqMetersPerHour; // Use sqMetersPerHour for calculation
    }

    @Override
    public String toString() {
        return "House [address=" + address + ", rooms=" + rooms + ", houseID=" + houseID + "]";
    }
}
