/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.ArrayList;
import java.util.HashSet;

// line 2 "model.ump"
// line 81 "model.ump"
public class Player
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Player Attributes
    private String name;
    private HashSet<Card> hand;
    private ArrayList<Card> seen;
    private Characters character;
    private int id;

    //Player Associations
    private GameMain gameMain;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player(HashSet<Card> aHand, ArrayList aSeen, Characters aCharacter, int aId, GameMain aGameMain, String n)
    {
        name = n;
        hand = aHand;
        seen = aSeen;
        character = aCharacter;
        id = aId;
        boolean didAddGameMain = setGameMain(aGameMain);
        if (!didAddGameMain)
        {
            throw new RuntimeException("Unable to create player due to gameMain. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setHand(HashSet<Card> aHand)
    {
        boolean wasSet = false;
        hand = aHand;
        wasSet = true;
        return wasSet;
    }

    public boolean setSeen(ArrayList aSeen)
    {
        boolean wasSet = false;
        seen = aSeen;
        wasSet = true;
        return wasSet;
    }

    public boolean setCharacter(Characters aCharacter)
    {
        boolean wasSet = false;
        character = aCharacter;
        wasSet = true;
        return wasSet;
    }

    public boolean setId(int aId)
    {
        boolean wasSet = false;
        id = aId;
        wasSet = true;
        return wasSet;
    }

    public HashSet getHand()
    {
        return hand;
    }

    public ArrayList getSeen()
    {
        return seen;
    }

    public Characters getCharacter()
    {
        return character;
    }

    public int getId()
    {
        return id;
    }
    /* Code from template association_GetOne */
    public GameMain getGameMain()
    {
        return gameMain;
    }
    /* Code from template association_SetOneToAtMostN */
    public boolean setGameMain(GameMain aGameMain)
    {
        boolean wasSet = false;
        //Must provide gameMain to player
        if (aGameMain == null)
        {
            return wasSet;
        }

        //gameMain already at maximum (6)
        if (aGameMain.numberOfPlayers() >= GameMain.maximumNumberOfPlayers())
        {
            return wasSet;
        }

        GameMain existingGameMain = gameMain;
        gameMain = aGameMain;
        if (existingGameMain != null && !existingGameMain.equals(aGameMain))
        {
            boolean didRemove = existingGameMain.removePlayer(this);
            if (!didRemove)
            {
                gameMain = existingGameMain;
                return wasSet;
            }
        }
        gameMain.addPlayer(this);
        wasSet = true;
        return wasSet;
    }

    public void delete()
    {
        GameMain placeholderGameMain = gameMain;
        this.gameMain = null;
        if(placeholderGameMain != null)
        {
            placeholderGameMain.removePlayer(this);
        }
    }


    public String toString()
    {
        return super.toString() + "["+
                "id" + ":" + getId()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "hand" + "=" + (getHand() != null ? !getHand().equals(this)  ? getHand().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "seen" + "=" + (getSeen() != null ? !getSeen().equals(this)  ? getSeen().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "character" + "=" + (getCharacter() != null ? !getCharacter().equals(this)  ? getCharacter().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "gameMain = "+(getGameMain()!=null?Integer.toHexString(System.identityHashCode(getGameMain())):"null");
    }
}