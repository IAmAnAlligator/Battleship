import java.util.*;

public class Game {

    static final Game game = new Game();
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    private final Player player = new Player();
    private final Player bot = new Player();

    public void startGame() {

        System.out.println("Enter your name:");
        player.setName(scanner.nextLine());

        System.out.println("Hello " + player.getName() + "!");
        System.out.println("Enter 1 - Play on random field");
        System.out.println("Enter 2 - Initialize field by your own");
        System.out.println("Press any button to exit");
        String choose = scanner.nextLine();

        if (choose.equals("1")) {
            game.initRandomField(player);
        } else if (choose.equals("2")) {
            game.showField(player.getField());
            game.initField(player);
        } else {
            System.exit(0);
        }

        System.out.println("You field is: ");
        showField(player.getField(), player.getGameProcessField());

        bot.setName("Bot");
        game.initRandomField(bot);

        List<String> places = player.getListOfPlaces();

        while (player.isFleetActive() && bot.isFleetActive()) {

            System.out.println(player.getName() + " attacks");
            String place = scanner.nextLine();
            player.attack(bot, place);

            String shoot = getPlaceForAttack(places);
            System.out.println(bot.getName() + " attacks " + shoot);
            bot.attack(player, shoot);
            showField(player.getField(), player.getGameProcessField());
        }

    }

    private String getPlaceForAttack(List<String> places) {

        String digits = "0123456789";
        String letters = "ABCDEFGHIJ";
        int randomDigit;
        char randomLetter;
        String result;

        int range = (int) (Math.random() * (5 - 1) + 1);

        if (range == 4) {
            int randomPlace = (int) (Math.random() * (places.size() - 1) + 0) + 1;
            result = places.get(randomPlace);
            places.remove(randomPlace);
        } else {
            randomDigit = (int) (Math.random() * (digits.length() - 1) + 0) + 1;
            randomLetter = letters.charAt(randomDigit);
            result = randomDigit + "" + randomLetter;
        }

        return result;

    }

    private void initField(Player player) {

        char[][] playerField = player.getField();

        System.out.println("Enter coordinates for your Battleship, 4 squares:");
        String battleshipPlace = scanner.nextLine();

        Ship battleship = new Ship("Battleship", 4, List.of(battleshipPlace.split(" ")));
        game.addShip(playerField, battleship);
        game.showField(playerField);

        System.out.println("Enter coordinates for your Cruiser, 3 squares:");
        String cruiserAPlace = scanner.nextLine();
        Ship cruiserA = new Ship("Cruiser", 3, List.of(cruiserAPlace.split(" ")));
        game.addShip(playerField, cruiserA);
        game.showField(playerField);

        System.out.println("Enter coordinates for your another Cruiser, 3 squares:");
        String cruiserBPlace = scanner.nextLine();
        Ship cruiserB = new Ship("Cruiser", 3, List.of(cruiserBPlace.split(" ")));
        game.addShip(playerField, cruiserB);
        game.showField(playerField);

        System.out.println("Enter coordinates for your Destroyer, 2 squares:");
        String destroyerAPlace = scanner.nextLine();
        Ship destroyerA = new Ship("Destroyer", 2, List.of(destroyerAPlace.split(" ")));
        game.addShip(playerField, destroyerA);
        game.showField(playerField);

        System.out.println("Enter coordinates for your another Destroyer, 2 squares:");
        String destroyerBPlace = scanner.nextLine();
        Ship destroyerB = new Ship("Destroyer", 2, List.of(destroyerBPlace.split(" ")));
        game.addShip(playerField, destroyerB);
        game.showField(playerField);

        System.out.println("Enter coordinates for your another Destroyer, 2 squares:");
        String destroyerCPlace = scanner.nextLine();
        Ship destroyerC = new Ship("Destroyer", 2, List.of(destroyerCPlace.split(" ")));
        game.addShip(playerField, destroyerC);
        game.showField(playerField);

        System.out.println("Enter coordinates for your Submarine, 1 square:");
        String submarineAPlace = scanner.nextLine();
        Ship submarineA = new Ship("Submarine", 1, List.of(submarineAPlace.split(" ")));
        game.addShip(playerField, submarineA);
        game.showField(playerField);

        System.out.println("Enter coordinates for your another Submarine, 1 square:");
        String submarineBPlace = scanner.nextLine();
        Ship submarineB = new Ship("Submarine", 1, List.of(submarineBPlace.split(" ")));
        game.addShip(playerField, submarineB);
        game.showField(playerField);

        System.out.println("Enter coordinates for your another Submarine, 1 square:");
        String submarineCPlace = scanner.nextLine();
        Ship submarineC = new Ship("Submarine", 1, List.of(submarineCPlace.split(" ")));
        game.addShip(playerField, submarineC);
        game.showField(playerField);

        System.out.println("Enter coordinates for your another Submarine, 1 square:");
        String submarineDPlace = scanner.nextLine();
        Ship submarineD = new Ship("Submarine", 1, List.of(submarineDPlace.split(" ")));
        game.addShip(playerField, submarineD);
        game.showField(playerField);

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

    private void initRandomField(Player player) {

        char[][] playerField = player.getField();

        String battleshipRandom = random.nextBoolean() ? "1A 1B 1C 1D" : "1G 1H 1I 1J";
        String cruiserARandom = random.nextBoolean() ? "10A 10B 10C" : "10H 10I 10J";
        String cruiserBRandom = random.nextBoolean() ? "3C 4C 5C" : "3H 4H 5H";
        String destroyerARandom = random.nextBoolean() ? "3A 4A" : "3J 4J";
        String destroyerBRandom = random.nextBoolean() ? "7A 8A" : "7J 8J";
        String destroyerCRandom = random.nextBoolean() ? "7C 8C" : "7H 8H";
        String submarineARandom = random.nextBoolean() ? "3E" : "3F";
        String submarineBRandom = random.nextBoolean() ? "6E" : "6F";
        String submarineCRandom = random.nextBoolean() ? "8E" : "8F";
        String submarineDRandom = random.nextBoolean() ? "10E" : "10F";

        Ship battleship = new Ship("Battleship", 4, List.of(battleshipRandom.split(" ")));
        Ship cruiserA = new Ship("Cruiser", 3, List.of(cruiserARandom.split(" ")));
        Ship cruiserB = new Ship("Cruiser", 3, List.of(cruiserBRandom.split(" ")));
        Ship destroyerA = new Ship("Destroyer", 2, List.of(destroyerARandom.split(" ")));
        Ship destroyerB = new Ship("Destroyer", 2, List.of(destroyerBRandom.split(" ")));
        Ship destroyerC = new Ship("Destroyer", 2, List.of(destroyerCRandom.split(" ")));
        Ship submarineA = new Ship("Submarine", 1, List.of(submarineARandom.split(" ")));
        Ship submarineB = new Ship("Submarine", 1, List.of(submarineBRandom.split(" ")));
        Ship submarineC = new Ship("Submarine", 1, List.of(submarineCRandom.split(" ")));
        Ship submarineD = new Ship("Submarine", 1, List.of(submarineDRandom.split(" ")));

        game.addShip(playerField, battleship);
        game.addShip(playerField, cruiserA);
        game.addShip(playerField, cruiserB);
        game.addShip(playerField, destroyerA);
        game.addShip(playerField, destroyerB);
        game.addShip(playerField, destroyerC);
        game.addShip(playerField, submarineA);
        game.addShip(playerField, submarineB);
        game.addShip(playerField, submarineC);
        game.addShip(playerField, submarineD);

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

        System.out.println("   A  B  C  D  E  F  G  H  I  J");

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

        System.out.println("   A  B  C  D  E  F  G  H  I  J         A  B  C  D  E  F  G  H  I  J");

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

        int digit;
        int letter;

        for (int i = 0; i < ship.getPlace().size(); i++) {
            digit = Integer.parseInt(ship.getPlace().get(i)
                    .substring(0, ship.getPlace().get(i).length() - 1)) - 1;
            letter = ship.getPlace().get(i).charAt(ship.getPlace().get(i).length() - 1) - 65;

            field[digit][letter] = '1';
        }
    }
}
