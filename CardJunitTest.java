
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardJunitTest {

	@Test
	void testSetName() {
		Card c = new Card("test");
		String t = "test";
		assertTrue(c.getName() != null);
		
	}
	@Test
	void testGetName() {
		Card c = new Card("test");
		String t = "test";
		assertTrue(c.getName().contentEquals(t));
	}
	@Test
	void testMurderCard() {
		Card c = new Card("test");
		String t = "test";
		c.setIsMurderCard(true);
		assertTrue(c.getIsMurderCard());
	}
	
	void testAddToDeck () {
		Deck d = new Deck();
		Card c = new Card("test");
		d.addCard(c);
		assertTrue(d.getCards().size() > 0);
	}
	
	void testFindMurderInDeck () {
		Deck d = new Deck();
		Card c = new Card("test");
		Card z = new Card("test");
		Card e = new Card("test");
		Card f = new Card("test");
		Card g = new Card("test");
		g.setIsMurderCard(true);
		d.addCard(c);
		d.addCard(z);
		d.addCard(e);
		d.addCard(f);
		d.addCard(g);
		Card h = null;
		for (Card l : d.getCards()) {
			if (l.getIsMurderCard()) {
				h = l;
			}
		}
		
		assertTrue(h.getIsMurderCard());
	}
	

	

}
