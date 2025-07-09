import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MineSweeperTest {
    private MineSweeper mineSweeper;

    @BeforeEach
    public void setUp() {
        mineSweeper = new MineSweeper();
        mineSweeper.setupField(1); // Setup the field with mines
    }

    @Test
    public void testSetupField() {
        int mineCount = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mineSweeper.fieldHidden[i][j] == 100) {
                    mineCount++;
                }
            }
        }
        assertEquals(10, mineCount, "There should be exactly 10 mines on the field.");
    }

    @Test
    public void testBuildHidden() {
        mineSweeper.buildHidden();
        int totalMines = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mineSweeper.fieldHidden[i][j] == 100) {
                    totalMines++;
                }
            }
        }

        // Check that the numbers in fieldHidden are correct
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mineSweeper.fieldHidden[i][j] != 100) {
                    int expectedCount = countMinesAround(i, j);
                    assertEquals(expectedCount, mineSweeper.fieldHidden[i][j], "Mine count around cell (" + i + ", " + j + ") is incorrect.");
                }
            }
        }
    }

    private int countMinesAround(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < 10 && newCol >= 0 && newCol < 10) {
                    if (mineSweeper.fieldHidden[newRow][newCol] == 100) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Test
    public void testPlayMove() {
        // Simulate a move on a safe cell
        InputStream in = new ByteArrayInputStream("0\n0\n".getBytes());
        System.setIn(in);
        boolean result = mineSweeper.playMove();
        assertTrue(result, "The move should be valid and not hit a mine.");

        // Simulate a move on a mine
        // Find a mine location
        int mineRow = -1, mineCol = -1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mineSweeper.fieldHidden[i][j] == 100) {
                    mineRow = i;
                    mineCol = j;
                    break;
                }
            }
            if (mineRow != -1) break;
        }

        in = new ByteArrayInputStream((mineRow + "\n" + mineCol + "\n").getBytes());
        System.setIn(in);
        result = mineSweeper.playMove();
        assertFalse(result, "The move should hit a mine and return false.");
    }

    @Test
    public void testCheckWin() {
        // Uncover all non-mine cells
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mineSweeper.fieldHidden[i][j] != 100) {
                    mineSweeper.fieldVisible[i][j] = 50; // Mark as uncovered
                }
            }
        }
        assertTrue(mineSweeper.checkWin(), "The player should win when all non-mine cells are uncovered.");

        // Reset the visible field and simulate

    }
}