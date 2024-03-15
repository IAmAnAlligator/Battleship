import java.io.IOException;
import java.util.*;

public class Game {
    private final String BATTLESHIP = "Battleship";
    private final String CRUISER = "Cruiser";
    private final String DESTROYER = "Destroyer";
    private final String SUBMARINE = "Submarine";
    private final String ABCDEFGHIJ = "   A  B  C  D  E  F  G  H  I  J";

    static final Game GAME = new Game();
    private final Scanner SCANNER = new Scanner(System.in);
    private final Random RANDOM = new Random();
    private final Player PLAYER = new Player();
    private final Player BOT = new Player();

    public void startGame() {

        System.out.println("Enter your name, please:");
        PLAYER.setName(SCANNER.nextLine());

        menuInitField();

        System.out.println("Your field is: ");
        showField(PLAYER.getField(), PLAYER.getGameProcessField());

        BOT.setName("Bot");
        GAME.initRandomField(BOT);

        battleInput();
    }

    private void menuInitField() {
        System.out.println("Hello " + PLAYER.getName() + "!");
        System.out.println("Enter 1 - Play on a random field");
        System.out.println("Enter 2 - Initialize a field by your own");

        String choose = SCANNER.nextLine();

        boolean success = false;

        if (choose.equals("1")) {
            GAME.initRandomField(PLAYER);
        } else if (choose.equals("2")) {
            GAME.showField(PLAYER.getField());
            while (!success) {
                if (initField(PLAYER)) {
                    success = true;
                } else {
                    System.out.println("Incorrect field, try again");
                }
            }
        }
    }

    private void battleInput() {
        List<String> places = PLAYER.getListOfPlaces();

        while (PLAYER.isFleetActive() && BOT.isFleetActive()) {

            System.out.println(PLAYER.getName() + " attacks");

            boolean success = false;

            while (!success) {
                try {
                    String place = SCANNER.nextLine();
                    PLAYER.attack(BOT, place.toUpperCase());
                    success = true;
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Incorrect input, try again");
                }
            }

            String shoot = getPlaceForAttack(places);
            System.out.println(BOT.getName() + " attacks " + shoot);
            BOT.attack(PLAYER, shoot);
            showField(PLAYER.getField(), PLAYER.getGameProcessField());
        }

        System.out.println(PLAYER.isFleetActive() ? "Congratulations!\nYou won!" : "You lost!");
    }

    private String getPlaceForAttack(List<String> places) {

        String digits = "0123456789";
        String letters = "ABCDEFGHIJ";
        int randomDigit;
        char randomLetter;
        String result;

        int range = (int) (Math.random() * (4 - 1) + 1);

        if (range == 3) {
            int size = places.size() - 1;

            if (size == 0) {
                result = places.get(0);
                places.remove(0);
            } else {
                int randomPlace = (int) (Math.random() * (places.size() - 1) + 0) + 1;
                result = places.get(randomPlace);
                places.remove(randomPlace);
            }

        } else {
            randomDigit = (int) (Math.random() * (digits.length() - 1) + 0) + 1;
            randomLetter = letters.charAt(randomDigit);
            result = randomDigit + "" + randomLetter;
        }

        return result;

    }

    private boolean isPlaceFree(char[][] playerField, List<String> place) {
        for (String s : place) {
            if (playerField[parseDigit(s)][parseLetter(s)] == '1') {
                return false;
            }
        }

        return true;
    }

    private boolean isSizeCorrect(int size, int placeLength) {
        return size == placeLength;
    }

    private Ship initShip(char[][] playerField, String shipClass, int size) {

        while (true) {
            try {
                String place = SCANNER.nextLine().toUpperCase();
                List<String> placeSplit = List.of(place.split(" "));

                if (!isPlaceFree(playerField, placeSplit)) {
                    throw new IOException();
                }

                if (!isSizeCorrect(size, placeSplit.size())) {
                    throw new IOException();
                }

                Ship ship = new Ship(shipClass, size, placeSplit);
                GAME.addShip(playerField, ship);

                return ship;

            } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Incorrect input, try again");
            }
        }
    }

    private boolean initField(Player player) {

        char[][] playerField = player.getField();

        System.out.println("Enter coordinates for your " + BATTLESHIP + ", 4 squares:");
        System.out.println("e.g. 1A 1B 1C 1D");
        Ship battleship = initShip(playerField, BATTLESHIP, 4);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your " + CRUISER + ", 3 squares:");
        Ship cruiserA = initShip(playerField, CRUISER, 3);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your another " + CRUISER + ", 3 squares:");
        Ship cruiserB = initShip(playerField, CRUISER, 3);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your " + DESTROYER + ", 2 squares:");
        Ship destroyerA = initShip(playerField, DESTROYER, 2);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your another " + DESTROYER + ", 2 squares:");
        Ship destroyerB = initShip(playerField, DESTROYER, 2);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your another " + DESTROYER + ", 2 squares:");
        Ship destroyerC = initShip(playerField, DESTROYER, 2);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your " + SUBMARINE + ", 1 square:");
        Ship submarineA = initShip(playerField, SUBMARINE, 1);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your another " + SUBMARINE + ", 1 square:");
        Ship submarineB = initShip(playerField, SUBMARINE, 1);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your another " + SUBMARINE + ", 1 square:");
        Ship submarineC = initShip(playerField, SUBMARINE, 1);
        GAME.showField(playerField);

        System.out.println("Enter coordinates for your another " + SUBMARINE + ", 1 square:");
        Ship submarineD = initShip(playerField, SUBMARINE, 1);
        GAME.showField(playerField);

        if (FieldValidator.fieldValidator(convertToIntField(playerField))) {
            player.setFleet(new ArrayList<>(Arrays.asList(
                    battleship,
                    cruiserA,
                    cruiserB,
                    destroyerA,
                    destroyerB,
                    destroyerC,
                    submarineA,
                    submarineB,
                    submarineC,
                    submarineD)));
            return true;
        }

        clearField(playerField);
        return false;
    }

    private void clearField(char[][] playerField) {
        for (char[] chars : playerField) {
            Arrays.fill(chars, '0');
        }
    }

    private int[][] convertToIntField(char[][] playerField) {
        int[][] result = new int[10][10];

        for (int i = 0; i < playerField.length; i++) {
            for (int j = 0; j < playerField[i].length; j++) {
                if (playerField[i][j] == '1') {
                    result[i][j] = 1;
                } else {
                    result[i][j] = 0;
                }
            }
        }

        return result;
    }

    private void initRandomField(Player player) {

        char[][] playerField = player.getField();

        String battleshipRandom = RANDOM.nextBoolean() ? "1A 1B 1C 1D" : "1G 1H 1I 1J";
        String cruiserARandom = RANDOM.nextBoolean() ? "10A 10B 10C" : "10H 10I 10J";
        String cruiserBRandom = RANDOM.nextBoolean() ? "3C 4C 5C" : "3H 4H 5H";
        String destroyerARandom = RANDOM.nextBoolean() ? "3A 4A" : "3J 4J";
        String destroyerBRandom = RANDOM.nextBoolean() ? "7A 8A" : "7J 8J";
        String destroyerCRandom = RANDOM.nextBoolean() ? "7C 8C" : "7H 8H";
        String submarineARandom = RANDOM.nextBoolean() ? "3E" : "3F";
        String submarineBRandom = RANDOM.nextBoolean() ? "6E" : "6F";
        String submarineCRandom = RANDOM.nextBoolean() ? "8E" : "8F";
        String submarineDRandom = RANDOM.nextBoolean() ? "10E" : "10F";

        Ship battleship = new Ship(BATTLESHIP, 4, List.of(battleshipRandom.split(" ")));
        Ship cruiserA = new Ship(CRUISER, 3, List.of(cruiserARandom.split(" ")));
        Ship cruiserB = new Ship(CRUISER, 3, List.of(cruiserBRandom.split(" ")));
        Ship destroyerA = new Ship(DESTROYER, 2, List.of(destroyerARandom.split(" ")));
        Ship destroyerB = new Ship(DESTROYER, 2, List.of(destroyerBRandom.split(" ")));
        Ship destroyerC = new Ship(DESTROYER, 2, List.of(destroyerCRandom.split(" ")));
        Ship submarineA = new Ship(SUBMARINE, 1, List.of(submarineARandom.split(" ")));
        Ship submarineB = new Ship(SUBMARINE, 1, List.of(submarineBRandom.split(" ")));
        Ship submarineC = new Ship(SUBMARINE, 1, List.of(submarineCRandom.split(" ")));
        Ship submarineD = new Ship(SUBMARINE, 1, List.of(submarineDRandom.split(" ")));

        GAME.addShip(playerField, battleship);
        GAME.addShip(playerField, cruiserA);
        GAME.addShip(playerField, cruiserB);
        GAME.addShip(playerField, destroyerA);
        GAME.addShip(playerField, destroyerB);
        GAME.addShip(playerField, destroyerC);
        GAME.addShip(playerField, submarineA);
        GAME.addShip(playerField, submarineB);
        GAME.addShip(playerField, submarineC);
        GAME.addShip(playerField, submarineD);

        player.setFleet(new ArrayList<>(Arrays.asList(
                battleship,
                cruiserA,
                cruiserB,
                destroyerA,
                destroyerB,
                destroyerC,
                submarineA,
                submarineB,
                submarineC,
                submarineD)));
    }

    private void showField(char[][] playerField) {

        System.out.println(ABCDEFGHIJ);

        for (int i = 0; i < playerField.length; i++) {
            if (i == playerField.length - 1) {
                System.out.print((i + 1) + " ");
            } else {
                System.out.print((i + 1) + "  ");
            }
            for (int j = 0; j < playerField[i].length; j++) {
                if (j == playerField[i].length - 1) {
                    System.out.print(playerField[i][j]);
                } else {
                    System.out.print(playerField[i][j] + "  ");
                }
            }

            System.out.println();
        }

        System.out.println();

    }

    private void showField(char[][] playerField, char[][] gameProcessField) {

        System.out.println(ABCDEFGHIJ + "      " + ABCDEFGHIJ);

        for (int i = 0; i < playerField.length; i++) {
            if (i == playerField.length - 1) {
                System.out.print((i + 1) + " ");
            } else {
                System.out.print((i + 1) + "  ");
            }
            for (int j = 0; j < playerField[i].length; j++) {
                if (j == playerField[i].length - 1) {
                    System.out.print(playerField[i][j] + "      ");
                } else {
                    System.out.print(playerField[i][j] + "  ");
                }
            }

            if (i == playerField.length - 1) {
                System.out.print((i + 1) + " ");
            } else {
                System.out.print((i + 1) + "  ");
            }

            for (int j = 0; j < playerField[i].length; j++) {
                if (j == playerField[i].length - 1) {
                    System.out.print(gameProcessField[i][j] + " ");
                } else {
                    System.out.print(gameProcessField[i][j] + "  ");
                }
            }
            System.out.println();
        }

        System.out.println();
    }

    private void addShip(char[][] field, Ship ship) {
        for (int i = 0; i < ship.getPlace().size(); i++) {
            field[parseDigit(ship.getPlace().get(i))][parseLetter(ship.getPlace().get(i))] = '1';
        }
    }

    public int parseDigit(String input) {
        return Integer.parseInt(input
                .substring(0, input.length() - 1)) - 1;
    }

    public int parseLetter(String input) {
        return input.toUpperCase().charAt(input.length() - 1) - 65;
    }
}


