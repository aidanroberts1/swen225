/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.1.4597.b7ac3a910 modeling language!*/


import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.sound.midi.Soundbank;

// line 25 "model.ump"
// line 98 "model.ump"
public class GameMain
{

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //GameMain Associations
    private Board board;
    private Deck deck;
    private List<Player> players;
    private ArrayList<Player> Players = new ArrayList<>();
    private ArrayList<Characters> PlayableCharcters = new ArrayList();
    private ArrayList<Characters> ActiveCharacters = new ArrayList<>();
    private ArrayList<Room> PlayableRooms = new ArrayList();
    private ArrayList<Weapons> PlayableWeapons = new ArrayList();
    private List<boardSpot> boardSpots;
    private List<BoardEntity> boardEntities;
    
    //ArrayList<Card> inactive;
    //ArrayList<Card> active;
    public int diceone;
    public int dicetwo;
    private int totalMove = 0;
    public  int numpeople;
    Board aBoard = new Board(PlayableWeapons, ActiveCharacters, PlayableRooms, this);
    Boolean gameover = false;
    //Deck aDeck = new Deck(active, inactive, this);

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public GameMain()
    {
        /*
        if (aBoard == null || aBoard.getGameMain() != null)
        {
            throw new RuntimeException("Unable to create GameMain due to aBoard. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        board = aBoard;
        if (aDeck == null || aDeck.getGameMain() != null)
        {
            throw new RuntimeException("Unable to create GameMain due to aDeck. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        */
        //Deck deck;
        players = new ArrayList<Player>();

        GenerateWeapons();
        GenerateRooms();
        GenerateStartCharacters();
        chooseCharacters();
        fillDeck();
        GenerateMurder();
        dealCards();
        for (Characters c : ActiveCharacters) {
        	aBoard.addCharacter(c.getId(), c.getLocation());	//add chars to board
        }
        playGame();


    }
    
    public void playGame() {
    	while (gameover == false) {
    		for (Player p : Players) {
    			takeTurn(p);
    		}
    		
    	}
    	
    }

    public void GenerateMurder(){
        Random MurderRoomNum = new Random();
        Random MurderPlayerNum = new Random();
        Random MurderWeaponNum = new Random();
        int mr = MurderRoomNum.nextInt(10 );
        int mp = MurderPlayerNum.nextInt(ActiveCharacters.size()  );
        int mw = MurderWeaponNum.nextInt(PlayableWeapons.size() );

        ArrayList<String> CharacterNames = new ArrayList<>();
        for (Characters c : ActiveCharacters){
            CharacterNames.add(c.getName());
        }

        ArrayList<Card> activecards = deck.getActive();
        for (Room r : PlayableRooms){
            // get id numbers of rooms and match them
            if (r.getIDnumber() == mr){
                for (Card c : activecards){
                    if (c.getName().equals(r.getName())){
                        c.setIsMurderCard(true);
                    }
                }
            }
        }
        ArrayList<String> weaponnames = new ArrayList<>();
        weaponnames.add("Candlestick");
        weaponnames.add("Dagger");
        weaponnames.add("Lead Pipe");
        weaponnames.add("Revolver");
        weaponnames.add("Rope");
        weaponnames.add("Spanner");

        String nameOfMurderWeapon = weaponnames.get(mw);
        for (Weapons w : PlayableWeapons){
            // get id numbers of rooms and match them
            if (w.getName().equals(nameOfMurderWeapon)){
                for (Card c : activecards){
                    if (c.getName().equals(nameOfMurderWeapon)){
                        c.setIsMurderCard(true);
                    }
                }
            }
        }

        String murderperson = CharacterNames.get(mp);

        for (Card c : activecards){
            if (c.getName().equals(murderperson)){
                c.setIsMurderCard(true);
            }
        }



        System.out.println("murder cards are ");
        for (Card c : activecards){
            if(c.getIsMurderCard() == true){
                System.out.println(c.getName());
            }
        }


    }

    public void takeTurn(Player p){
    	boolean hasMadeAccusation = false;
    	rollDice();
        totalMove = diceone + dicetwo;
        boolean updateMap = true;
        
        
        while (!hasMadeAccusation) {
	    	
        	if (updateMap) {System.out.println(aBoard.printArray()); updateMap = false;}	//update board
	    	System.out.println(p.getName() + " its your turn: \n");
	    	System.out.println("You rolled a " + diceone + " and a "+ dicetwo + " you can move " + totalMove + " tiles\n");
	        System.out.println("What would you like to do?\n\n"
	        					+ "	m) Move\n"
	        					+ "	a) Make an accusation\n"
	        					+ "	s) Show map key\n"
	        					+ "	h) Look at cards you have seen\n");
	        InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	        try {
	            String answer = br.readLine();
	            if (answer.equals("m") || answer.equals("M")){
	            	carryOutMove(p.getCharacter());
	            	updateMap = true;
	            } else if (answer.equals("h") || answer.equals("H")) {
	            	lookAtHand(p);
	            } else if (answer.equals("s") || answer.equals("S")) {
	            	System.out.println(showBoardDetails());
	            	updateMap = true;
	            } else if (answer.equals("a") || answer.equals("A")) {
	            	boardSpot playerLocation = p.getCharacter().getLocation();
	            	//System.out.println("" + "" + );
	            	/*
	            	 * figure out if they are in a room
	            	 */
	            	Room roomPLayerIsIn = null;
	            	char playersRoom = p.getCharacter().getRoom();
	            	System.out.println("PLAYERS ROOM: " + playersRoom);
	            	if (playersRoom == 'K') {roomPLayerIsIn = PlayableRooms.get(0);}
	            	if (playersRoom == 'D') {roomPLayerIsIn = PlayableRooms.get(1);}
	            	if (playersRoom == 'L') {roomPLayerIsIn = PlayableRooms.get(2);}
	            	if (playersRoom == 'H') {roomPLayerIsIn = PlayableRooms.get(3);}
	            	if (playersRoom == 'S') {roomPLayerIsIn = PlayableRooms.get(4);}
	            	if (playersRoom == 'R') {roomPLayerIsIn = PlayableRooms.get(5);}
	            	if (playersRoom == 'I') {roomPLayerIsIn = PlayableRooms.get(6);}
	            	if (playersRoom == 'C') {roomPLayerIsIn = PlayableRooms.get(7);}
	            	if (playersRoom == 'B') {roomPLayerIsIn = PlayableRooms.get(8);}
	            	
	            	if (roomPLayerIsIn == null) {
	            		System.out.println("you cannot make an accusation as you are not in a room");
	            		hasMadeAccusation = false;
	            	} else {
		            	System.out.println("the cards you have seen are ");
		            	for (int i = 0; i < p.getSeen().size(); i++) {
		            		Card m = (Card) p.getSeen().get(i);
		            		System.out.println(m.getName());
		            	}
		            	Iterator<Card> hand = p.getHand().iterator();
		                while(hand.hasNext()){
		                	System.out.println(hand.next().getName());
		                }
		                
		            	/*
		            	 * choose room for accusation
		            	 */
		                
		                System.out.println("the room for your accusation is " + roomPLayerIsIn);
		                
		                
		                Room chosenRoom = roomPLayerIsIn;
		                
		                /*
		                 * choose weapon for accusation
		                 */
		                System.out.println("The weapons are ");
		                int num = 0;
		                
		                for(Weapons w :PlayableWeapons) {
		                	System.out.println(num + " : " + w.getName());
		                	num++;
		                }
		                System.out.println("please choose a weapon for your accusation");
		                int a = 0;
		                answer = br.readLine();
		                a = Integer.parseInt(answer);
		                Weapons chosenWeapon = PlayableWeapons.get(a);
		                
		                /*
		                 * choose character for accusation
		                 */
		                
		                System.out.println("The characters are ");
		                num = 0;
		                
		                for(Characters c :ActiveCharacters) {
		                	if(!p.getCharacter().getName().contentEquals(c.getName())) {
		                		System.out.println(c.getId() + " : " + c.getName());
		                    	num++;                                                    // dont display the option for the palyers own character
		                	}
		                	
		                }
		                System.out.println("please choose a character for your accusation");
		               
		                answer = br.readLine();
		                a = Integer.parseInt(answer);
		                Characters chosenCharacters = ActiveCharacters.get(a-1);
		                
		                System.out.println("your accusation is " + chosenCharacters.getName() + " in the " + chosenRoom.getName() + " with the " + chosenWeapon.getName());
		                makeAccusation(chosenCharacters, chosenRoom, chosenWeapon);
		                Player accused = null;
		                for (Player pa : Players) {
		                	String name = pa.getCharacter().getName();
		                	if (name.contentEquals(chosenCharacters.getName())) {
		                		accused = pa;
		                	}
		                }
		                Card ca = checkOpsHand(p,accused, chosenRoom, chosenCharacters, chosenWeapon );
		                if(ca == null) {
		                	System.out.println("the accused has no cards you havnet already seen");
		                }
		                System.out.println(accused.getName() + " has shown you " + ca.getName());
		                p.getSeen().add(ca);
		                hasMadeAccusation = true;
	            	}
	                
	                
	                
	            }
	        }catch(IOException ioerror){
	            System.out.println("io exception");
	        }
	        System.out.println("");
	    }
        totalMove = 0;
    }
    
    private void carryOutMove(Characters c) {
    	
    	InputStreamReader isr = new InputStreamReader(System.in);
    	BufferedReader br = new BufferedReader(isr);
    	
    	System.out.println("What Direction do you want to move in?\n\n"
				+ "	n) North\n"
				+ "	e) East\n"
				+ "	s) South\n"
				+ "	w) West\n"
				+ "	x) Exit\n");
    	String direction;
		try {
			direction = br.readLine();
			if(direction.equalsIgnoreCase("X")) {return;}
			if(direction.equalsIgnoreCase("N")) {if(c.move(aBoard, 'N')) {totalMove--;}}
	    	if(direction.equalsIgnoreCase("S")) {if(c.move(aBoard, 'S')) {totalMove--;}}
	    	if(direction.equalsIgnoreCase("E")) {if(c.move(aBoard, 'E')) {totalMove--;}}
	    	if(direction.equalsIgnoreCase("W")) {if(c.move(aBoard, 'W')) {totalMove--;}}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
    public String showBoardDetails() {
    	String message = 	"Rooms:			Weapons:\n" + 
    						" - (K)itchen		 - Candlestick\n" + 
    						" - (D)ining Room	 - Dagger\n" + 
    						" - (L)ounge		 - Lead Pipe\n" + 
    						" - (B)all Room		 - Revolver\n" + 
    						" - (C)onservatory	 - Rope\n" + 
    						" - B(I)llard Room	 - Spanner\n" + 
    						" - Lib(R)ary		 \n" + 
    						" - (S)tudy\n" + 
    						" - (H)all\n";
    			
    	return message;
    }
    


	public Card checkOpsHand(Player accusaer, Player accused, Room r, Characters c, Weapons w) {
    	HashSet handofop = accused.getHand();
    	Iterator<Card> hand = accused.getHand().iterator();
        while(hand.hasNext())
        {
        	String name = hand.next().getName();
            if(name.contentEquals(r.getName())) {
            	if (!accusaer.getSeen().contains(hand.next())){
            		return hand.next();
            	}
            	
            }
            
            if(name.contentEquals(c.getName())) {
            	if (!accusaer.getSeen().contains(hand.next())){
            		return hand.next();
            	}
            }
			if(name.contentEquals(w.getName())) {
				if (!accusaer.getSeen().contains(hand.next())){
					return hand.next();
				}
			}
        }
        return null;
    	
    			
    }

    public boolean makeAccusation(Characters a, Room b, Weapons c ){
        int count = 0;
        ArrayList<Card> activecards = deck.getActive();
        for (Card card : activecards){
            if (card.getIsMurderCard()){
                if (card.getName().equals(a.getName())){
                    count++;
                }
                if (card.getName().equals(b.getName())){
                    count++;
                }
                if (card.getName().equals(c.getName())){
                    count++;
                }

            }
        }
        if (count == 3){
        	System.out.println("your accusation was the correct solution ");
        	gameover = true;
            return true;
        }
        System.out.println("your accusation was not the correct soltion");
        return false;

    }

    public void lookAtHand(Player p){
        HashSet h = p.getHand();
        Iterator<Card> hand = p.getHand().iterator();
        System.out.println("Player " + p.getName() + "'s hand has in it ");
        while(hand.hasNext())
        {
            System.out.println(hand.next().getName());
        }
        ArrayList<Card> cardSeen = p.getSeen();
        for (Card c : cardSeen ) {
        	System.out.println(c.getName());
        }
        System.out.println("");

    }




    public void fillDeck(){
        ArrayList<Card> active = new ArrayList<>();
        ArrayList<Card> inactive = new ArrayList<>();
        deck = new Deck(active, inactive, aBoard);
        for (Characters c : ActiveCharacters ){
            String name = c.getName();
            boolean b = false;
            CharacterCard ChCard = new CharacterCard(name, b, deck);
            active.add(ChCard);
        }
        for (Weapons w : PlayableWeapons){
            String name = w.getName();
            boolean b = false;
            CharacterCard WCard = new CharacterCard(name, b, deck);
            active.add(WCard);
        }
        for (Room r : PlayableRooms){
            String name = r.getName();
            boolean b = false;
            CharacterCard RCard = new CharacterCard(name, b, deck);
            active.add(RCard);
        }

        for (Card c : active) {
            //System.out.println(c.getName());
        }

    }

    public void dealCards(){

        ArrayList<Card> activecards = deck.getActive();
        int pos = 0;

        int i = 0;
        for (Player p : Players){
            HashSet<Card> hand = new HashSet<>();
            for (int j = 0; j <  activecards.size(); j++) {
                if ((pos + Players.size()) > activecards.size()){
                    break;
                }
                if(!activecards.get(pos).getIsMurderCard()){
                    hand.add(activecards.get(pos));

                }
                pos  = pos + Players.size();
            }
            p.setHand(hand);
            //System.out.println(p.getHand().size());


            i++;
            pos = i;
        }

    }


    public GameMain(ArrayList aWeaponsForBoard, ArrayList aCharactersForBoard, ArrayList aRoomsForBoard, ArrayList aActiveForDeck, ArrayList aInactiveForDeck)
    {
        board = new Board(aWeaponsForBoard, aCharactersForBoard, aRoomsForBoard, this);
        deck = new Deck(aActiveForDeck, aInactiveForDeck, this);
        players = new ArrayList<Player>();
    }

    public void rollDice (){
        Random diceo = new Random();
        Random dicet = new Random();
        int d1 = diceo.nextInt(7);
        int d2 = dicet.nextInt(7);
        diceone = d1;
        dicetwo = d2;
    }
    public void GenerateWeapons() {
        System.out.println("weapons");
        ArrayList<String> weaponnames = new ArrayList<>();
        weaponnames.add("Candlestick");
        weaponnames.add("Dagger");
        weaponnames.add("Lead Pipe");
        weaponnames.add("Revolver");
        weaponnames.add("Rope");
        weaponnames.add("Spanner");
        Random rand = new Random();
        ArrayList<Integer> roomnums = new ArrayList<>();
        roomnums.addAll(Arrays.asList(0, 1, 2, 3, 4, 5));

        for (int i = 0; i < 6; i++) {
            int p = rand.nextInt(roomnums.size());
            int n = roomnums.get(p);
            String name = weaponnames.get(i);
            Weapons a = new Weapons(aBoard, null, name);
            roomnums.remove(p);
            PlayableWeapons.add(a);
        }
    }
    
    
    public void GenerateRooms(){
        System.out.println("rooms");
        int pos = 0;


        try {
            File file = new File("C:\\Users\\Domin\\eclipse-workspace\\swen-225-joint-v2\\src\\roomData.txt");
            Scanner input = new Scanner(file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            input = new Scanner(file);
            String in = br.readLine();
            if (in.equals("Rooms:")) {
                while (PlayableRooms.size() < 9) {
                    String name = br.readLine();
                    String line = br.readLine();
                    String[] tokens = line.split(",");

                    ArrayList<boardSpot> corners = new ArrayList<>();
                    //name = input.nextLine();
                    int p = 0;
                    int j = p + 1;
                    for (int i = 0; i < 4; i++) {
                        int a = Integer.parseInt(tokens[p]);
                        int b = Integer.parseInt(tokens[j]);
                        boardSpot c = new boardSpot(a, b, true);
                        corners.add(c);
                        p = p + 2;

                        j = p + 1;
                    }
                    line = br.readLine();
                    String na[] = line.split(",");
                    ArrayList<boardSpot> notAvailable = new ArrayList<>();
                    //name = input.nextLine();

                    p = 0;
                    j = p + 1;
                    for (int i = 0; i < na.length / 2; i++) {

                        int a = Integer.parseInt(tokens[p]);
                        int b = Integer.parseInt(tokens[j]);
                        boardSpot c = new boardSpot(a, b, false);
                        notAvailable.add(c);
                        p = p + 2;
                        j = p + 1;
                    }
                    ArrayList<boardSpot> doors = new ArrayList<>();
                    line = br.readLine();
                    String d[] = line.split(",");
                    p = 0;
                    j = p + 1;
                    for (int i = 0; i < d.length / 2; i++) {
                        int a = Integer.parseInt(tokens[p]);
                        int b = Integer.parseInt(tokens[j]);
                        boardSpot c = new boardSpot(a, b, true);
                        doors.add(c);
                        p = p + 2;
                        j = p + 1;
                    }

                   // System.out.println(pos);
                    Room r = new Room(aBoard ,name, corners, notAvailable, doors, PlayableRooms.size());
                    //System.out.println("room number is " + PlayableRooms.size());
                    pos++;
                    PlayableRooms.add(r);
                }

            }

        } catch (IOException sc) {
            System.out.println("ioexception");
        }
        System.out.println("clear");
        for (Room r : PlayableRooms) {
        	/*
            System.out.println(r.getName());
            System.out.println("corners " + r.getCorners().size());
            System.out.println("not avilable " + r.getNotAvilable().size());
            System.out.println("doors " + r.getDoors().size());
            System.out.println(" ");
            */
        }
    }
    public void GenerateStartCharacters(){
        System.out.println("characters");
        /*
        set up stuff that is imutable, create characters and add to a list
         */
        ArrayList<String> characternames = new ArrayList<>();
        characternames.add("Mrs. White");
        characternames.add("Colonel Mustard");
        characternames.add("Miss Scarlett");
        characternames.add("Professor Plum");
        characternames.add("Mrs. Peacock");
        characternames.add("Mr. Green");

        ArrayList<Integer> characterStartLocaations = new ArrayList<>();
        //characterStartLocaations.addAll(Arrays.asList(9,0,0,17,7,24,23,19,23,6,14,0)); old one
        characterStartLocaations.addAll(Arrays.asList(7,4,0,17,7,24,23,19,23,6,14,0));
        int pos = 0;
        for (int i = 0; i < 6 ; i++) {
            String tempname = characternames.get(i);
            Boolean tempBool = false;
            int lx = characterStartLocaations.get(pos);
            int ly = characterStartLocaations.get(pos+1);
            boardSpot b = new boardSpot(lx, ly, false);
            Characters temp = new Characters(aBoard, b,tempname,tempBool, i);
            PlayableCharcters.add(temp);
            pos = pos +2;
        }
        /*
        for (Characters c : PlayableCharcters){
            System.out.println(c.getName());
            System.out.println(c.getLocationX());
            System.out.println(c.getLocationY());
        }
        */
        /*
        do player interaction stuff, eg : choose a player
         */
        Random r = new Random();
        int randomInteger = r.nextInt(6);

    }

    public void chooseCharacters() {
        numpeople = -1;
        try {
            while (numpeople < 0 || numpeople > 7) {
                System.out.println("How many people would like to play ? (max 6 players)");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String answer = br.readLine();
                numpeople = Integer.parseInt(answer);
            }
        } catch (IOException ioerror) {
            System.out.println("io exception");
        }

        try{
            for (int i = 0; i <  numpeople; i++) {
                System.out.println("enter player " + i + "'s  name");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String answer = br.readLine();
                Characters c = null;
                for (Characters x : PlayableCharcters){
                    if (x.getName().equals(answer)){
                        c = x;
                    }
                }
                HashSet h = new HashSet();
                ArrayList s = new ArrayList();
                Player p = new Player(h,s, c,i, this, answer);
                Players.add(p);
            }

        }catch(IOException ioerror){
            System.out.println("io exception");
        }

        Characters player = null;
        System.out.println("num people is " + numpeople);
        int num = 7;
        String confirm = "";
        for (Player p : Players){
            // while (player == null) {
            System.out.println(p.getName() + ", please choose a character to play as by entering the number next to their name ");
            System.out.println("Your options are: \n");
            for (Characters c : PlayableCharcters){
                if (!ActiveCharacters.contains(c)){
                    System.out.println(c.getId() + " : " + c.getName());
                }
            }
            //System.out.println("0 : Mrs. White \n" + "1 : Colonel Mustard \n" + "2 : Miss Scarlett  \n" + "3: Professor Plum \n" + "4 : Mrs. Peacock \n" + "5 : Mr. Green \n");
            try {
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);

                String answer = br.readLine();
                num = Integer.parseInt(answer);

                System.out.println("you have selected " + PlayableCharcters.get(num).getName() + " if this is correct please press y, else press n ");
                confirm = br.readLine();

                if (confirm.equals("y") || confirm.equals("Y")) {
                    player = PlayableCharcters.get(num);
                    PlayableCharcters.get(num).setIsPlayer(true);
                    ActiveCharacters.add(PlayableCharcters.get(num));
                    p.setCharacter(PlayableCharcters.get(num));
                }
                if (player != null) {
                    System.out.println("You have selected your player,  you are " + PlayableCharcters.get(num).getName() + " and your start postion is ( " + 
                PlayableCharcters.get(num).getLocation().getX() + "," + PlayableCharcters.get(num).getLocation().getY() + " )");
                }
            } catch (IOException wronginput) {
                System.out.println("io exception");
            }
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
    /* Code from template association_GetOne */
    public Deck getDeck()
    {
        return deck;
    }
    /* Code from template association_GetMany */
    public Player getPlayer(int index)
    {
        Player aPlayer = players.get(index);
        return aPlayer;
    }

    public List<Player> getPlayers()
    {
        List<Player> newPlayers = Collections.unmodifiableList(players);
        return newPlayers;
    }

    public int numberOfPlayers()
    {
        int number = players.size();
        return number;
    }

    public boolean hasPlayers()
    {
        boolean has = players.size() > 0;
        return has;
    }

    public int indexOfPlayer(Player aPlayer)
    {
        int index = players.indexOf(aPlayer);
        return index;
    }
    /* Code from template association_IsNumberOfValidMethod */
    public boolean isNumberOfPlayersValid()
    {
        boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
        return isValid;
    }
    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfPlayers()
    {
        return 3;
    }
    /* Code from template association_MaximumNumberOfMethod */
    public static int maximumNumberOfPlayers()
    {
        return 6;
    }
    /* Code from template association_AddMNToOnlyOne */
    public Player addPlayer(HashSet aHand, ArrayList aSeen, Characters aCharacter, int aId)
    {
        if (numberOfPlayers() >= maximumNumberOfPlayers())
        {
            return null;
        }
        else
        {
            return new Player(aHand, aSeen, aCharacter, aId, this, null);
        }
    }

    public boolean addPlayer(Player aPlayer)
    {
        boolean wasAdded = false;
        if (players.contains(aPlayer)) { return false; }
        if (numberOfPlayers() >= maximumNumberOfPlayers())
        {
            return wasAdded;
        }

        GameMain existingGameMain = aPlayer.getGameMain();
        boolean isNewGameMain = existingGameMain != null && !this.equals(existingGameMain);

        if (isNewGameMain && existingGameMain.numberOfPlayers() <= minimumNumberOfPlayers())
        {
            return wasAdded;
        }

        if (isNewGameMain)
        {
            aPlayer.setGameMain(this);
        }
        else
        {
            players.add(aPlayer);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removePlayer(Player aPlayer)
    {
        boolean wasRemoved = false;
        //Unable to remove aPlayer, as it must always have a gameMain
        if (this.equals(aPlayer.getGameMain()))
        {
            return wasRemoved;
        }

        //gameMain already at minimum (3)
        if (numberOfPlayers() <= minimumNumberOfPlayers())
        {
            return wasRemoved;
        }
        players.remove(aPlayer);
        wasRemoved = true;
        return wasRemoved;
    }
    /* Code from template association_AddIndexControlFunctions */
    public boolean addPlayerAt(Player aPlayer, int index)
    {
        boolean wasAdded = false;
        if(addPlayer(aPlayer))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
            players.remove(aPlayer);
            players.add(index, aPlayer);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMovePlayerAt(Player aPlayer, int index)
    {
        boolean wasAdded = false;
        if(players.contains(aPlayer))
        {
            if(index < 0 ) { index = 0; }
            if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
            players.remove(aPlayer);
            players.add(index, aPlayer);
            wasAdded = true;
        }
        else
        {
            wasAdded = addPlayerAt(aPlayer, index);
        }
        return wasAdded;
    }

    public void delete()
    {
        Board existingBoard = board;
        board = null;
        if (existingBoard != null)
        {
            existingBoard.delete();
        }
        Deck existingDeck = deck;
        deck = null;
        if (existingDeck != null)
        {
            existingDeck.delete();
        }
        for(int i=players.size(); i > 0; i--)
        {
            Player aPlayer = players.get(i - 1);
            aPlayer.delete();
        }
    }
    public static void main(String[] args) {
        new GameMain();
    }

}