package battleship;

import java.io.IOException;

public class BattleshipGame {

    BattleshipField player1;
    BattleshipField player2;


    public void init() {
        player1 = new BattleshipField();
        player2 = new BattleshipField();
    }

    public void placeShips(BattleshipField user) {
        user.placeShip("Aircraft Carrier", 5);
        user.print(false);
        user.placeShip("Battleship", 4);
        user.print(false);
        user.placeShip("Submarine", 3);
        user.print(false);
        user.placeShip("Cruiser", 3);
        user.print(false);
        user.placeShip("Destroyer", 2);
        user.print(false);
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        BattleshipField.scanner.nextLine();
        try {
            Runtime.getRuntime().exec("clear");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Player 1, place your ships on the game field");
        player1.print(false);
        placeShips(player1);
        promptEnterKey();

        System.out.println("Player 2, place your ships on the game field");
        player2.print(false);
        placeShips(player2);
        promptEnterKey();

        boolean isGameOver = false;
        while (true) {

            player2.print(true);
            System.out.println("---------------------");
            player1.print(false);
            System.out.println("Player 1, it's your turn:");
            isGameOver = player1.takeShot(player2) == 3;
            if(isGameOver){
                break;
            }

            promptEnterKey();

            player1.print(true);
            System.out.println("---------------------");
            player2.print(false);
            System.out.println("Player 2, it's your turn:");
            isGameOver = player2.takeShot(player1) == 3;
            if(isGameOver){
                break;
            }

            promptEnterKey();
        }

    }

}
