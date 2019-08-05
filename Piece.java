/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/



// line 54 "model.ump"
// line 123 "model.ump"
public class Piece extends BoardEntity
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Piece Attributes
    private boardSpot location;
    private String name;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Piece(Board aBoard, boardSpot aLocation, String aName)
    {
        super(aBoard);
        location = aLocation;
        name = aName;


    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setLocation(boardSpot aLocation)
    {
        boolean wasSet = false;
        location = aLocation;
        wasSet = true;
        return wasSet;
    }

    public boolean setName(String aName)
    {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boardSpot getLocation()
    {
        return location;
    }

    public String getName()
    {
        return name;
    }

    public void delete()
    {
        super.delete();
    }


    public String toString()
    {
        return super.toString() + "["+
                "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "location" + "=" + (getLocation() != null ? !getLocation().equals(this)  ? getLocation().toString().replaceAll("  ","    ") : "this" : "null");
    }
}