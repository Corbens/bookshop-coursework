
public class Ebook extends Book {

	private int numberOfPages;
	private EbookFormat format;
	
	public Ebook(int isbn, String title, Language language, Genre genre, String releaseDate, double price,
			int quantity, int numberOfPages, EbookFormat format) {
		super(isbn, title, language, genre, releaseDate, price, quantity);
		this.numberOfPages = numberOfPages;
		this.format = format;
	}
	
	public String getType() {
		return "Ebook";
	}
	
	public int getNumberOfPages() {
		return this.numberOfPages;
	}
	
	public EbookFormat getFormat() {
		return this.format;
	}
}
