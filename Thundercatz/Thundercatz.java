/* 
 * NJM3348.java
 * Version:
 * $Id: Thundercatz.java,v 1.3 2014/05/11 19:52:51 p142-02n Exp $
 * 
 * Revisions:
 * $Log: Thundercatz.java,v $
 * Revision 1.3  2014/05/11 19:52:51  p142-02n
 * Commented code, average of 43/50 for both player 1 and 2
 *
 * Revision 1.2  2014/05/11 19:32:29  p142-02n
 * Up to 90% wins; added defending with pieces size 4.
 *
 * Revision 1.1  2014/05/07 23:57:53  p142-02n
 * Implemented more strategy, 78% wins.
 *
 * Revision 1.2  2014/04/14 22:11:56  p142-02n
 * Added comments and updated variable names. PART 2 COMPLETE!
 *
 * Revision 1.1  2014/04/14 21:49:43  p142-02n
 * Added team name.
 *
 * Revision 1.4  2014/04/14 21:40:35  p142-02n
 * All methods have been implemented. Loops when playing against itself :(  Needs Comments and better variable names
 *
 * Revision 1.3  2014/04/11 04:10:18  p142-02n
 * Added comments to move() method.
 *
 * Revision 1.2  2014/04/10 22:16:44  p142-02n
 * Implemented move() function. Needs to implement ability to gobble other stacks in move()
 *
 * Revision 1.1.2.4  2014/03/11 18:04:14  njm3348
 * Final Commit after comments.
 *
 * Revision 1.1.2.3  2014/03/11 16:42:28  njm3348
 * Commit before comments. Methods work at this point.
 *
 * Revision 1.1.2.2  2014/03/11 03:45:18  njm3348
 * Commited after init was done and gameDump was done
 *
 * Revision 1.1.2.1  2014/03/10 20:52:33  njm3348
 * Intial Commit. Second file after changinig name
 *
 * 
 */

package Players.Thundercatz;

import java.util.ArrayList;
import java.util.Stack;

import Engine.Logger;
import Interface.Coordinate;
import Interface.GobbletPart1;
import Interface.PlayerModule;
import Interface.PlayerMove;

/**
 * Player class that implements the methods in the PlayerModule interface.
 * 
 * @author Nick Marchionda
 * @author Laura Silva
 * 
 */
public class Thundercatz implements PlayerModule, GobbletPart1 {
    private int playerID;
    private ArrayList<ArrayList<Stack<Piece>>> board;
    private ArrayList<Stack<Piece>> p1Stack;
    private ArrayList<Stack<Piece>> p2Stack;
    private Logger log;

    public final static int MAX_SIZE = 4;
    public final static int MAX_DIM = 4;
    public boolean invalid = false;

    @Override
    /**
     * Prints game data out to standard output.
     */
    public void dumpGameState() {
        for (int row = 0; row < MAX_DIM; row++) {
            for (int col = 0; col < MAX_DIM; col++) {
                if (board.get(row).get(col).isEmpty()) {
                    System.out.print("[]");
                    System.out.print("   ");
                } else {
                    System.out.print(board.get(row).get(col).peek().toString());
                    System.out.print(" ");

                }

            }
            if (row == 0 || row == 2) {
                if (row == 0) {
                    for (Stack<Piece> stack : p1Stack) {

                        if (stack.isEmpty()) {
                            System.out.print(" _");
                        } else {
                            System.out.print("  " + stack.peek().getSize());
                        }
                    }
                } else {
                    for (Stack<Piece> stack : p2Stack) {
                        if (stack.isEmpty()) {
                            System.out.print(" _");
                        } else {
                            System.out.print("  " + stack.peek().getSize());
                        }
                    }
                }
            }
            System.out.println();
        }

    }

    @Override
    /**
     * Gets the playerID of the specific player.
     * @return playerID (int)
     */
    public int getID() {
        return this.playerID;
    }

    @Override
    /**
     * Gets the playerID of the piece on the spot of the board given
     * @param  row (int)
     * @param  col (int)
     * @return playerID (int) 
     */
    public int getTopOwnerOnBoard(int row, int col) {
        if (board.get(row).get(col).isEmpty()) {
            return -1;
        }
        return board.get(row).get(col).peek().getID();
    }

    @Override
    /**
     * Gets the top piece's size on the spot of the board given
     * @param  row (int)
     * @param  col (int)
     * @return size (int)
     */
    public int getTopSizeOnBoard(int row, int col) {
        if (board.get(row).get(col).isEmpty()) {
            return -1;
        }

        return board.get(row).get(col).peek().getSize();
    }

    @Override
    /**
     * Gets the the size of the top piece on the stack given
     * @param  playerID (int)
     * @param  numStack (int)
     * @return size (int)
     */
    public int getTopSizeOnStack(int playerID, int numStack) {

        if (playerID == 1) {
            if (p1Stack.get(numStack - 1).isEmpty()) {
                return -1;
            }
            return p1Stack.get(numStack - 1).peek().getSize();
        } else {
            if (p2Stack.get(numStack - 1).isEmpty()) {
                return -1;
            }
            return p2Stack.get(numStack - 1).peek().getSize();
        }
    }

    @Override
    /**
     * Constructs the board, player stacks and pieces to fill those stacks
     * @param  logger (Logger)
     * @param  playerID (int)
     */
    public void init(Logger logger, int playerID) {
        this.log = logger;
        this.playerID = playerID;
        p1Stack = new ArrayList<Stack<Piece>>();
        p2Stack = new ArrayList<Stack<Piece>>();
        for (int index = 0; index < 3; index++) {
            this.p1Stack.add(new Stack<Piece>());
            this.p2Stack.add(new Stack<Piece>());
        }
        for (int l = 0; l < 3; l++) {
            p1Stack.get(l).push(new Piece(1, 1));
            p1Stack.get(l).push(new Piece(1, 2));
            p1Stack.get(l).push(new Piece(1, 3));
            p1Stack.get(l).push(new Piece(1, 4));
            p2Stack.get(l).push(new Piece(2, 1));
            p2Stack.get(l).push(new Piece(2, 2));
            p2Stack.get(l).push(new Piece(2, 3));
            p2Stack.get(l).push(new Piece(2, 4));
        }

        board = new ArrayList<ArrayList<Stack<Piece>>>();
        for (int i = 0; i < MAX_DIM; i++) {
            board.add(new ArrayList<Stack<Piece>>());
            for (int j = 0; j < MAX_DIM; j++) {
                board.get(i).add(new Stack<Piece>());
            }
        }
    }

    @Override
    /**
     * Updates the internal representation of the board and stacks that is displayed to through gameStateDump. 
     * @param move - move is a playerMove object with the start and end coordinates and id of the player.
     * 
     */
    public void lastMove(PlayerMove move) {
        if (move.getPlayerId() == 1) {
            if (move.getStack() == 0) {
                Piece piece = board.get(move.getStartRow())
                        .get(move.getStartCol()).pop();
                board.get(move.getEndRow()).get(move.getEndCol()).push(piece);
            } else {
                Piece piece = p1Stack.get(move.getStack() - 1).pop();
                board.get(move.getEndRow()).get(move.getEndCol()).push(piece);
            }
        } else {
            if (move.getStack() == 0) {
                Piece piece = board.get(move.getStartRow())
                        .get(move.getStartCol()).pop();
                board.get(move.getEndRow()).get(move.getEndCol()).push(piece);
            } else {
                Piece piece = p2Stack.get(move.getStack() - 1).pop();
                board.get(move.getEndRow()).get(move.getEndCol()).push(piece);
            }

        }

    }

    @Override
    /**
     * Generates player move object
     * 
     * @return PlayerMove object
     */
    public PlayerMove move() {
        // Opposite team player's ID
        int paramID;

        // Piece that will be moved
        Piece pieceToMove = null;

        // Stack number
        int numStack = 1;

        // Place on board in which to move piece
        Coordinate endCoordinate = null;

        // Size that piece must be bigger than in order to gobble
        int targetSize = 0;

        // Variable to hold the final move
        PlayerMove newPlayerMove = null;

        // Holds greatest piece size in player stacks
        int piecesize = 0;

        // Shows if piece is blocking
        boolean block = false;

        // Determine which player stack to use
        ArrayList<Stack<Piece>> playerStack;
        if (this.playerID == 1) {
            playerStack = p1Stack;
            paramID = 2;
        } else {
            playerStack = p2Stack;
            paramID = 1;
        }

        // Make defense priority when it finds 3 of opposite player in one
        // winning line
        for (int row = 0; row < MAX_DIM; row++) {
            for (int col = 0; col < MAX_DIM; col++) {
                if (board.get(row).get(col).isEmpty()
                        && gobbleCheck(row, col, paramID)) {
                    block = true;
                    endCoordinate = new Coordinate(row, col);
                    break;
                }
            }
        }

        // Make winning second in priority when it finds 3 of own player in one
        // winning line
        if (endCoordinate == null) {
            for (int row = 0; row < MAX_DIM; row++) {
                for (int col = 0; col < MAX_DIM; col++) {
                    if (board.get(row).get(col).isEmpty()
                            && gobbleCheck(row, col, playerID)) {
                        endCoordinate = new Coordinate(row, col);
                        break;
                    }
                }
            }
        }

        // If it can't defend or make winning move, start with diagonal bottom
        // right to top left
        for (int row = 0; row < MAX_DIM; row++) {
            for (int col = 0; col < MAX_DIM; col++) {
                //if in diagonal row and column
                if (row == col || row + col == 3) {
                    for (int x = 0; x < MAX_DIM; x++) {
                        for (int y = 0; y < MAX_DIM; y++) {
                            if (board.get(x).get(y).isEmpty()) {
                                if (x == y) {
                                    endCoordinate = new Coordinate(x, y);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Check if coordinate has not been determined
        if (endCoordinate == null) {

            // Find place on board where piece will be moved to
            for (int row = 0; row < MAX_DIM; row++) {
                for (int col = 0; col < MAX_DIM; col++) {
                    // If space is empty
                    if (board.get(row).get(col).isEmpty()) {
                        endCoordinate = new Coordinate(row, col);
                    }
                    // If space is not empty, check if can gobble
                    else if (board.get(row).get(col).peek().getSize() < MAX_SIZE) {
                        if (gobbleCheck(row, col, paramID)
                                && board.get(row).get(col).peek().getID() != this.playerID) {
                            targetSize = board.get(row).get(col).peek()
                                    .getSize();
                            endCoordinate = new Coordinate(row, col);
                            break;
                        }
                    }
                }
            }
        }
        
        // If it's blocking, use piece size 4 to block unless all size 4 are already blocking
        if (block) {
            for (int row = 0; row < MAX_DIM; row++) {
                for (int col = 0; col < MAX_DIM; col++) {
                    if (!board.get(row).get(col).isEmpty()
                            && board.get(row).get(col).peek().getSize() == MAX_SIZE
                            && board.get(row).get(col).peek().getID() == playerID
                            && !gobbleCheck(row, col, paramID)) {
                        pieceToMove = board.get(row).get(col).peek();
                        newPlayerMove = new PlayerMove(pieceToMove.getID(), 0,
                                pieceToMove.getSize(),
                                new Coordinate(row, col), endCoordinate);
                        return newPlayerMove;
                    }
                }
            }
        }

        // Find the greatest pieces from the player stacks to move to the board
        for (Stack<Piece> stack : playerStack) {
            if (stack.isEmpty()) {

            }

            else if (stack.peek().getSize() >= piecesize) {
                piecesize = stack.peek().getSize();
                numStack = 1 + playerStack.indexOf(stack);
                pieceToMove = stack.peek();
            }

        }

        // Return player move after it finds the largest piece from stack, when not empty
        if (pieceToMove != null) {
            newPlayerMove = new PlayerMove(pieceToMove.getID(), numStack,
                    pieceToMove.getSize(), new Coordinate(-1, -1),
                    endCoordinate);
            return newPlayerMove;
        }

        // If all 3 stacks are empty, find piece from board to move
        if (pieceToMove == null) {
            for (int row = 0; row < MAX_DIM; row++) {
                for (int col = 0; col < MAX_DIM; col++) {
                    if (!board.get(row).get(col).isEmpty()
                            && board.get(row).get(col).peek().getSize() > targetSize
                            && board.get(row).get(col).peek().getID() == this.playerID) {
                        pieceToMove = board.get(row).get(col).peek();
                        newPlayerMove = new PlayerMove(pieceToMove.getID(), 0,
                                pieceToMove.getSize(),
                                new Coordinate(row, col), endCoordinate);
                    }
                }
            }
        }
        return newPlayerMove;
    }

    /**
     * Check if spot is gobble-able.
     * 
     * @param row
     *            in which to move (int)
     * @param col
     *            in which to move (int)
     * @param playerID
     *            of opposing player(int)
     * @return true if gobble-able, false if not (boolean)
     */
    public boolean gobbleCheck(int row, int col, int playerID) {
        // Counters of number of pieces on winning lines
        int countCol = 0;
        int countRow = 0;
        int countDiag1 = 0;
        int countDiag2 = 0;

        // Check columns
        for (int c = 0; c < MAX_DIM; c++) {
            if (!board.get(row).get(c).isEmpty()) {
                if (board.get(row).get(c).peek().getID() == playerID) {
                    countCol += 1;
                }
            }
        }
        if (countCol == 3) {
            return true;
        }

        // Check rows
        for (int r = 0; r < MAX_DIM; r++) {
            if (!board.get(r).get(col).isEmpty()) {
                if (board.get(r).get(col).peek().getID() == playerID) {
                    countRow += 1;
                }
            }
        }
        if (countRow == 3) {
            return true;
        }

        // Check diagonals
        if (row == col || row + col == 3) {
            for (int x = 0; x < MAX_DIM; x++) {
                for (int y = 0; y < MAX_DIM; y++) {
                    if (!board.get(x).get(y).isEmpty()) {
                        if (x == y) {
                            if (board.get(x).get(y).peek().getID() == playerID) {
                                countDiag1 += 1;
                            }
                        }

                        if (x + y == 3) {
                            if (board.get(x).get(y).peek().getID() == playerID) {
                                countDiag2 += 1;
                            }
                        }
                    }
                }
            }
        }
        if (countDiag1 == 3) {
            return true;
        }

        if (countDiag2 == 3) {
            return true;
        }

        return false;

    }

    @Override
    public void playerInvalidated(int playerId) {
        invalid = true;
        System.out.println("Player " + playerId + " is invalidated.");
    }

}
