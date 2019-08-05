/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 69 "model.ump"
// line 149 "model.ump"
public class BoardEntity
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //BoardEntity Associations
    private Board board;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public BoardEntity(Board aBoard)
    {
        boolean didAddBoard = setBoard(aBoard);
        if (!didAddBoard)
        {
            throw new RuntimeException("Unable to create boardEntity due to board. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template association_GetOne */
    public Board getBoard()
    {
        return board;
    }
    /* Code from template association_SetOneToAtMostN */
    public boolean setBoard(Board aBoard)
    {
        boolean wasSet = false;
        //Must provide board to boardEntity
        if (aBoard == null)
        {
            return wasSet;
        }

        //board already at maximum (21)
        if (aBoard.numberOfBoardEntities() >= Board.maximumNumberOfBoardEntities())
        {
            return wasSet;
        }

        Board existingBoard = board;
        board = aBoard;
        if (existingBoard != null && !existingBoard.equals(aBoard))
        {
            boolean didRemove = existingBoard.removeBoardEntity(this);
            if (!didRemove)
            {
                board = existingBoard;
                return wasSet;
            }
        }
        board.addBoardEntity(this);
        wasSet = true;
        return wasSet;
    }

    public void delete()
    {
        Board placeholderBoard = board;
        this.board = null;
        if(placeholderBoard != null)
        {
            placeholderBoard.removeBoardEntity(this);
        }
    }

}