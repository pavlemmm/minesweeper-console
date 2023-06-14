public class Field {
    public boolean isOpened = false;
    public boolean isFlagged = false;
    public boolean isBomb = false;
    public int num = 0;

    public String returnSymbol() {
        if(isFlagged) {
            return "F";
        } else if(!isOpened) {
            return "/";
        } else if (num > 0) {
            return Integer.toString(num);
        } else {
            return " ";
        }
    }

    public String bombOrNotSymbol() {
        if(isBomb) {
            return "*";
        } else {
            return "/";
        }
    }
}
