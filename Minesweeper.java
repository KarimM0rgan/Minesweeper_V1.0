import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    /** multidimensional array displaying the hidden tiles. **/
    public int[][] fieldVisible = new int[10][10];

    /** multidimensional array containing mines and clues. **/
    public int[][] fieldHidden = new int[10][10];

    /** main function that creates an object of minesweeper class and runs the game. **/
    public static void main(String[] args) {
        MineSweeper M = new MineSweeper();
        M.startGame();
    }

    /** method that starts the game, takes no variables, and has the winning scenario implemented. It also introduces the game rules.**/
    public void startGame() {
        StdOut.println("\n\n================Welcome to Minesweeper ! ================\n");
        setupField(1);

        boolean flag = true;
        while (flag) { // to be implemented
            displayVisible(); // to be implemented
            flag = playMove();
            if (checkWin()) {
                displayHidden();
                StdOut.println("\n================You WON!!!================");
                break;
            }
        }
    }

    /** method that sets up the mines in the play-field.**/
    public void setupField(int diff) {
        int var=0;
        while(var!=10) { // Currently set to ten bombs but will be custom in V 2.0.
            Random random = new Random();
            int i = random.nextInt(10);
            int j = random.nextInt(10);
            //StdOut.println("i: " + i + " j: " + j);
            fieldHidden[i][j] = 100;
            var++;
        }
        buildHidden();
    }

    /** method that builds the hidden matrix that sets up the tiles with proximity mines clues. **/
    // logic: each cell will be chosen and count the number of bombs present in all of its neighboring cells.
    // This value will be saved in the hidden matrix cell.
    public void buildHidden() {
        for(int i=0; i<10; i++)
        {
            for(int j=0; j<10; j++)
            {
                int cnt=0;
                if(fieldHidden[i][j]!=100)
                {

                    if(i!=0)
                    {
                        if(fieldHidden[i-1][j]==100) cnt++;
                        if(j!=0)
                        {
                            if(fieldHidden[i-1][j-1]==100) cnt++;
                        }

                    }
                    if(i!=9)
                    {
                        if(fieldHidden[i+1][j]==100) cnt++;
                        if(j!=9)
                        {
                            if(fieldHidden[i+1][j+1]==100) cnt++;
                        }
                    }
                    if(j!=0)
                    {
                        if(fieldHidden[i][j-1]==100) cnt++;
                        if(i!=9)
                        {
                            if(fieldHidden[i+1][j-1]==100) cnt++;
                        }
                    }
                    if(j!=9)
                    {
                        if(fieldHidden[i][j+1]==100) cnt++;
                        if(i!=0)
                        {
                            if(fieldHidden[i-1][j+1]==100) cnt++;
                        }
                    }

                    fieldHidden[i][j] = cnt;
                }
            }
        }

    }

    /** method that shows the current progress of the player by uncovering the tiles they pressed.**/
    public void displayVisible()
    {
        StdOut.print("\t ");
        for(int i=0; i<10; i++)
        {
            StdOut.print(" " + i + "  ");
        }
        StdOut.print("\n");
        for(int i=0; i<10; i++)
        {
            StdOut.print(i + "\t| ");
            for(int j=0; j<10; j++)
            {
                if(fieldVisible[i][j]==0)
                {
                    StdOut.print("?");
                }
                else if(fieldVisible[i][j]==50)
                {
                    StdOut.print(" ");
                }
                else
                {
                    StdOut.print(fieldVisible[i][j]);
                }
                StdOut.print(" | ");
            }
            StdOut.print("\n");
        }
    }

    /** method that actually contains the game. Player enters the numbers of row and column, and either the tile is uncovered or the mine explodes.**/
    public boolean playMove() {
        Scanner sc= new Scanner(System.in);
        StdOut.print("\nEnter Row Number: ");
        int i= sc.nextInt();
        StdOut.print("Enter Column Number: ");
        int j= sc.nextInt();

        if(i<0 || i>9 || j<0 || j>9 || fieldVisible[i][j]!=0)
        {
            StdOut.print("\nIncorrect Input!!");
            return true;
        }

        if(fieldHidden[i][j]==100)
        {
            displayHidden();
            StdOut.print("Oops! You stepped on a mine!\n============GAME OVER============");
            return false;
        }
        else if(fieldHidden[i][j]==0)
        {
            fixVisible(i, j);
        }
        else
        {
            fixNeighbours(i, j);
        }

        return true;
    }

    /** both `fixVisible` and `fixNeighbors` will assist as showing the consequences of revealing a tile both. **/
    public void fixVisible(int i, int j) {
        fieldVisible[i][j] = 50;
        if(i!=0)
        {
            fieldVisible[i-1][j] = fieldHidden[i-1][j];
            if(fieldVisible[i-1][j]==0) fieldVisible[i-1][j] = 50;
            if(j!=0)
            {
                fieldVisible[i-1][j-1] = fieldHidden[i-1][j-1];
                if(fieldVisible[i-1][j-1]==0) fieldVisible[i-1][j-1]=50;

            }
        }
        if(i!=9)
        {
            fieldVisible[i+1][j]=fieldHidden[i+1][j];
            if(fieldVisible[i+1][j]==0) fieldVisible[i+1][j]=50;
            if(j!=9)
            {
                fieldVisible[i+1][j+1]= fieldHidden[i+1][j+1];
                if(fieldVisible[i+1][j+1]==0) fieldVisible[i+1][j+1] = 50;
            }
        }
        if(j!=0)
        {
            fieldVisible[i][j-1]=fieldHidden[i][j-1];
            if(fieldVisible[i][j-1]==0) fieldVisible[i][j-1] = 50;
            if(i!=9)
            {
                fieldVisible[i+1][j-1]=fieldHidden[i+1][j-1];
                if(fieldVisible[i+1][j-1]==0) fieldVisible[i+1][j-1] = 50;
            }
        }
        if(j!=9)
        {
            fieldVisible[i][j+1]=fieldHidden[i][j+1];
            if(fieldVisible[i][j+1]==0) fieldVisible[i][j+1] = 50;
            if(i!=0)
            {
                fieldVisible[i-1][j+1]=fieldHidden[i-1][j+1];
                if(fieldVisible[i-1][j+1]==0) fieldVisible[i-1][j+1] = 50;
            }
        }
    }

    public void fixNeighbours(int i, int j)
    {
        Random random = new Random();
        int x = random.nextInt()%4;

        fieldVisible[i][j] = fieldHidden[i][j];

        if(x==0)
        {
            if(i!=0)
            {
                if(fieldHidden[i-1][j]!=100)
                {
                    fieldVisible[i-1][j] = fieldHidden[i-1][j];
                    if(fieldVisible[i-1][j]==0)  fieldVisible[i-1][j] = 50;
                }
            }
            if(j!=0)
            {
                if(fieldHidden[i][j-1]!=100)
                {
                    fieldVisible[i][j-1] = fieldHidden[i][j-1];
                    if(fieldVisible[i][j-1]==0)  fieldVisible[i][j-1] = 50;
                }

            }
            if(i!=0 && j!=0)
            {
                if(fieldHidden[i-1][j-1]!=100)
                {
                    fieldVisible[i-1][j-1] = fieldHidden[i-1][j-1];
                    if(fieldVisible[i-1][j-1]==0)  fieldVisible[i-1][j-1] = 50;
                }

            }
        }
        else if(x==1)
        {
            if(i!=0)
            {
                if(fieldHidden[i-1][j]!=100)
                {
                    fieldVisible[i-1][j] = fieldHidden[i-1][j];
                    if(fieldVisible[i-1][j]==0)  fieldVisible[i-1][j] = 50;
                }
            }
            if(j!=9)
            {
                if(fieldHidden[i][j+1]!=100)
                {
                    fieldVisible[i][j+1] = fieldHidden[i][j+1];
                    if(fieldVisible[i][j+1]==0)  fieldVisible[i][j+1] = 50;
                }

            }
            if(i!=0 && j!=9)
            {
                if(fieldHidden[i-1][j+1]!=100)
                {
                    fieldVisible[i-1][j+1] = fieldHidden[i-1][j+1];
                    if(fieldVisible[i-1][j+1]==0)  fieldVisible[i-1][j+1] = 50;
                }
            }
        }
        else if(x==2)
        {
            if(i!=9)
            {
                if(fieldHidden[i+1][j]!=100)
                {
                    fieldVisible[i+1][j] = fieldHidden[i+1][j];
                    if(fieldVisible[i+1][j]==0)  fieldVisible[i+1][j] = 50;
                }
            }
            if(j!=9)
            {
                if(fieldHidden[i][j+1]!=100)
                {
                    fieldVisible[i][j+1] = fieldHidden[i][j+1];
                    if(fieldVisible[i][j+1]==0)  fieldVisible[i][j+1] = 50;
                }

            }
            if(i!=9 && j!=9)
            {
                if(fieldHidden[i+1][j+1]!=100)
                {
                    fieldVisible[i+1][j+1] = fieldHidden[i+1][j+1];
                    if(fieldVisible[i+1][j+1]==0)  fieldVisible[i+1][j+1] = 50;
                }
            }
        }
        else
        {
            if(i!=9)
            {
                if(fieldHidden[i+1][j]!=100)
                {
                    fieldVisible[i+1][j] = fieldHidden[i+1][j];
                    if(fieldVisible[i+1][j]==0)  fieldVisible[i+1][j] = 50;
                }
            }
            if(j!=0)
            {
                if(fieldHidden[i][j-1]!=100)
                {
                    fieldVisible[i][j-1] = fieldHidden[i][j-1];
                    if(fieldVisible[i][j-1]==0)  fieldVisible[i][j-1] = 50;
                }

            }
            if(i!=9 && j!=0)
            {
                if(fieldHidden[i+1][j-1]!=100)
                {
                    fieldVisible[i+1][j-1] = fieldHidden[i+1][j-1];
                    if(fieldVisible[i+1][j-1]==0)  fieldVisible[i+1][j-1] = 50;
                }
            }
        }
    }

    /** method will be used to check if the player has avoided all the mines on the play-field.**/
     public boolean checkWin() {
        for(int i=0; i<10; i++)
        {
            for(int j=0; j<10; j++)
            {
                if(fieldVisible[i][j]==0)
                {
                    if(fieldHidden[i][j]!=100)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /** this method will be called once the game is over, showing the remaining hidden tiles.
     * In case of winning, mines are revealed.
     * In case of losing, all remaining tiles are revealed.
     */
    public void displayHidden()
    {
        System.out.print("\t ");
        for(int i=0; i<10; i++)
        {
            System.out.print(" " + i + "  ");
        }
        System.out.print("\n");
        for(int i=0; i<10; i++)
        {
            System.out.print(i + "\t| ");
            for(int j=0; j<10; j++)
            {
                if(fieldHidden[i][j]==0)
                {
                    System.out.print(" ");
                }
                else if(fieldHidden[i][j]==100)
                {
                    System.out.print("X");
                }
                else
                {
                    System.out.print(fieldHidden[i][j]);
                }
                System.out.print(" | ");
            }
            System.out.print("\n");
        }
    }
}