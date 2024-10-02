import java.util.*;

public class Game {
    private Coordinates coordinates;
    private Field field;
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        field = new Field();
        coordinates = new Coordinates();
    }


    public void fillPlayerField() {
        int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        for (int size : shipSizes) {
            boolean correct = false;
            while (!correct) {
                field.printPlayerFiled();
                System.out.println("Place a ship of size " + size + ". Example: A1A4");
                String line = coordinates.readString();
                int[] shipCoordinates = coordinates.getCoordinates(line);

                if (coordinates.isCorrectCoord(shipCoordinates) && coordinates.isCorrectLength(shipCoordinates, size)) {
                    if (field.isEmptyAround(shipCoordinates, field.getPlayerField())) {
                        field.fillField(shipCoordinates, field.getPlayerField());
                        correct = true;
                    }
                } else {
                    System.out.println("Invalid ship placement, try again.");
                }
            }
        }
    }
    public void fillComputerField() {
        Random random = new Random();
        int[] shipSizes = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        for (int size : shipSizes) {
            boolean correct = false;
            while (!correct) {
                int x1 = random.nextInt(10);
                int y1 = random.nextInt(10);
                int x2 = x1, y2 = y1;

                if (random.nextBoolean()) { // Горизонтально
                    y2 = y1 + size - 1;
                    if (y2 >= 10) continue;
                } else { // Вертикально
                    x2 = x1 + size - 1;

                    if (x2 >= 10) continue;
                }

                int[] shipCoordinates = {x1, y1, x2, y2};


                if (coordinates.isCorrectCoord(shipCoordinates) && coordinates.isCorrectLength(shipCoordinates, size)) {
                    if (field.isEmptyAround(shipCoordinates, field.getComputerField())) {
                        field.fillField(shipCoordinates, field.getComputerField());
                        correct = true;
                    }
                }
            }
        }
    }
    public void startGame() {
        try {
            fillPlayerField();
            fillComputerField();
            boolean gameOn = true;

            while (gameOn) {
                field.printPlayerFiled();
                field.printComputerField();
                System.out.println("Your turn to shoot.");
                field.makePlayerShot();
                Thread.sleep(2000);
                System.out.println("Computer's turn.");
                field.makeComputerShot();

                if (isGameOver()) {
                    gameOn = false;
                }
            }
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isGameOver() {
        if (field.haveShips(field.getPlayerField())) {
            System.out.println("Game over! Computer wins.");
            return true;
        } else if (field.haveShips(field.getComputerField())) {
            System.out.println("Game over! You win.");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
