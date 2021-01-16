package battleship;

import java.util.Scanner;

public class BattleshipField {

    static final Scanner scanner = new Scanner(System.in);

    private static final int sz = 10;

    private BattleshipFieldCell[][] field = new BattleshipFieldCell[sz][sz];

    BattleshipField(){
        for(int i = 0; i < sz; i++){
            for(int j = 0; j < sz; j++){
                field[i][j] = BattleshipFieldCell.UNKNOWN;
            }
        }
    }

    public boolean hasAliveShips(){
        for(int i = 0; i < sz; i++){
            for(int j = 0; j < sz; j++){
                if(field[i][j] == BattleshipFieldCell.SHIP){
                    return true;
                }
            }
        }
        return false;
    }

    private int getCellRow(String cell){
        return cell.charAt(0) - 'A';
    }

    private int getCellColumn(String cell){
        return Integer.parseInt(cell.substring(1)) - 1;
    }

    private int calculateRangeSize(String begin, String end){

        int beginRow = getCellRow(begin);
        int endRow = getCellRow(end);
        int beginCol = getCellColumn(begin);
        int endCol = getCellColumn(end);

        if(beginRow != endRow && beginCol != endCol){
            return -1;
        } else {
            if(beginRow == endRow){
                return Math.abs(endCol - beginCol) + 1;
            } else {
                return Math.abs((int)endRow - (int)beginRow) + 1;
            }
        }

    }

    private void place(String shipName, int length, String begin, String end) throws Exception {

        int rangeSize = calculateRangeSize(begin, end);
        if(rangeSize == -1) {
            throw new Exception("Error! Wrong ship location! Try again:");
        } else if(rangeSize != length){
            throw new Exception("Error! Wrong length of the " + shipName + "! Try again:");
        }

        int beginRow = getCellRow(begin);
        int endRow = getCellRow(end);
        int beginCol = getCellColumn(begin);
        int endCol = getCellColumn(end);

        int beginCellCol = Math.min(beginCol, endCol);
        int endCellCol = Math.max(beginCol, endCol);
        int beginCellRow = Math.min(beginRow, endRow);
        int endCellRow = Math.max(beginRow, endRow);

        for(int i = beginCellRow - 1; i <= endCellRow + 1; i++){
            for(int j = beginCellCol - 1; j <= endCellCol + 1; j++){
                if(i < 0 || j < 0 || i > sz - 1 || j > sz - 1) continue;
                if(field[i][j] == BattleshipFieldCell.SHIP){
                    throw new Exception("Error! You placed it too close to another one. Try again:");
                }
            }
        }

        for(int i = beginCellRow; i <= endCellRow; i++){
            for(int j = beginCellCol; j <= endCellCol; j++){
                field[i][j] = BattleshipFieldCell.SHIP;
            }
        }
    }

    public void placeShip(String shipName, int length){
        System.out.println("Enter the coordinates of the " + shipName + " (" + length + " cells):");
        boolean allGood = false;
        while (!allGood){
            String[] range = scanner.nextLine().split(" ");
            try{
                place(shipName, length, range[0], range[1]);
                allGood = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int shot(BattleshipField field, String cell) throws Exception{
        try{
            int shotRow = getCellRow(cell);
            int shotCol = getCellColumn(cell);
            switch (field.field[shotRow][shotCol]){
                case UNKNOWN: {
                    field.field[shotRow][shotCol] = BattleshipFieldCell.MISS;
                    return 0;
                }
                case SHIP: {
                    field.field[shotRow][shotCol] = BattleshipFieldCell.HIT;
                    for(int i = -1; i <= 1; i++){
                        for(int j = -1; j <= 1; j++){
                            if(Math.abs(i-j) == 1){
                                try {
                                    if (field.field[shotRow + i][shotCol + j] == BattleshipFieldCell.SHIP){
                                        return 1;
                                    }
                                } catch (Exception e){
                                    continue;
                                };
                            }
                        }
                    }
                    return field.hasAliveShips() ? 2 : 3;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e){
            throw new Exception("Error! You entered the wrong coordinates! Try again:");
        }
        return 0;
    }

    public int takeShot(BattleshipField field){
        while (true){
            String cell = scanner.nextLine();
            try {
                int result = shot(field, cell);
                switch (result){
                    case 0: {
                        System.out.println("You missed!");
                        break;
                    }
                    case 1: {
                        System.out.println("You hit a ship!");
                        break;
                    }
                    case 2: {
                        System.out.println("You sank a ship! Specify a new target:");
                        break;
                    }
                    case 3: {
                        System.out.println("You sank the last ship. You won. Congratulations!");
                        break;
                    }
                }
                return result;
            } catch (Exception error){
                System.out.println(error.getMessage());
            }
        }
    }

    public void print(boolean hideShips){
        System.out.print(" ");
        for(int i = 0; i < sz; i++){
            System.out.print(" " + (i + 1));
        }
        System.out.println();
        for(int i = 0; i < sz; i++){
            System.out.print((char)((int)'A' + i));
            for(int j = 0; j < sz; j++){
                if(hideShips){
                    System.out.print(" " + (
                            field[i][j] == BattleshipFieldCell.SHIP ? BattleshipFieldCell.UNKNOWN.symbol : field[i][j].symbol));
                } else {
                    System.out.print(" " + field[i][j].symbol);
                }
            }
            System.out.println();
        }
    }

}
