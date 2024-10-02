import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinates {


    private String regex = "([A-J])(\\d+)([A-J])(\\d+)";


    public String readString(){
        Scanner scanner = new Scanner(System.in);

        return scanner.nextLine().toUpperCase().replace(" ","");
    }

    public int[] getCoordinates(String line){
        int firstLetter = 0;
        int secondLetter = 0;

        int firstNumber = 0;
        int secondNumber = 0;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher =pattern.matcher(line);

        if(matcher.matches()){
            firstLetter = matcher.group(1).charAt(0)-'A'+1;//A=1, B=2, C=3
            firstNumber = Integer.parseInt(matcher.group(2));

            secondLetter = matcher.group(3).charAt(0) - 'A' + 1;
            secondNumber = Integer.parseInt(matcher.group(4));

            if(firstNumber <= 0 || firstNumber > 10) {
                firstLetter = 80;
            }
            if(secondNumber <= 0 || secondNumber > 10) {
                firstLetter = 80;
            }
        }else{
            firstLetter = 80;
            firstNumber = 80;
        }

        return new int[]{firstLetter-1,firstNumber-1,secondLetter-1,secondNumber-1};
    }

    public boolean isCorrectCoord(int[] coordinates) {
        // Проверка, что координаты не равны 80 и находятся в допустимых диапазонах
        if (coordinates[0] == 79 || coordinates[1] == 79 || coordinates[2] == 79 || coordinates[3] == 79) {
            return false;
        }

        return coordinates[0] == coordinates[2] || coordinates[1] == coordinates[3];
    }

    public boolean isCorrectLength(int[] coordinates, int length) {
        if (coordinates[0] == coordinates[2]) {
            return length == Math.abs(coordinates[3] - coordinates[1]) + 1; // Вертикальное расположение
        } else if (coordinates[1] == coordinates[3]) {
            return length == Math.abs(coordinates[2] - coordinates[0]) + 1; // Горизонтальное расположение
        }
        return false;
    }

}
