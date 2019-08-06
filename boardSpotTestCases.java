import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class boardSpotTestCases {

	@Test
	void testSetName() {
		boardSpot b = new boardSpot(0, 0, true);
		b.setName("d");
		assertTrue(b.getName() != null);
	}

	@Test
	void testGetType() {
		boardSpot b = new boardSpot(0, 0, true);
		b.setType("x");
		assertTrue(b.getType() != null);
	}

	@Test
	void testGetX() {
		boardSpot b = new boardSpot(0, 0, true);
		assertTrue(b.getX() == 0);
	}

	@Test
	void testGetY() {
		boardSpot b = new boardSpot(0, 8, true);
		assertTrue(b.getY() == 8);
	}

	@Test
	void testGetAvailable() {
		boardSpot b = new boardSpot(0, 0, true);
		assertTrue(b.getAvailable() == true);
	}

}
