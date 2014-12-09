/*Piece.java
 * Version:
 * $Id: Piece.java,v 1.1 2014/05/07 23:57:53 p142-02n Exp $
 * 
 * Revisions:
 * $Log: Piece.java,v $
 * Revision 1.1  2014/05/07 23:57:53  p142-02n
 * Implemented more strategy, 78% wins.
 *
 * Revision 1.1  2014/04/14 21:49:43  p142-02n
 * Added team name.
 *
 * Revision 1.1  2014/04/09 22:25:46  p142-02n
 * Initial commit. NJM player module
 *
 * Revision 1.1.2.3  2014/03/11 18:04:14  njm3348
 * Final Commit after comments.
 *
 * 
 */
package Players.Thundercatz;
/**
 * 
 * @author Nick Marchionda
 * @author Laura Silva
 * Piece is a class that holds the playerID and size of a particular piece.
 *
 */
public class Piece {
	private int playerID;
	private int size;
	/**
	 * 
	 * @param playerID - playerID is an integer from 1-2 that is used to identify either player 1 or 2
	 * @param size - size is an integer from 1-4 that is the size of the piece.
	 * Constructs a piece object.
	 */
	public Piece(int playerID,int size){
		this.playerID = playerID;
		this.size = size;
	}
	/**
	 * 
	 * @return-returns the integer of the piece's playerID
	 */
	public int getID(){
		return this.playerID;
	}
	/**
	 * 
	 * @return - returns the integer of the piece's size
	 */
	public int getSize(){
		return this.size;
	}
	/**
	 * @return - returns the combined string of playerID and size formatted to fit the board.
	 */
	public String toString(){
		String playerIDstr = Integer.toString(this.playerID);
		String sizeStr = Integer.toString(this.size);
		return sizeStr + "(" + playerIDstr + ")";
	}

}
