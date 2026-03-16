public class Card {
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }
    private final Suit suit;

    public Card(Suit suit) {
        this.suit = suit;
    }
    public Suit getSuit() {
        return suit;
    }
    public boolean isRed() {
        return suit == Suit.HEARTS || suit == Suit.DIAMONDS;
    }
}
