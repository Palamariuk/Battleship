package battleship;

import java.util.Scanner;

public class Main {

    static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {

        BattleshipGame game = new BattleshipGame();
        game.init();
        game.start();

    }
}
