//Prompted ChatGPT to check that the Card.isRed() method would return  
//true for hearts and diamonds and flase for clubs and spades.
//if fail then test fails and show that isRed() isn't working properly to match suit color.
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    void isRed_trueForHeartsAndDiamonds_falseForClubsAndSpades() {
        assertTrue(new Card(Card.Suit.HEARTS).isRed());
        assertTrue(new Card(Card.Suit.DIAMONDS).isRed());
        assertFalse(new Card(Card.Suit.CLUBS).isRed());
        assertFalse(new Card(Card.Suit.SPADES).isRed());
    }
}
