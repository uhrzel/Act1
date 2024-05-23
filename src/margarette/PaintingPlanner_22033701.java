package margarette;

import java.io.*;
import java.util.*;

public class PaintingPlanner_22033701 {
    private static ArrayList<Paint_22033701> paints = new ArrayList<>();
    private static ArrayList<House_22033701> houses = new ArrayList<>();
 private static boolean hasUnsavedChanges = false;
    public static void main(String[] args) {
        loadPaints("paints.txt");
        loadHouses("houses.txt");

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            displayMenu();
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                scanner.next(); // Clear invalid input
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    generatePaintPriceReport();
                    break;
                case 2:
                    addNewPaint(scanner);
                    break;
                case 3:
                    editPaintPrice(scanner);
                    break;
                case 4:
                    generateHousePaintingReport(scanner);
                    break;
                case 5:
               addNewHouse(houses, scanner);

                    break;
                case 6:
                    saveData();
                    break;
                case 7:
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        } while (choice != 7);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("Main Menu:");
        System.out.println("1. Generate Paint Price Report.");
        System.out.println("2. Add a New Paint.");
        System.out.println("3. Edit Paint Price.");
        System.out.println("4. Generate House Painting Report.");
        System.out.println("5. Add a New House for Painting.");
        System.out.println("6. Save Data.");
        System.out.println("7. Exit Program.");
        System.out.print("Enter your choice: ");
    }

 private static void loadPaints(String filename) {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
       String[] data = line.split(",");
if (data.length >= 6) {
    Paint_22033701 paint = new Paint_22033701(data[0], data[1], data[2], data[3], Double.parseDouble(data[4]), Double.parseDouble(data[5]));
    paints.add(paint);
} else {
    System.out.println("Invalid data format: " + line);
}

        }
    } catch (IOException e) {
        System.out.println("Error loading paints: " + e.getMessage());
    }
}

   private static void loadHouses(String filename) {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String address = parts[0];
                String houseID = parts[1];
                House_22033701 house = new House_22033701(address, 0, 0, houseID, houseID);
                houses.add(house);
            } else {
                System.out.println("Invalid data format: " + line);
            }
        }
    } catch (IOException e) {
        System.out.println("Error loading houses: " + e.getMessage());
    }
}

    private static void generatePaintPriceReport() {
        paints.sort(Comparator.comparingDouble(Paint_22033701::getPricePerLitre));

        System.out.printf("%-12s %-10s %-10s %-10s %-15s%n", "Barcode", "Brand", "Colour", "Sheen", "Price Per Litre");
        System.out.println("--------------------------------------------------------------");
        for (Paint_22033701 paint : paints) {
            System.out.printf("%-12s %-10s %-10s %-10s $%-14.2f%n", paint.getBarcode(), paint.getBrand(), paint.getColor(), paint.getSheen(), paint.getPricePerLitre());
        }
    }

   private static void addNewPaint(Scanner scanner) {
    while (true) {
        System.out.print("Enter barcode (10 digits): ");
        String barcode = scanner.nextLine();
        if (barcode.length() != 10 || !barcode.matches("\\d+")) {
            System.out.println("Invalid barcode. It must be exactly 10 digits.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        if (isBarcodeDuplicate(barcode)) {
            System.out.println("Barcode already exists. Please enter a unique barcode.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();
        if (brand.isEmpty()) {
            System.out.println("Brand cannot be empty.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        if (color.isEmpty()) {
            System.out.println("Color cannot be empty.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        System.out.print("Enter sheen: ");
        String sheen = scanner.nextLine();
        if (sheen.isEmpty()) {
            System.out.println("Sheen cannot be empty.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        System.out.print("Enter price per litre: ");
        double pricePerLitre;
        try {
            pricePerLitre = Double.parseDouble(scanner.nextLine());
            if (pricePerLitre <= 0) {
                System.out.println("Price per litre must be greater than zero.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Please enter a valid number.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        System.out.print("Enter square meters per litre: ");
        double sqMetersPerLiter;
        try {
            sqMetersPerLiter = Double.parseDouble(scanner.nextLine());
            if (sqMetersPerLiter <= 0) {
                System.out.println("Square meters per litre must be greater than zero.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid number.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        Paint_22033701 paint = new Paint_22033701(barcode, brand, color, sheen, pricePerLitre, sqMetersPerLiter);
        paints.add(paint);
         hasUnsavedChanges = true;  // Mark changes as unsaved
        System.out.println("Paint added successfully.");
        break;
    }
}

private static boolean getRetryChoice(Scanner scanner) {
    while (true) {
        System.out.print("Do you want to try again? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("yes")) return true;
        if (choice.equals("no")) return false;
        System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
    }
}

    private static boolean isBarcodeDuplicate(String barcode) {
        for (Paint_22033701 paint : paints) {
            if (paint.getBarcode().equals(barcode)) {
                return true;
            }
        }
        return false;
    }

 private static void editPaintPrice(Scanner scanner) {
    while (true) {
        System.out.print("Enter the 10-digit barcode of the paint: ");
        String barcode = scanner.nextLine();
        if (barcode.length() != 10 || !barcode.matches("\\d+")) {
            System.out.println("Invalid barcode. It must be exactly 10 digits.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        Paint_22033701 paint = findPaintByBarcode(barcode);
        if (paint == null) {
            System.out.println("Paint with the given barcode not found.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        System.out.println("Paint found!");
        System.out.printf("%-12s %-10s %-10s %-10s %-15s%n", "Barcode", "Brand", "Colour", "Sheen", "Price Per Litre");
        System.out.println("--------------------------------------------------------------");
        System.out.printf("%-12s %-10s %-10s %-10s $%-14.2f%n", paint.getBarcode(), paint.getBrand(), paint.getColor(), paint.getSheen(), paint.getPricePerLitre());

        System.out.print("Enter the new price per litre: ");
        double newPrice;
        try {
            newPrice = Double.parseDouble(scanner.nextLine());
            if (newPrice <= 0) {
                System.out.println("Price per litre must be greater than zero.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Please enter a valid number.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }

        paint.setPricePerLitre(newPrice);
        hasUnsavedChanges = true;  // Mark changes as unsaved

        System.out.println("Paint price updated successfully.");
        break;
    }
}
private static Paint_22033701 findPaintByBarcode(String barcode) {
    for (Paint_22033701 paint : paints) {
        if (paint.getBarcode().equals(barcode)) {
            return paint;
        }
    }
    return null;
}

   private static void generateHousePaintingReport(Scanner scanner) {
    System.out.print("Enter the ID of the house to be painted: ");
    String houseID = scanner.nextLine();

    // Find the house by ID
    House_22033701 house = findHouseByID(houseID);
    if (house == null) {
        System.out.println("House with the given ID not found.");
        return;
    }

    // Display house information
    System.out.println("House Painting Report:");
    System.out.println("Address: " + house.getAddress());
    System.out.println("Notes: " + house.getNotes());
    System.out.printf("Square metres per litre: %.2f%n", house.getSqMetersPerLiter());
    System.out.printf("Square metres per hour: %.2f%n", house.getSqMetersPerHour());
    System.out.println("\nRooms to be Painted\n");

    // Display table header
    System.out.printf("%-15s %-10s %-5s %-10s %-25s %-10s %-10s %-10s %-10s%n",
        "Description", "Area", "Coats", "Area*Coats", "Paint", "Price/Litre", "Litres Needed", "Paint Cost", "Hours Needed");
    System.out.println("-----------------------------------------------------------------------------------------------------------------------");

    double totalPaintCost = 0;
    double totalHoursNeeded = 0;

    for (Room_22033701 room : house.getRooms()) {
        double roomArea = room.calculateSurfaceArea();
        double areaCoats = roomArea * room.getCoats();
        Paint_22033701 paint = room.getPaint();
        double litresNeeded = areaCoats / house.getSqMetersPerLiter();
        double paintCost = litresNeeded * paint.getPricePerLitre();
        double hoursNeeded = areaCoats / house.getSqMetersPerHour();

        // Display room information
        System.out.printf("%-15s %-10.2f %-5d %-10.2f %-25s $%-10.2f %-10.2f $%-10.2f %-10.2f%n",
            room.getDescription(), roomArea, room.getCoats(), areaCoats,
            paint.getBrand() + " " + paint.getColor() + " " + paint.getSheen(),
            paint.getPricePerLitre(), litresNeeded, paintCost, hoursNeeded);

        totalPaintCost += paintCost;
        totalHoursNeeded += hoursNeeded;
    }

    // Display totals
    System.out.println("----------------------------------------------------------------------------------------------------------------------");
    System.out.printf("%-85s $%-10.2f %-10.2f%n", "TOTAL", totalPaintCost, totalHoursNeeded);
    
}

private static House_22033701 findHouseByID(String houseID) {
    for (House_22033701 house : houses) {
        if (house.getHouseID().equals(houseID)) {
            return house;
        }
    }
    return null;
}

private static void addNewHouse(ArrayList<House_22033701> houses, Scanner scanner) {

    String streetNumber, street, suburb, state, postcode, notes;
    double sqMetersPerHour, sqMetersPerLiter;

    // Validate address details
    while (true) {
        System.out.print("Enter the street number: ");
        streetNumber = scanner.nextLine().trim();
        if (streetNumber.isEmpty()) {
            System.out.println("Street number cannot be empty.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        System.out.print("Enter the street: ");
        street = scanner.nextLine().trim();
        if (street.isEmpty()) {
            System.out.println("Street cannot be empty.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        System.out.print("Enter the suburb: ");
        suburb = scanner.nextLine().trim();
        if (suburb.isEmpty()) {
            System.out.println("Suburb cannot be empty.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        System.out.print("Enter the postcode: ");
        postcode = scanner.nextLine().trim();
        if (postcode.length() != 4 || !postcode.matches("\\d+")) {
            System.out.println("Postcode must be 4 digits.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        System.out.print("Enter the state: ");
        state = scanner.nextLine().trim().toUpperCase();
        if (!Arrays.asList("NSW", "QLD", "VIC", "TAS", "SA", "NT", "WA").contains(state)) {
            System.out.println("State must be one of NSW, QLD, VIC, TAS, SA, NT, or WA.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        break;
    }

    // Validate painting estimates
    while (true) {
        System.out.print("Enter the estimated square metres per hour: ");
        try {
            sqMetersPerHour = Double.parseDouble(scanner.nextLine().trim());
            if (sqMetersPerHour <= 0 || sqMetersPerHour > 10) {
                System.out.println("Square meters per hour must be greater than 0 and less than or equal to 10.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid number.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        break;
    }

    while (true) {
        System.out.print("Enter the estimated square metres per litre: ");
        try {
            sqMetersPerLiter = Double.parseDouble(scanner.nextLine().trim());
            if (sqMetersPerLiter <= 0 || sqMetersPerLiter > 10) {
                System.out.println("Square meters per litre must be greater than 0 and less than or equal to 10.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid number.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        break;
    }

    System.out.print("Enter any additional notes about the house: ");
    notes = scanner.nextLine().trim();

    // Generate unique house ID
    String houseID = generateUniqueHouseID(houses);

    // Create address object
    Address_22033701 address = new Address_22033701(streetNumber + " " + street, suburb, state, postcode);

    // Create house object
    House_22033701 house = new House_22033701(address.toString(), sqMetersPerHour, sqMetersPerLiter, notes, houseID);

    // Validate and add rooms
    int numRooms;
    while (true) {
        System.out.print("How many rooms need to be painted: ");
        try {
            numRooms = Integer.parseInt(scanner.nextLine().trim());
            if (numRooms <= 0) {
                System.out.println("Number of rooms must be greater than zero.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Please enter a valid number.");
            if (!getRetryChoice(scanner)) return;
            continue;
        }
        break;
    }

    for (int i = 1; i <= numRooms; i++) {
        String description;
        double height, width, length;
        int coats;
        String paintBarcode;

        while (true) {
            System.out.printf("Enter room #%d's description: ", i);
            description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Description cannot be empty.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
            break;
        }

        while (true) {
            System.out.printf("Enter room #%d's height: ", i);
            try {
                height = Double.parseDouble(scanner.nextLine().trim());
                if (height <= 0) {
                    System.out.println("Height must be greater than zero.");
                    if (!getRetryChoice(scanner)) return;
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
            break;
        }

        while (true) {
            System.out.printf("Enter room #%d's width: ", i);
            try {
                width = Double.parseDouble(scanner.nextLine().trim());
                if (width <= 0) {
                    System.out.println("Width must be greater than zero.");
                    if (!getRetryChoice(scanner)) return;
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
            break;
        }

        while (true) {
            System.out.printf("Enter room #%d's length: ", i);
            try {
                length = Double.parseDouble(scanner.nextLine().trim());
                if (length <= 0) {
                    System.out.println("Length must be greater than zero.");
                    if (!getRetryChoice(scanner)) return;
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
            break;
        }

        while (true) {
            System.out.printf("Enter the number of coats of paint required for room #%d: ", i);
            try {
                coats = Integer.parseInt(scanner.nextLine().trim());
                if (coats <= 0 || coats >= 4) {
                    System.out.println("Number of coats must be greater than 0 and less than 4.");
                    if (!getRetryChoice(scanner)) return;
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid number.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
            break;
        }

        while (true) {
            System.out.printf("Enter the paint's barcode for room #%d: ", i);
            paintBarcode = scanner.nextLine().trim();
            Paint_22033701 paint = findPaintByBarcode(paintBarcode);
            if (paint == null) {
                System.out.println("Paint with the given barcode not found.");
                if (!getRetryChoice(scanner)) return;
                continue;
            }
            break;
        }

        // Create room object and add to house
        Paint_22033701 paint = findPaintByBarcode(paintBarcode);
        Room_22033701 room = new Room_22033701(description, height, width, length, paint, coats);
        house.addRoom(room);
    }

    // Add house to list
    houses.add(house);
    hasUnsavedChanges = true;  // Mark changes as unsaved

    System.out.println("New house added with ID of " + houseID);
}

private static String generateUniqueHouseID(ArrayList<House_22033701> houses) {
    Random random = new Random();
    String houseID;
    boolean unique;
    do {
        houseID = String.format("%08d", random.nextInt(100000000));
        unique = true;
        for (House_22033701 house : houses) {
            if (house.getHouseID().equals(houseID)) {
                unique = false;
                break;
            }
        }
    } while (!unique);
    return houseID;
}


private static void saveData() {
    try (PrintWriter paintWriter = new PrintWriter(new FileWriter("paints.txt"));
         PrintWriter houseWriter = new PrintWriter(new FileWriter("houses.txt"));
         PrintWriter addressWriter = new PrintWriter(new FileWriter("addresses.txt"));
         PrintWriter roomWriter = new PrintWriter(new FileWriter("rooms.txt"))) {

        // Save paints
        for (Paint_22033701 paint : paints) {
            paintWriter.println(paint.getBarcode() + "," + paint.getBrand() + "," + paint.getColor() + "," +
                                paint.getSheen() + "," + paint.getPricePerLitre() + "," + paint.getSqMetersPerLiter());
        }

        // Save houses, addresses, and rooms
        for (House_22033701 house : houses) {
            Address_22033701 address = house.getAddress();
            houseWriter.println(house.getHouseID() + "," + house.getSqMetersPerHour() + "," + 
                                house.getSqMetersPerLiter() + "," + house.getNotes());
            addressWriter.println(house.getHouseID() + "," + address.getStreet() + "," +
                                  address.getSuburb() + "," + address.getState() + "," +
                                  address.getPostcode());

            for (Room_22033701 room : house.getRooms()) {
                roomWriter.println(house.getHouseID() + "," + room.getDescription() + "," +
                                   room.getHeight() + "," + room.getWidth() + "," + room.getLength() + "," +
                                   room.getPaintBarcode() + "," + room.getCoats());
            }
        }

    } catch (IOException e) {
        System.out.println("Error saving data: " + e.getMessage());
    }
    hasUnsavedChanges = false;  // Data is now saved
    System.out.println("Data saved successfully.");
}

   private static void exitProgram(Scanner scanner) {
        if (hasUnsavedChanges) {
            System.out.println("You have unsaved changes. Do you want to save before exiting?");
            System.out.println("1. Save and exit");
            System.out.println("2. Return to main menu");
            System.out.println("3. Exit without saving");

            int choice;
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    scanner.next(); // Clear invalid input
                }
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        saveData();
                        System.out.println("Exiting program.");
                        System.exit(0); // Terminate the program
                        break;
                    case 2:
                        return; // Return to main menu
                    case 3:
                        System.out.println("Exiting program without saving.");
                        System.exit(0); // Terminate the program
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } while (true);
        } else {
            System.out.println("Exiting program.");
            System.exit(0); // Terminate the program
        }
    }



}
