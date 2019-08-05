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
    public boardSpot location;

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
    
    public void move(Board b, char dir) {
    	
    	if (dir == 'N') {
    		System.out.println(this.id + " is moving north");
    		b.move(this.getLocation().getX(), this.getLocation().getY(), 0, -1);
    	}
    	if (dir == 'E') {
    		System.out.println(this.id + " is moving east");
    		b.move(this.getLocation().getX(), this.getLocation().getY(), 1, 0);
    		}
    	if (dir == 'S') {
    		System.out.println(this.id + " is moving south");
    		b.move(this.getLocation().getX(), this.getLocation().getY(), 0, 1);
    		}
    	if (dir == 'W') {
    		System.out.println(this.id + " is moving west");
    		b.move(this.getLocation().getX(), this.getLocation().getY(), -1, 0);
    	}
    	
    	
    }


    public String toString()
    {
        return super.toString() + "["+
                "isPlayer" + ":" + getIsPlayer()+ "]";
    }
}