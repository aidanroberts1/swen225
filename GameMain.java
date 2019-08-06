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
        players = new ArrayList<Player>();

        System.out.println("=============================== WELCOME TO CLUEDO ===============================");
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
    	while (!gameover) {
    		for (Player p : Players) {
    			if (gameover) {
    				System.out.println("\n\n" + p.getName() + "IS THE WINNER!!");
    				GameIsOver();		//At this point game is over
    				return;
    			}
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
            if (r.getIDnumber() == 8){                // CHNAHE THIS 
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
    }

    public void takeTurn(Player p){
    	boolean hasMadeAccusation = false;
    	rollDice();
        totalMove = diceone + dicetwo;
        boolean updateMap = true;
        boolean endedTurn = false;
        
        
        while (!hasMadeAccusation && !endedTurn) {
        	System.out.println("=============================== " + p.getName() + "'s Turn!" + " ===============================");
	    	
        	if (updateMap) {System.out.println(aBoard.printArray()); updateMap = false;}	//update board
        	
        	String options = "";
	    	System.out.println(p.getName() + " its your turn.\n");
	    	System.out.println("You rolled a " + diceone + " and a "+ dicetwo + " you can move " + totalMove + " tiles\n");
	        System.out.println("What would you like to do?\n");
	        
	        options += "\ts) Show map key\n";
	        options += "\th) Look at cards you have seen\n";
	        if(p.getCharacter().getRoom() == ' ' && totalMove > 0) {	//if not in a room
	        	options += "\tm) Move\n";
	        } else if (p.getCharacter().getRoom() != ' ') {
	        	options += "\ta) Make an accusation\n";
	        	options += "\tl) Leave room\n";
	        }
	        options += "\tx) End turn\n";
	        System.out.println(options);
	        InputStreamReader isr = new InputStreamReader(System.in);
	        BufferedReader br = new BufferedReader(isr);
	        try {
	            String answer = br.readLine();
	            if (answer.equals("m") || answer.equals("M")){			//HANDLE MOVE
	            	carryOutMove(p.getCharacter());
	            	updateMap = true;
	            } else if (answer.equals("h") || answer.equals("H")) {	//HANDLE LOOK AT HAND
	            	lookAtHand(p);
	            } else if (answer.equals("l") || answer.equals("L")) {	//HANDLE LEAVE ROOM
	            	carryOutLeaveRoom(p.getCharacter());
	            	updateMap = true;
	            } else if (answer.equals("x") || answer.equals("X")) {	//HANDLE EXIT
	            	endedTurn = true;
	            } else if (answer.equals("s") || answer.equals("S")) {	//HANDLE SHOW SEEN
	            	System.out.println(showBoardDetails());
	            	updateMap = true;
	            } else if (answer.equals("a") || answer.equals("A")) {	//HANDLE ACCUSATION
	            	
	            	if (carryOutAccusation(p)) {
	            		if (gameover) {
	            			hasMadeAccusation = true;
	            			return; 
	            		}
	            		hasMadeAccusation = true;
	            		
	            	} else {
	            		hasMadeAccusation = false;
	            	}
	            
	            	
	                
	                
	            }
	        }catch(IOException ioerror){
	            System.out.println("io exception");
	        }
	        System.out.println("");
	    }
        totalMove = 0;
    }
    
    private boolean carryOutAccusation(Player p) {
    	 InputStreamReader isr = new InputStreamReader(System.in);
	     BufferedReader br = new BufferedReader(isr);
    	try { 
    		
    	String answer = " ";
    	boardSpot playerLocation = p.getCharacter().getLocation();

    	/*
    	 * figure out if they are in a room
    	 */
    	Room roomPLayerIsIn = null;
    	char playersRoom = p.getCharacter().getRoom();
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
    		return false;
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
            
            System.out.println("the room for your accusation is " + roomPLayerIsIn.getName());
            
            
            Room chosenRoom = roomPLayerIsIn;
            
            /*
             * choose weapon for accusation
             */
            System.out.println("The weapons are: ");
            int num = 0;
            
            for(Weapons w :PlayableWeapons) {
            	System.out.println(num + " : " + w.getName());
            	num++;
            }
            System.out.println("Please select a weapon for your accusation by entering the given number.");
            int a = 0;
            answer = br.readLine();
            try { 
            	a = Integer.parseInt(answer);
            }catch (NumberFormatException wronginput) {
                System.out.println("please enter a number NOT letters or text.\n");
            }
            Weapons chosenWeapon = PlayableWeapons.get(a);
            
            /*
             * choose character for accusation
             */
            
            System.out.println("The other players are: ");
            num = 0;
            for (Player player : Players) {
            	String playerName = p.getName();
            	String thisPlayerName = player.getName();
            	if(!playerName.equals(thisPlayerName)) {
            		System.out.println(player.getId() + " : " + player.getName());
            	}
            }
            
            System.out.println("Please select a player for your accusation by entering the given number.\n");
           
            answer = br.readLine();
            
           try {
            	a = Integer.parseInt(answer);
            }catch (NumberFormatException wronginput) {
                System.out.println("please enter a number NOT letters or text ");
            }
            Player  playerofselected = Players.get(a);
            
            System.out.println("Now please choose a character to suggest\n");
            
            for (Characters chars : ActiveCharacters) {
            	System.out.println(chars.getId() + " : " + chars.getName());
            }
            answer = br.readLine();
            a = Integer.parseInt(answer);
            Characters chosenCharacters = ActiveCharacters.get(a);
            
            System.out.println("Your accusation is " + chosenCharacters.getName() + " in the " + chosenRoom.getName() + " with the " + chosenWeapon.getName());
            if (makeAccusation(chosenCharacters, chosenRoom, chosenWeapon)) {
            	return true; 
            }
            
            
            
            
            
            
            
            Player accused = null;
            for (Player pa : Players) {
            	String name = pa.getCharacter().getName();
            	if (name.contentEquals(chosenCharacters.getName())) {
            		accused = pa;
            	}
            }
            accused = playerofselected;
            Card ca = checkOpsHand(p, accused, chosenRoom, chosenCharacters, chosenWeapon );
            if(ca == null) {
            	System.out.println("The accused has no cards you havnet already seen.\n");
            } else {
	            System.out.println(accused.getName() + " has shown you " + ca.getName() + "\n");
	            p.getSeen().add(ca);
            }
            return true;
    	}
    	}catch (IOException ioerror) {
    		System.out.println("io error");
    	}
		return false;
    	
    }
    
   
    
    private void carryOutLeaveRoom(Characters c) {
    	
    	InputStreamReader isr = new InputStreamReader(System.in);
    	BufferedReader br = new BufferedReader(isr);
    	
    	char playersRoom = c.getRoom();
    	Room rm = null;
    	
    	if (playersRoom == 'K') {rm = PlayableRooms.get(0);}	//find room
    	if (playersRoom == 'D') {rm = PlayableRooms.get(1);}
    	if (playersRoom == 'L') {rm = PlayableRooms.get(2);}
    	if (playersRoom == 'H') {rm = PlayableRooms.get(3);}
    	if (playersRoom == 'S') {rm = PlayableRooms.get(4);}
    	if (playersRoom == 'R') {rm = PlayableRooms.get(5);}
    	if (playersRoom == 'I') {rm = PlayableRooms.get(6);}
    	if (playersRoom == 'C') {rm = PlayableRooms.get(7);}
    	if (playersRoom == 'B') {rm = PlayableRooms.get(8);}
    	
    	System.out.println("What Door do you want to leave through?\n");
    	
    	ArrayList<boardSpot> roomDoors = rm.getDoors();
    	
    	int count = 0;
    	for (boardSpot bs : roomDoors) {
    		System.out.println("\t" + count++ + ") The door with the with X = " + bs.getX() + ", Y = " + bs.getY());
    	}
    	System.out.println("");
    	
    	String answer = "";
    	try {
			answer = br.readLine();
			aBoard.leaveRoom((char) (c.getId()+'0'), roomDoors.get(Integer.parseInt(answer)));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
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
        	Card cardtest = hand.next();
            if(name.contentEquals(r.getName())) {
            	if (!(accusaer.getSeen().contains(cardtest))){
            		return hand.next();
            	}
            	
            }
            
            if(name.contentEquals(c.getName())) {
            	if (!(accusaer.getSeen().contains(cardtest))){
            		return hand.next();
            	}
            }
			if(name.contentEquals(w.getName())) {
				if (!(accusaer.getSeen().contains(cardtest))){
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
        	System.out.println("Your accusation was the correct solution!\n");
        	gameover = true;
        	
            return true;
        }
        System.out.println("Your accusation was NOT the correct solution!\n");
        return false;

    }
    
    public void GameIsOver() {
    	System.out.println("=========================== GAME OVER! CRIME HAS BEEN SOLVED! ===========================");
    }

    public void lookAtHand(Player p){
        HashSet h = p.getHand();
        Iterator<Card> hand = p.getHand().iterator();
        System.out.println("Player " + p.getName() + "'s list of seen cards has in it:\n");
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
                        //doors.add(c);
                        p = p + 2;
                        j = p + 1;
                    }
                    
                    Room r = new Room(aBoard ,name, corners, notAvailable, doors, PlayableRooms.size());
                    pos++;
                    PlayableRooms.add(r);
                }

            }

        } catch (IOException sc) {
            System.out.println("ioexception");
        }
        overwriteRoomDoors();
    }
    
    public void GenerateStartCharacters(){
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

        /**
         * do player interaction mechanics, eg: choose a player
         */
        Random r = new Random();
        int randomInteger = r.nextInt(6);

    }

    public void chooseCharacters() {
        numpeople = -1;
        try {
            while (numpeople < 3 || numpeople > 7) {
                System.out.println("How many people would like to play ? (min 3 players : max 6 players)\n");
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);
                String answer = br.readLine();
                try {
                	numpeople = Integer.parseInt(answer);
                }catch (NumberFormatException wronginput) {
                    System.out.println("please enter a number NOT letters or text!");
                }
            }
        } catch (IOException ioerror) {
            System.out.println("io exception");
        }

        try{
            for (int i = 0; i <  numpeople; i++) {
                System.out.println("\nenter player " + i + "'s  name\n");
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
        
        int num = 7;
        String confirm = "";
        for (Player p : Players){
            System.out.println("\n" + p.getName() + ", please choose a character to play as by entering the number next to their name.\n");
            System.out.println("Your options are: \n");
            for (Characters c : PlayableCharcters){
                if (!ActiveCharacters.contains(c)){
                    System.out.println(c.getId() + " : " + c.getName());
                }
            }
            
            try {
                InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(isr);

                String answer = br.readLine();
                try {
                	num = Integer.parseInt(answer);
                }catch (NumberFormatException wronginput) {
                    System.out.println("please enter a number NOT letters or text!\n");
                }

                System.out.println("You have selected " + PlayableCharcters.get(num).getName() + " if this is correct please press y, else press n.\n");
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
    
    private void overwriteRoomDoors() {
    	ArrayList<boardSpot> bsArray = new ArrayList<boardSpot>();
    	
    	boardSpot b = new boardSpot(4, 6, true);
    	bsArray.add(b);
    	PlayableRooms.get(0).getDoors().add(b);	//0
    	bsArray.clear();
    	
    	b = new boardSpot(7, 12, true);
    	bsArray.add(b);
    	PlayableRooms.get(1).getDoors().add(b);
    	b = new boardSpot(6, 15, true);
    	bsArray.add(b);
    	PlayableRooms.get(1).getDoors().add(b);	//1
    	bsArray.clear();
    	
    	b = new boardSpot(6, 19, true);
    	bsArray.add(b);
    	PlayableRooms.get(2).getDoors().add(b);	//2
    	bsArray.clear();
    	
    	b = new boardSpot(11, 18, true);
    	bsArray.add(b);
    	PlayableRooms.get(3).getDoors().add(b);
    	b = new boardSpot(12, 18, true);
    	bsArray.add(b);
    	PlayableRooms.get(3).getDoors().add(b);
    	b = new boardSpot(14, 20, true);
    	bsArray.add(b);
    	PlayableRooms.get(3).getDoors().add(b); //3
    	bsArray.clear();
    	
    	b = new boardSpot(17, 21, true);
    	bsArray.add(b);
    	PlayableRooms.get(4).getDoors().add(b);	//4
    	bsArray.clear();
    	
    	b = new boardSpot(17, 16, true);
    	bsArray.add(b);
    	PlayableRooms.get(5).getDoors().add(b);
    	b = new boardSpot(20, 14, true);
    	bsArray.add(b);
    	PlayableRooms.get(5).getDoors().add(b); //5
    	bsArray.clear();
    	
    	b = new boardSpot(18, 9, true);
    	bsArray.add(b);
    	PlayableRooms.get(6).getDoors().add(b);
    	b = new boardSpot(22, 12, true);
    	bsArray.add(b);
    	PlayableRooms.get(6).getDoors().add(b);	//6
    	bsArray.clear();
    	
    	b = new boardSpot(18, 5, true);
    	bsArray.add(b);
    	PlayableRooms.get(7).getDoors().add(b);	//7
    	bsArray.clear();
    	
    	b = new boardSpot(8, 5, true);
    	bsArray.add(b);
    	PlayableRooms.get(8).getDoors().add(b);
    	b = new boardSpot(10, 7, true);
    	bsArray.add(b);
    	PlayableRooms.get(8).getDoors().add(b);
    	b = new boardSpot(15, 7, true);
    	bsArray.add(b);
    	PlayableRooms.get(8).getDoors().add(b);
    	b = new boardSpot(16, 5, true);
    	bsArray.add(b);
    	PlayableRooms.get(8).getDoors().add(b);	//8
    	bsArray.clear();
    	
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