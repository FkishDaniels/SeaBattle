import java.util.Random;

public class Field {
    private final int FIELD_SIZE = 10;
    private String[][] playerField;
    private String [][] computerField;
    private String [][] computerFieldForPlayer;
    private Coordinates coordinates;

    public Field(){
        playerField = new String[FIELD_SIZE][FIELD_SIZE];
        computerField = new String[FIELD_SIZE][FIELD_SIZE];
        computerFieldForPlayer = new String[FIELD_SIZE][FIELD_SIZE];

        for(int i = 0; i < FIELD_SIZE; i++){
            for(int j = 0; j < FIELD_SIZE; j++){
                playerField[i][j] = "□";
                computerField[i][j] = "□";
                computerFieldForPlayer[i][j] = "□";
            }
        }

        coordinates = new Coordinates();
    }

    private void printField(String[][] field) {
        System.out.print("  ");
        for (char letter = 'A'; letter < 'A' + FIELD_SIZE; letter++) {
            System.out.print(letter + " ");
        }
        System.out.println();

        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (String cell : field[i]) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printPlayerFiled(){
        System.out.println("Player field");
        printField(playerField);
    }

    public void printComputerField(){
        System.out.println("Computer field");
        printField(computerFieldForPlayer);
    }

    public void fillField(int[] coordinates,String[][] field){
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        if(coordinates[0] == coordinates[2]) {
            min = Math.min(coordinates[1], coordinates[3]);
            max = Math.max(coordinates[1], coordinates[3]);
            for (int i = min; i <= max; i++) {
                field[i][coordinates[0]] = "O";
            }
        }else if (coordinates[1] == coordinates[3]) {
            min = Math.min(coordinates[0], coordinates[2]);
            max = Math.max(coordinates[0], coordinates[2]);
            for (int i = min; i<= max;i++) {
                field[coordinates[3]][i] = "O";
            }
        }
        printPlayerFiled();
    }

    public void makePlayerShot(){
        System.out.println("Write coordinates to make shot");
        int [] shipCoordinates = coordinates.getCoordinates(coordinates.readString());
        if(coordinates.isCorrectCoord(shipCoordinates)) {
            int x = shipCoordinates[1];
            int y = shipCoordinates[0];
            if (computerField[x][y] == "□") {
                computerField[x][y] = "■";
                computerFieldForPlayer[x][y] = "■";

                System.out.println("The shot missed the ship");
            } else if (computerField[x][y] == "O") {
                computerField[x][y] = "X";
                computerFieldForPlayer[x][y] = "X";

                System.out.println("The shot hit the ship");
            } else if (computerField[x][y] == "X" || computerField[x][y] == "■") {
                System.out.println("You've already made this move");
                makePlayerShot();
            }
        }else{
            System.out.println("Wrong coordinates");
            makePlayerShot();
        }

    }
    public void makeComputerShot(){
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);

        if(playerField[x][y] == "□"){
            System.out.println("Computer missed the hit");
            playerField[x][y] = "■";
        } else if (playerField[x][y] == "O") {
            System.out.println("Computer hit the ship");
            playerField[x][y] = "X";
        }else if (playerField[x][y] == "X" || playerField[x][y] == "■"){
            makeComputerShot();
        }
    }
    public boolean isEmptyAround(int[] coordinates, String[][] gameField) {
        // Определяем минимальные и максимальные координаты (корабль может быть горизонтальным или вертикальным)
        int rowStart = Math.min(coordinates[1], coordinates[3]);
        int rowEnd = Math.max(coordinates[1], coordinates[3]);
        int colStart = Math.min(coordinates[0], coordinates[2]);
        int colEnd = Math.max(coordinates[0], coordinates[2]);

        // Проверяем область вокруг корабля (включая сам корабль)
        int iStart = Math.max(0, rowStart - 1);
        int iEnd = Math.min(gameField.length - 1, rowEnd + 1);
        int jStart = Math.max(0, colStart - 1);
        int jEnd = Math.min(gameField[0].length - 1, colEnd + 1);

        // Проходим по области вокруг корабля и проверяем наличие других кораблей
        for (int i = iStart; i <= iEnd; i++) {
            for (int j = jStart; j <= jEnd; j++) {
                if (gameField[i][j].equals("O")) {
                    System.out.println("Ошибка! Корабль рядом! Попробуй снова:");
                    return false;
                }
            }
        }

        return true;
    }


    public boolean haveShips(String[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j].equals("O")) {
                    return false;
                }
            }
        }
        return true;
    }

    public String[][] getPlayerField() {
        return playerField;
    }

    public String[][] getComputerField() {
        return computerField;
    }

}
