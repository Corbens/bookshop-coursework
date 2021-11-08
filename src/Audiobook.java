
public class Audiobook extends Book {

	private double listeningLength;
	private AudioFormat format;
	
	public Audiobook(int isbn, String title, Language language, Genre genre, String releaseDate, double price, 
			int quantity, double listeningLength, AudioFormat format) {
		super(isbn, title, language, genre, releaseDate, price, quantity);
		this.listeningLength = listeningLength;
		this.format = format;
	}
	
	public String getType() {
		return "Audiobook"; 
	}
	
	public double getListeningLength() {
		return this.listeningLength;
	}
	
	public AudioFormat getFormat() {
		return this.format;
	}
	
}
