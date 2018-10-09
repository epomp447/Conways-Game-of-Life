import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.FileInputStream;

//===========================================================================
/**
 * Conway's Game of Life. (See
 * <a href="https://en.wikipedia.org/wiki/Conway's_Game_of_Life">Conway's Game
 * of Life</a> on wikipedia;
 * <a href="https://www.youtube.com/watch?v=R9Plq-D1gEk">this</a> is interesting
 * too on youtube.)
 *
 * <pre>
 * v1
 *     {@link #initializeBoard()}
 *     {@link #toString()}
 * v2
 *     {@link #countNeighbors(int, int)}
 * v3
 *     {@link #nextGeneration()}
 * v4
 *     {@link LifePanel#mouseClicked(MouseEvent)}
 * v5
 *     {@link LifePanel#keyTyped(KeyEvent)}
 * v6
 *     {@link #save()}
 *     {@link #load()}
 * </pre>
 */
public class ConwaysGameOfLife extends JFrame {
    /** file name for loads and saves */
    static final String sFname = "cgol.dat";
    /** cell width in pixels */
    static final int sCellW = 10;
    /** cell height in pixels */
    static final int sCellH = 10;

    /** columns in board */
    final int mBoardW;
    /** rows in board */
    final int mBoardH;
    /** window width in pixels */
    final int mWindowW;
    /** window height in pixels */
    final int mWindowH;

    /** conway's game of life board. true = alive; false = dead/empty */
    boolean[][] mBoard;
    /** generation number (starting at 0) */
    int mGen;

    // -----------------------------------------------------------------------
    /** create with default size */
    public ConwaysGameOfLife() {
        this(50, 50); // 50 x 50 is the default
    }

    // -----------------------------------------------------------------------
    /**
     * create with specified size
     *
     * @param w
     *            is the width.
     * @param h
     *            is the height.
     */
    public ConwaysGameOfLife(int w, int h) {
        assert (w > 0 && h > 0);
        mBoardW = w;
        mBoardH = h;
        mBoard = new boolean[mBoardH][mBoardW];

        setTitle("Conway's Game of Life: generation=" + mGen);
        int winW = mBoardW * sCellW;
        int winH = mBoardH * sCellH;
        setSize(winW, winH);
        setLocation(50, 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        LifePanel lp = new LifePanel(this);
        add(lp);
        addMouseListener(lp);
        addKeyListener(lp);

        initializeBoard();
        int row = 10;
        int col = 10;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row = 11;
        col = 11;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row = 10;
        col = 12;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row = 11;
        col = 12;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row = 12;
        col = 11;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row = 10;
        col = 11;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row = 11;
        col = 10;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row = 12;
        col = 10;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row = 12;
        col = 12;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " + countNeighbors(row, col) + " neighbors");
        row=1;
        col=1;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=1;
        col=48;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=48;
        col=1;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=48;
        col=48;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");

        row=1;
        col=16;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=1;
        col=34;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=48;
        col=16;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=48;
        col=34;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");

        row=0;
        col=0;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=0;
        col=49;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=49;
        col=0;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        row=49;
        col=49;
        System.out.println("mBoard:" + "[" + row + "]" + "[" + col + "] has " +countNeighbors(row,col)+ " neighbors");
        setVisible(true);
        createBufferStrategy(2); // double buffer
        Insets in = getInsets();
        mWindowW = winW + in.left;
        mWindowH = winH + in.top;
        setSize(mWindowW, mWindowH);
    }

    // -----------------------------------------------------------------------
    /**
     * code to set up the initial board configuration.
     *
     * @todo for students v1, add code here so your toString has something to show.
     */
    protected void initializeBoard() {
        // ------glider gun-------
//         mBoard[10][10] = true;
//         mBoard[10][11] = true;
//         mBoard[11][10] = true;
//         mBoard[11][11] = true;
//         mBoard[10][20] = true;
//         mBoard[11][20] = true;
//         mBoard[12][20] = true;
//         mBoard[9][21] = true;
//         mBoard[13][21] = true;
//         mBoard[8][22] = true;
//         mBoard[14][22] = true;
//         mBoard[8][23] = true;
//         mBoard[14][23] = true;
//         mBoard[11][24] = true;
//         mBoard[9][25] = true;
//         mBoard[13][25] = true;
//         mBoard[11][26] = true;
//         mBoard[11][27] = true;
//         mBoard[10][26] = true;
//         mBoard[12][26] = true;
//         mBoard[10][30] = true;
//         mBoard[9][30] = true;
//         mBoard[8][30] = true;
//         mBoard[10][31] = true;
//         mBoard[9][31] = true;
//         mBoard[8][31] = true;
//         mBoard[7][32] = true;
//         mBoard[11][32] = true;
//         mBoard[7][34] = true;
//         mBoard[11][34] = true;
//         mBoard[6][34] = true;
//         mBoard[12][34] = true;
//         mBoard[8][44] = true;
//         mBoard[9][44] = true;
//         mBoard[8][45] = true;
//         mBoard[9][45] = true;

        // -------glider-------
        mBoard[10][10] = true;
        mBoard[11][11] = true;
        mBoard[12][11] = true;
        mBoard[11][12] = true;
        mBoard[10][12] = true;

//          mBoard[30][31] = true;
//          mBoard[30][32] = true;
//          mBoard[30][33] = true;
//          mBoard[31][30] = true;
//          mBoard[31][31] = true;
//          mBoard[31][32] = true;

//        mBoard[0][0] = true;
//        mBoard[0][49] = true;
//        mBoard[49][0] = true;
//        mBoard[49][49] = true;
//        mBoard[1][1] = true;
//        mBoard[1][48] = true;
//        mBoard[48][1] = true;
//        mBoard[48][48] = true;
//        mBoard[2][2] = true;
//        mBoard[47][2] = true;
//        mBoard[2][47] = true;
//        mBoard[47][47] = true;
//        mBoard[2][0] = true;
//        mBoard[0][2] = true;
//        mBoard[0][47] = true;
//        mBoard[47][0] = true;
//        mBoard[47][49] = true;
//        mBoard[49][47] = true;
//        mBoard[49][2] = true;
//        mBoard[2][49] = true;
//
//
//        mBoard[0][15] = true;
//        mBoard[0][35] = true;
//        mBoard[49][15] = true;
//        mBoard[49][35] = true;
//
//        mBoard[1][16] = true;
//        mBoard[1][34] = true;
//        mBoard[48][16] = true;
//        mBoard[48][34] = true;
//
//        mBoard[2][17] = true;
//        mBoard[2][33] = true;
//        mBoard[47][17] = true;
//        mBoard[47][33] = true;
//
//        mBoard[2][15] = true;
//        mBoard[2][35] = true;
//        mBoard[47][15] = true;
//        mBoard[47][35] = true;
//
//        mBoard[0][17] = true;
//        mBoard[0][33] = true;
//        mBoard[49][17] = true;
//        mBoard[49][33] = true;


    }

    // -----------------------------------------------------------------------
    /**
     * this function replaces the current generation with the next. see
     * http://en.wikipedia.org/wiki/Conway's_Game_of_Life (especially Rules) for a
     * description of how to calculate the next generation.
     *
     * @todo for students v3.
     */
    public void nextGeneration() {
        ++mGen;
        System.out.println("creating next generation " + mGen);
        boolean[][] next = new boolean[mBoardH][mBoardW];
        for (int i = 0; i < mBoardH; i++) {
            for (int j = 0; j < mBoardW; j++) {
                if (countNeighbors(i, j) == 3 || (mBoard[i][j]==true && countNeighbors(i,j)==2)) {
                    next[i][j] = true;
                }
//                } else if ((countNeighbors(i, j) == 4)) {
//                    next[i][j] = mBoard[i][j];
//                }
                else
                    next[i][j] = false;
            }
        }
        mBoard = next; // replace old generation with new one
        setTitle("Conway's Game of Life: generation=" + mGen);
        System.out.println(this);
    }

    // -----------------------------------------------------------------------
    /**
     * count the number of (living) neighbors around x = (r,c).
     *
     * <pre>
     *     - - -
     *     - x -
     *     - - -
     * </pre>
     *
     * @param r
     *            is the row
     * @param c
     *            is the column
     * @return the count of living neighbors.
     * @todo for students v2.
     */
    @VisibleForTesting // really should be private instead of public
    public int countNeighbors(int r, int c) {
        int neighbors = 0;
//        for (int i = -1; i <= 1; i++) {
//            for (int j = -1; j <= 1; j++) {
             if ((r-1 >= 0) && (c-1 >= 0) && (mBoard[r - 1][c - 1]))
                 neighbors++;
             if ((r-1 >= 0) && mBoard[r - 1][c])
                 neighbors++;
             if ((r-1 >= 0) && (c+1 < mBoardW) && mBoard[r - 1][c + 1])
                 neighbors++;
             if ((r-1 >= 0) && (c-1 >= 0) && mBoard[r][c - 1])
                 neighbors++;
             if ((r-1 >= 0) && (c+1 < mBoardW) && mBoard[r][c + 1])
                 neighbors++;
             if ((r+1 < mBoardH) && (c-1 >= 0) && mBoard[r + 1][c - 1])
                 neighbors++;
             if ((r+1 < mBoardH) && mBoard[r + 1][c])
                 neighbors++;
             if ((r+1 < mBoardH) && (c+1 < mBoardW) && mBoard[r + 1][c + 1])
                 neighbors++;
//            }
//        }

        return neighbors;
    }

    // -----------------------------------------------------------------------
    /**
     * ye olde toe stringe methode. returns a string representing the board (alive
     * and dead cells) that can be printed or saved to some other output stream.
     *
     * @return a string that represents the board.
     * @todo for students v1.
     */
    @Override
    public String toString() {
        String s = "";
        s += "gen=" + this.mGen + "\n";
        for (int i = 0; i < mBoardH; i++) {
            for (int j = 0; j < mBoardW; j++) {
                if (mBoard[i][j] == true) {
                    s += "" + i + " " + "" + j + "\n";
                }
            }
        }
//        PrintWriter outputStream = null;
//        try {
//            outputStream = new PrintWriter(new FileOutputStream("toString.txt"));
//            outputStream.print(s);
//            outputStream.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("Problem opening files");
//            System.exit(0);
//        }
        return s;
    }

    // -----------------------------------------------------------------------
    /**
     * load a saved game. load it from file named sFname.
     *
     * @todo for students v6.
     */
    public void load() {
        for (int i = 0; i < mBoardH; i++) {
            for (int j = 0; j < mBoardW; j++) {
                mBoard[i][j] = false;
            }
        }

        Scanner inputStream = null;
        try {
            inputStream = new Scanner(new FileInputStream("loadTest.txt"));// input
        } catch (FileNotFoundException e) {
            System.out.println("Problem opening files");
            System.exit(0);
        }
        while (inputStream.hasNextInt()) {
            // String mgen = inputStream.nextLine();
            int x = inputStream.nextInt();
            int y = inputStream.nextInt();
            mBoard[x][y] = true;
            System.out.println("mboard: [" + x + "] [" + y + "] = " + mBoard[x][y]);
        }

    }

    // -----------------------------------------------------------------------
    /**
     * save the current game. save it to file named sFname.
     *
     * @todo for students v6.
     */
    public void save() {
    }

    // -----------------------------------------------------------------------
    public static void main(String[] args) {
        new ConwaysGameOfLife();
    }

}
// ===========================================================================