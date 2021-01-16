package battleship;

public enum BattleshipFieldCell {
    UNKNOWN(0, '~'),
    SHIP(1, 'O'),
    MISS(2, 'M'),
    HIT(3, 'X');


    int index;
    char symbol;

    BattleshipFieldCell(int index, char symbol) {
        this.index = index;
        this.symbol = symbol;
    }


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }
}
