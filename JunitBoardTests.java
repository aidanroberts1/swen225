import static org.junit.jupiter.api.Assertions.*;

import java.io.CharConversionException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class JunitBoardTests {

	@Test
	void testBoardArrayListArrayListArrayListGameMain() {
		fail("Not yet implemented");
	}

	@Test
	void testSetWeapons() {
		ArrayList<Weapons> we = new ArrayList<>();
		ArrayList<Room> r = new ArrayList<>();
		ArrayList<Characters> c = new ArrayList<>();
		
		Board b = new Board(we,r,c);
		
		boardSpot bs = new boardSpot(0, 0, false);
		Weapons wep = new Weapons(b,bs,"swoard");
		we.add(wep);
		b.setWeapons(we);
		
		
		assertTrue(b.getWeapons().size() > 0);
		
		
		
	}

	@Test
	void testGetWeapons() {
		fail("Not yet implemented");
	}

}
