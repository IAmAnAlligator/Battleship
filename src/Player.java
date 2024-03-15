import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private String name;
    private char[][] field = new char[10][10];
    private char[][] gameProcessField = new char[10][10];
    private List<Ship> fleet;

    public Player() {
        for (char[] chars : this.field) {
            Arrays.fill(chars, '0');
        }
        for (char[] chars : this.gameProcessField) {
            Arrays.fill(chars, '0');
        }
    }

    public boolean isFleetActive() {

        for (Ship ship : fleet) {
            if (ship.isActive()) {
                return true;
            }
        }

        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[][] getField() {
        return field;
    }

    public void setField(char[][] field) {
        this.field = field;
    }

    public List<Ship> getFleet() {
        return fleet;
    }

    public void setFleet(List<Ship> fleet) {
        this.fleet = fleet;
    }

    public char[][] getGameProcessField() {
        return gameProcessField;
    }

    public void setGameProcessField(char[][] gameProcessField) {
        this.gameProcessField = gameProcessField;
    }

    public Ship getShipByPlace(String place) {

        for (int i = 0; i < getFleet().size(); i++) {

            for (int j = 0; j < getFleet().get(i).getPlace().size(); j++) {
                if (getFleet().get(i).getPlace().get(j).equals(place)) {
                    return getFleet().get(i);
                }
            }
        }

        return null;
    }

    public void attack(Player d, String place) {

        int digit = Game.GAME.parseDigit(place);
        int letter = Game.GAME.parseLetter(place);

        if (d.getField()[digit][letter] == '1') {
            d.getField()[digit][letter] = 'X';
            gameProcessField[digit][letter] = 'X';

            if (d.getShipByPlace(place) != null) {
                Ship ship = d.getShipByPlace(place);
                ship.setSize(ship.getSize() - 1);

                if (ship.getSize() > 0) {
                    System.out.println("Ship was damaged");
                } else if (ship.getSize() == 0) {
                    ship.setActive(false);
                    System.out.println(ship.getShipClass() + " was destroyed");
                }
            }

        } else {
            d.getField()[digit][letter] = 'X';
            gameProcessField[digit][letter] = 'X';
            System.out.println(getName() + " missed!");
        }
    }

    public List<String> getListOfPlaces() {
        List<String> places = new ArrayList<>();

        for (Ship ship : fleet) {
            places.addAll(ship.getPlace());
        }

        return places;
    }

}
