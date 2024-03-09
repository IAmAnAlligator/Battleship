import java.util.List;

public class Ship {

    private String shipClass;
    private List<String> place;
    private int size;
    private boolean isActive = true;

    public Ship(String shipClass, int size, List<String> place) {
        this.shipClass = shipClass;
        this.size = size;
        this.place = place;
    }

    public List<String> getPlace() {
        return place;
    }

    public void setPlace(List<String> place) {
        this.place = place;
    }

    public String getShipClass() {
        return shipClass;
    }

    public void setShipClass(String shipClass) {
        this.shipClass = shipClass;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
