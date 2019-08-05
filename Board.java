/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.*;

// line 9 "model.ump"
// line 86 "model.ump"
public class Board
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Board Attributes
    private ArrayList weapons;
    private ArrayList<Characters> characters;
    private ArrayList rooms;
    private int WIDTH = 24;
    private int HEIGHT = 25;
    private char[][] boardArray = new char[][] {
    							  {'X','X','X','X','X','X','X','X','X',' ','X','X','X','X',' ','X','X','X','X','X','X','X','X','X'},//0
    		 					  {'K','K','K','K','K','K','X',' ',' ',' ','B','B','B','B',' ',' ',' ','X','C','C','C','C','C','C'},//1
    		 					  {'K','K','K','K','K','K',' ',' ','B','B','B','B','B','B','B','B',' ',' ','C','C','C','C','C','C'},//2
    		 					  {'K','K','K','K','K','K',' ',' ','B','B','B','B','B','B','B','B',' ',' ','C','C','C','C','C','C'},//3
    		 					  {'K','K','K','K','K','K',' ',' ','B','B','B','B','B','B','B','B',' ',' ','n','C','C','C','C','C'},//4
    		 					  {'K','K','K','K','K','K',' ',' ','e','B','B','B','B','B','B','w',' ',' ',' ','C','C','C','C','X'},//5
    		 					  {'X','K','K','K','n','K',' ',' ','B','B','B','B','B','B','B','B',' ',' ',' ',' ',' ',' ',' ',' '},//6
    		 					  {' ',' ',' ',' ',' ',' ',' ',' ','B','B','B','B','B','B','B','B',' ',' ',' ',' ',' ',' ',' ','X'},//7
    		 					  {'X',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','I','I','I','I','I','I'},//8
    		 					  {'D','D','D','D','D',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','e','I','I','I','I','I'},//9
    		 					  {'D','D','D','D','D','D','D','D',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','I','I','I','I','I','I'},//10
    		 					  {'D','D','D','D','D','D','D','D',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','I','I','I','I','I','I'},//11
    		 					  {'D','D','D','D','D','D','D','w',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','I','I','I','I','n','I'},//12
    		 					  {'D','D','D','D','D','D','D','D',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','X'},//13
    		 					  {'D','D','D','D','D','D','D','D',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','R','R','s','R','R','X'},//14
    		 					  {'D','D','D','D','D','D','n','D',' ',' ',' ',' ',' ',' ',' ',' ',' ','R','R','R','R','R','R','R'},//15
    		 					  {'X',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','e','R','R','R','R','R','R'},//16
    		 					  {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','R','R','R','R','R','R','R'},//17
    		 					  {'X',' ',' ',' ',' ',' ',' ',' ',' ','H','H','s','s','H','H',' ',' ',' ','R','R','R','R','R','X'},//18
    		 					  {'L','L','L','L','L','L','s',' ',' ','H','H','H','H','H','H',' ',' ',' ',' ',' ',' ',' ',' ',' '},//19
    		 					  {'L','L','L','L','L','L','L',' ',' ','H','H','H','H','H','w',' ',' ',' ',' ',' ',' ',' ',' ',' '},//20
    		 					  {'L','L','L','L','L','L','L',' ',' ','H','H','H','H','H','H',' ',' ','s','S','S','S','S','S','S'},//21
    		 					  {'L','L','L','L','L','L','L',' ',' ','H','H','H','H','H','H',' ',' ','S','S','S','S','S','S','S'},//22
    		 					  {'L','L','L','L','L','L','L',' ',' ','H','H','H','H','H','H',' ',' ','S','S','S','S','S','S','S'},//23
    		 					  {'L','L','L','L','L','L','X',' ','X','H','H','H','H','H','H','X',' ','X','S','S','S','S','S','S'},};//24
    		 					  //0   1  	2  	3  	4  	5  	6  	7  	8  	9  	10  11  12  13  14  15  16  17  18  19 20  21  22  23

    //Board Associations
    private List<boardSpot> boardSpots;
    private List<BoardEntity> boardEntities;
    private GameMain gameMain;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Board(ArrayList aWeapons, ArrayList aCharacters, ArrayList aRooms, GameMain aGameMain)
    {
        weapons = aWeapons;
        characters = aCharacters;
        rooms = aRooms;
        boardSpots = new ArrayList<boardSpot>();
        boardEntities = new ArrayList<BoardEntity>();
        if (aGameMain == null || aGameMain.getBoard() != null)
        {
            throw new RuntimeException("Unable to create Board due to aGameMain. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        gameMain = aGameMain;
    }

    public Board(ArrayList aWeapons, ArrayList aCharacters, ArrayList aRooms, Deck aDeckForGameMain)
    {
        weapons = aWeapons;
        characters = aCharacters;
        rooms = aRooms;
        boardSpots = new ArrayList<boardSpot>();
        boardEntities = new ArrayList<BoardEntity>();
       // gameMain = new GameMain(this, aDeckForGameMain);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setWeapons(ArrayList aWeapons)
    {
        boolean wasSet = false;
        weapons = aWeapons;
        wasSet = true;
        return wasSet;
    }

    public boolean setCharacters(ArrayList aCharacters)
    {
        boolean wasSet = false;
        characters = aCharacters;
        wasSet = true;
        return wasSet;
    }

    public boolean setRooms(ArrayList aRooms)
    {
        boolean wasSet = false;
        rooms = aRooms;
        wasSet = true;
        return wasSet;
    }

    public ArrayList getWeapons()
    {
        return weapons;
    }

    public ArrayList getCharacters()
    {
        return characters;
    }

    public ArrayList getRooms()
    {
        return rooms;
    }
    /* Code from template association_GetMany */
    public boardSpot getBoardSpot(int index)
    {
        boardSpot aBoardSpot = boardSpots.get(index);
        return aBoardSpot;
    }

    public List<boardSpot> getBoardSpots()
    {
        List<boardSpot> newBoardSpots = Collections.unmodifiableList(boardSpots);
        return newBoardSpots;
    }

    public int numberOfBoardSpots()
    {
        int number = boardSpots.size();
        return number;
    }

    public boolean hasBoardSpots()
    {
        boolean has = boardSpots.size() > 0;
        return has;
    }

    public int indexOfBoardSpot(boardSpot aBoardSpot)
    {
        int index = boardSpots.indexOf(aBoardSpot);
        return index;
    }
    /* Code from template association_GetMany */
    public BoardEntity getBoardEntity(int index)
    {
        BoardEntity aBoardEntity = boardEntities.get(index);
        return aBoardEntity;
    }

    public List<BoardEntity> getBoardEntities()
    {
        List<BoardEntity> newBoardEntities = Collections.unmodifiableList(boardEntities);
        return newBoardEntities;
    }

    public int numberOfBoardEntities()
    {
        int number = boardEntities.size();
        return number;
    }

    public boolean hasBoardEntities()
    {
        boolean has = boardEntities.size() > 0;
        return has;
    }

    public int indexOfBoardEntity(BoardEntity aBoardEntity)
    {
        int index = boardEntities.indexOf(aBoardEntity);
        return index;
    }
    /* Code from template association_GetOne */
    public GameMain getGameMain()
    {
        return gameMain;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfBoardSpots()
    {
        return 0;
    }
    /* Code from template association_AddManyToOne */
    public boardSpot addBoardSpot(String aType, String aName, int aX, int aY, boolean aAvailable)
    {
        return new boardSpot(aType, aName, aX, aY, aAvailable, this);
    }

    public boolean addBoardSpot(boardSpot aBoardSpot)
    {
        boolean wasAdded = false;
        if (boardSpots.contains(aBoardSpot)) { return false; }
        Board existingBoard = aBoardSpot.getBoard();
        boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);
        if (isNewBoard)
        {
            aBoardSpot.setBoard(this);
        }
        else
        {
            boardSpots.add(aBoardSpot);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeBoardSpot(boardSpot aBoardSpot)
    {
        boolean wasRemoved = false;
        //Unable to remove aBoardSpot, as it must always have a board
        if (!this.equals(aBoardSpot.getBoard()))
        {
            boardSpots.remove(aBoardSpot);
            wasRemoved = true;
        }
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addBoardSpotAt(boardSpot aBoardSpot, int index)
    {
        boolean wasAdded = false;
        if(addBoardSpot(aBoardSpot))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfBoardSpots()) { index = numberOfBoardSpots() - 1; }
            boardSpots.remove(aBoardSpot);
            boardSpots.add(index, aBoardSpot);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveBoardSpotAt(boardSpot aBoardSpot, int index)
    {
        boolean wasAdded = false;
        if(boardSpots.contains(aBoardSpot))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfBoardSpots()) { index = numberOfBoardSpots() - 1; }
            boardSpots.remove(aBoardSpot);
            boardSpots.add(index, aBoardSpot);
            wasAdded = true;
        }
        else
        {
            wasAdded = addBoardSpotAt(aBoardSpot, index);
        }
        return wasAdded;
    }
    /* Code from template association_IsNumberOfValidMethod */
    public boolean isNumberOfBoardEntitiesValid()
    {
        boolean isValid = numberOfBoardEntities() >= minimumNumberOfBoardEntities() && numberOfBoardEntities() <= maximumNumberOfBoardEntities();
        return isValid;
    }
    /* Code from template association_RequiredNumberOfMethod */
    public static int requiredNumberOfBoardEntities()
    {
        return 21;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfBoardEntities()
    {
        return 21;
    }
    /* Code from template association_MaximumNumberOfMethod */
    public static int maximumNumberOfBoardEntities()
    {
        return 21;
    }
    /* Code from template association_AddMNToOnlyOne */
    /*public BoardEntity addBoardEntity()
    {
        if (numberOfBoardEntities() >= maximumNumberOfBoardEntities())				//GIVES ERROR
        {
            return null;
        }
        else
        {
            return new BoardEntity(this);
        }
    }*/

    public boolean addBoardEntity(BoardEntity aBoardEntity)
    {
        boolean wasAdded = false;
        if (boardEntities.contains(aBoardEntity)) { return false; }
        if (numberOfBoardEntities() >= maximumNumberOfBoardEntities())
        {
            return wasAdded;
        }

        Board existingBoard = aBoardEntity.getBoard();
        boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

        if (isNewBoard && existingBoard.numberOfBoardEntities() <= minimumNumberOfBoardEntities())
        {
            return wasAdded;
        }

        if (isNewBoard)
        {
            aBoardEntity.setBoard(this);
        }
        else
        {
            boardEntities.add(aBoardEntity);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeBoardEntity(BoardEntity aBoardEntity)
    {
        boolean wasRemoved = false;
        //Unable to remove aBoardEntity, as it must always have a board
        if (this.equals(aBoardEntity.getBoard()))
        {
            return wasRemoved;
        }

        //board already at minimum (21)
        if (numberOfBoardEntities() <= minimumNumberOfBoardEntities())
        {
            return wasRemoved;
        }
        boardEntities.remove(aBoardEntity);
        wasRemoved = true;
        return wasRemoved;
    }

    public void delete()
    {
        for(int i=boardSpots.size(); i > 0; i--)
        {
            boardSpot aBoardSpot = boardSpots.get(i - 1);
            aBoardSpot.delete();
        }
        for(int i=boardEntities.size(); i > 0; i--)
        {
            BoardEntity aBoardEntity = boardEntities.get(i - 1);
            aBoardEntity.delete();
        }
        GameMain existingGameMain = gameMain;
        gameMain = null;
        if (existingGameMain != null)
        {
            existingGameMain.delete();
        }
    }


    public String toString()
    {
        return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "weapons" + "=" + (getWeapons() != null ? !getWeapons().equals(this)  ? getWeapons().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "characters" + "=" + (getCharacters() != null ? !getCharacters().equals(this)  ? getCharacters().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "rooms" + "=" + (getRooms() != null ? !getRooms().equals(this)  ? getRooms().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "gameMain = "+(getGameMain()!=null?Integer.toHexString(System.identityHashCode(getGameMain())):"null");
    }
    
    public String printArray() {
    	String message = "";
    	
    	for (int i = 0; i < HEIGHT; i++) {
    		for (int j = 0; j < WIDTH; j++) {
    			message += boardArray[i][j];
    		}
    		message += "\n";
    	}
    	
    	return message;
    }
    
    
    /**
     * Moves a given tile in a given direction
     */
    public void move(int x, int y, int dx, int dy) {
    	int newX = x+dx;
    	int newY = y+dy;
    	
    	System.out.println("new x pos: " + newX + ", new y pos: " + newY);
    	
    	if (moveIsValid(newX, newY)) {	//if the move is valid
    		char ID = boardArray[y][x];	//save current char
    		boardArray[y][x] = ' ';		//set old spot as blank
    		boardArray[newY][newX] = ID;//set new spot as the character
    		
    		boardSpot b = new boardSpot(newX, newY, true);
    		for (int i = 0; i < characters.size(); i++) {
    			if(characters.get(i).getId() == ID) {
    				characters.get(i).setLocation(b);
    			}
    		}
        	
    	}
    	
    	
    }
    
    public boolean moveIsValid(int newXpos, int newYpos) {
    	//check if out of bounds
    	if(newXpos > WIDTH-1 || newXpos < 0) {
    		System.out.println("fail, can not move out of bounds");
    		return false;
    	}
    	if(newYpos > HEIGHT-1 || newYpos < 0) {
    		System.out.println("fail, can not move out of bounds");
    		return false;
    	}

    	char newLocation = boardArray[newYpos][newXpos];
    	if (newLocation == ' '|| newLocation == 'n' || newLocation == 'e' || newLocation == 's' || newLocation == 'w') {
    		System.out.println("move was completed");
    		return true;
    	} else {
    		System.out.println("fail, you can only move into a free tile");
    		return false;
    	}
    }
    
    /**
     * Adds character in spot when selected
     */
    public void addCharacter (int id, boardSpot bs) {
    	//System.out.println("ID: " + id + " x: " + bs.getX() + " y: " + bs.getY());
    	if(boardArray[bs.getY()][bs.getX()] == ' ') {	//only add to a 
    		boardArray[bs.getY()][bs.getX()] = (char) (id+'0');	// had to cast to int by adding the ascii value of '0' to it.
    	}
    }
    
    
}
