
public class Paperback extends Book {

	private int numberOfPages;
	private Condition condition;
	
	public Paperback(int isbn, String title, Language language, Genre genre, String releaseDate, double price,
			int quantity, int numberOfPages, Condition condition) {
		super(isbn, title, language, genre, releaseDate, price, quantity);
		this.numberOfPages = numberOfPages;
		this.condition = condition;
	}
	
	public String getType() {
		return "Paperback";
	} 
	
	public int getNumberOfPages() {
		return this.numberOfPages;
	}

	public Condition getCondition() {
		return this.condition;
	}
}
