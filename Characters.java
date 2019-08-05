/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 64 "model.ump"
// line 129 "model.ump"
public class Characters extends Piece
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Characters Attributes
    private boolean isPlayer;
    int id;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Characters(Board aBoard, boardSpot aLocation, String aName, boolean aIsPlayer, int d)
    {
        super(aBoard, aLocation, aName);
        isPlayer = aIsPlayer;
        id = d;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setIsPlayer(boolean aIsPlayer)
    {
        boolean wasSet = false;
        isPlayer = aIsPlayer;
        wasSet = true;
        return wasSet;
    }

    public boolean getIsPlayer()
    {
        return isPlayer;
    }

    public void delete()
    {
        super.delete();
    }


    public String toString()
    {
        return super.toString() + "["+
                "isPlayer" + ":" + getIsPlayer()+ "]";
    }
}