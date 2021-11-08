
public abstract class Book {
	
	private int isbn;
	private String title;
	private Language language;
	private Genre genre;
	private String releaseDate;
	private double price;
	private int quantity;
	
	public Book(int isbn, String title, Language language, Genre genre, String releaseDate, double price, int quantity) {
		this.isbn = isbn;
		this.title = title;
		this.language = language;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.price = price;
		this.quantity = quantity;
	}
	
	public int getIsbn() {
		return this.isbn;
	}
	
	public abstract String getType();
	
	public String getTitle() {
		return this.title;
	}
	
	public Language getLang() {
		return this.language;
	}
	
	public Genre getGenre() {
		return this.genre;
	}
	
	public String getReleaseDate() {
		return this.releaseDate;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void updateQuantity(int newQuantity) {
		this.quantity = newQuantity;
	}
	
}
