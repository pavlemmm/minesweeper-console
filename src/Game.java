import java.io.IOException;
import java.util.Scanner;

public class Game {
    private Board board;

    public static int rows;
    public static int columns;
    public static int bombsNum;

    private final Scanner in = new Scanner(System.in);

    private int numOfGuesses = 0;


    public void Start() {
        System.out.print("--- Minesweeper ---\n\n");
        System.out.print("Unesi broj redova: ");
        Game.columns = in.nextInt();
        System.out.print("Unesi broj kolona: ");
        Game.rows = in.nextInt();
        System.out.print("Unesi broj bombi: ");
        Game.bombsNum = in.nextInt();

        this.board = new Board();

        System.out.println("\nUnesi red, kolonu i naredbu F (postavi zastavicu) ili O (otvori polje)\n");
        Input();
    }

    private void Restart() {
//        Scanner in = new Scanner(new FileReader("highscore.txt"));
//        int txtHighscore = in.nextInt();

        // Check if highscore
        numOfGuesses = 0;

        Start();
    }

    private void Input() {
//        try {
//            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // Ocisti konzolu
//        } catch (Exception ignored) {}

        board.DrawBoard();

        System.out.print("> ");
        String input = in.next();

        int x, y;
        char action;
        try {
            x = (int)input.charAt(0) - 97;
            y = Character.getNumericValue(input.charAt(1)) - 1;
            action = Character.toLowerCase(input.charAt(2));
            if(x<0 || x>=Game.columns || y<0 || y>=Game.rows || (action != 'f' && action != 'o')){
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Pogrešan unos!");
            Input();
            return;
        }

        numOfGuesses++;

        if(action == 'f') {
            board.FlagField(x, y);
        } else {
            int res = board.OpenField(x, y);
            if(res == 1) {
                Win();
                return;
            }
            else if(res == -1) {
                GameOver();
                return;
            }
        }

        Input();
    }

    private void GameOver(){
        board.DrawBoardFinal();
        System.out.print("Izgubio si. Započinjem novu igru...\n\n\n");
        Restart();
    }

    private void Win(){
        board.DrawBoardFinal();
        System.out.printf("Pobedio si iz %d pokušaja. Započinjem novu igru...\n\n\n", numOfGuesses);
        Restart();
    }
}
