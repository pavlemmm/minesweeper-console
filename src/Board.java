public class Board {
    private final Field[][] board = new Field[Game.rows][Game.columns];
    private boolean isGenerated = false;
    private int openedFields = 0;

    public Board() {
        InitializeBoard();
    }

    public void DrawBoard() {
        // Ispisi oznake kolona
        System.out.print("\n    ");
        for(int i=0; i<Game.columns;i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.print('\n');

        System.out.print("   ");
        for(int i=0; i<Game.columns;i++) {
            System.out.print("──");
        }
        System.out.print('\n');

        for(int i = 0; i < Game.rows; i++) {
            System.out.printf("%c │", (char) (i + 97)); // Ispisi oznake redova
            for(int j = 0; j < Game.columns; j++) {
                System.out.print(" " + board[i][j].returnSymbol());
            }

            System.out.print('\n');
        }
        System.out.print('\n');
    }

    public void DrawBoardFinal() {
        // Ispisi oznake kolona
        System.out.print("\n    ");
        for(int i=0; i<Game.columns;i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.print('\n');

        System.out.print("   ");
        for(int i=0; i<Game.columns;i++) {
            System.out.print("──");
        }
        System.out.print('\n');

        for(int i = 0; i < Game.rows; i++) {
            System.out.printf("%c │", (char) (i + 97)); // Ispisi oznake redova
            for(int j = 0; j < Game.columns; j++) {
                System.out.print(" " + board[i][j].bombOrNotSymbol());
            }

            System.out.print('\n');
        }
        System.out.print('\n');
    }


    private void InitializeBoard() {
        for(int i = 0; i < Game.rows; i++) {
            for (int j = 0; j < Game.columns; j++) {
                board[i][j] = new Field();
            }
        }
    }

    public void GenerateBoard(int x, int y) {
        int bombsNum = Game.bombsNum;

        int fieldsNum = Game.columns * Game.rows - 1;

        int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}}; // Izbaci polja oko pocetne tacke iz racunice
        for(int[] d: dir){
            if(x+d[0] >= 0 && x+d[0] < Game.columns && y+d[1] >= 0 && y+d[1] < Game.rows) {
                fieldsNum--;
            }
        }



        for(int i = 0; i < Game.rows; i++) {
            for(int j = 0; j < Game.columns; j++) {
                if ((i >= x-1 && i <= x+1) && (j >= y-1 && j <= y+1)) continue;

                double probability = (double) bombsNum / fieldsNum;

                if (probability == 0) break;

                if(Math.random() < probability) {
                    board[i][j].isBomb = true;
                    bombsNum--;
                    CheckNeighbours(i,j);
                }
                fieldsNum--;

//                System.out.println("\nProbability: " + probability);
//                System.out.println("FieldsNum: " + fieldsNum);
//                System.out.println("bombsNum: " + bombsNum + "\n");
            }
        }
    }

    private void CheckNeighbours(int x, int y){
        int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};
        for(int[] d: dir){
            try{
                board[x + d[0]][y + d[1]].num++;
            } catch (IndexOutOfBoundsException ignored) {}
        }
//        if(x > 0) {
//            board[x-1][y].num++;
//            if(y > 0) {
//                board[x-1][y-1].num++;
//            }
//        }
//        if(y>0) {
//            board[x][y-1].num++;
//            if(x<Game.columns-1){
//                board[x+1][y-1].num++;
//            }
//        }
//        if(x<Game.columns-1) {
//            board[x+1][y].num++;
//            if(y<Game.rows-1){
//                board[x+1][y+1].num++;
//            }
//        }
//        if(y<Game.rows-1){
//            board[x][y+1].num++;
//            if(x>0){
//                board[x-1][y+1].num++;
//            }
//        }
    }

    public int OpenField(int x, int y){

        if(!isGenerated) {
            GenerateBoard(x, y);
            isGenerated = true;
        }

        if(board[x][y].isBomb) {
            return -1;
        }

        OpenNeighbours(x, y);

        if(openedFields == Game.columns * Game.rows - Game.bombsNum){
            return 1;
        }


        return 0;
    }

    private void OpenNeighbours(int x, int y) {
        if(x<0 || x>= Game.columns || y<0 || y>= Game.rows || board[x][y].isOpened || board[x][y].isBomb)
            return;

        board[x][y].isOpened = true;
        openedFields += 1;

        if(board[x][y].num > 0)
            return;

        int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {-1, -1}, {-1, 1}, {1, -1}};
        for(int[] d: dir) {
            OpenNeighbours(x+d[0], y+d[1]);
        }

    }

    public void FlagField(int x, int y) {
        if(!board[x][y].isOpened) {
            board[x][y].isFlagged = !board[x][y].isFlagged;
        }
    }
}
