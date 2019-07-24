/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.util.ArrayList;

// line 74 "model.ump"
// line 154 "model.ump"
public class Room extends BoardEntity
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Room Attributes
    private String name;
    private ArrayList corners;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Room(Board aBoard, String aName, ArrayList aCorners)
    {
        super(aBoard);
        name = aName;
        corners = aCorners;
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setName(String aName)
    {
        boolean wasSet = false;
        name = aName;
        wasSet = true;
        return wasSet;
    }

    public boolean setCorners(ArrayList aCorners)
    {
        boolean wasSet = false;
        corners = aCorners;
        wasSet = true;
        return wasSet;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList getCorners()
    {
        return corners;
    }

    public void delete()
    {
        super.delete();
    }


    public String toString()
    {
        return super.toString() + "["+
                "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
                "  " + "corners" + "=" + (getCorners() != null ? !getCorners().equals(this)  ? getCorners().toString().replaceAll("  ","    ") : "this" : "null");
    }
}