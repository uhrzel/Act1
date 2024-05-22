package margarette;

public class Room_22033701 {
    private String description;
    private double height;
    private double width;
    private double length;
    private Paint_22033701 paint;
    private int coats;

    // Constructor
    public Room_22033701(String description, double height, double width, double length, Paint_22033701 paint, int coats) {
        this.description = description;
        this.height = height;
        this.width = width;
        this.length = length;
        this.paint = paint;
        this.coats = coats;
    }

    // Getter and setter methods
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    public double getLength() { return length; }
    public void setLength(double length) { this.length = length; }
public Paint_22033701 getPaint() {
    return paint;
}

    public void setPaint(Paint_22033701 paint) { this.paint = paint; }
    public int getCoats() { return coats; }
    public void setCoats(int coats) { this.coats = coats; }

    // Calculate surface area to be painted
    public double calculateSurfaceArea() {
        return 2 * height * (width + length);
    }

    // Calculate paint needed for the room
    public double calculatePaintNeeded() {
        return calculateSurfaceArea() * coats / paint.getSqMetersPerLiter();
    }

    @Override
    public String toString() {
        return "Room [description=" + description + ", height=" + height + ", width=" + width + ", length=" + length +
                ", paint=" + paint + ", coats=" + coats + "]";
    }

  public String getPaintBarcode() {
    return paint.getBarcode();
}

}
