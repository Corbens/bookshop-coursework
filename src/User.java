import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class User {

	private int userId;
	private String username;
	private String surname;
	private int houseNumber;
	private String postcode;
	private String city;
	
	public User(int userId, String username, String surname, int houseNumber, String postcode, String city) {
		this.userId = userId;
		this.username = username;
		this.surname = surname; 
		this.houseNumber = houseNumber;
		this.postcode = postcode;
		this.city = city;
	}

	public int getUserId() {
		return this.userId;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getPostcode() {
		return this.postcode;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getAddress() {
		return this.houseNumber + " " + this.postcode + " " + this.city;
	}
	
	public abstract Role getRole();
	
	public ArrayList<Book> getBooks() throws FileNotFoundException { //returns an arrayList of book objects by reading each line of stock.txt 
		ArrayList<Book> bookList = new ArrayList<Book>();
		File inputFile = new File("Stock.txt"); 
		Scanner fileScanner = new Scanner(inputFile);
		
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] details = line.split(",");
			if(details[1].trim().toLowerCase().equals("paperback")) {
				Paperback book = new Paperback(Integer.parseInt(details[0]), details[2].trim(), Language.getLang(details[3].trim()),
						Genre.getGen(details[4].trim()), details[5].trim(), Double.parseDouble(details[6].trim()), Integer.parseInt(details[7].trim()),
						Integer.parseInt(details[8].trim()), Condition.getCon(details[9].trim()));
				bookList.add(book);
			}else if(details[1].trim().toLowerCase().equals("ebook")) {
				Ebook book = new Ebook(Integer.parseInt(details[0]), details[2].trim(), Language.getLang(details[3].trim()),
						Genre.getGen(details[4].trim()), details[5].trim(), Double.parseDouble(details[6].trim()), Integer.parseInt(details[7].trim()),
						Integer.parseInt(details[8].trim()), EbookFormat.getEbookF(details[9].trim()));
				bookList.add(book);
			}else if(details[1].trim().toLowerCase().equals("audiobook")) {
				Audiobook book = new Audiobook(Integer.parseInt(details[0]), details[2].trim(), Language.getLang(details[3].trim()),
						Genre.getGen(details[4].trim()), details[5].trim(), Double.parseDouble(details[6].trim()), Integer.parseInt(details[7].trim()),
						Double.parseDouble(details[8].trim()), AudioFormat.getAudioF(details[9].trim()));
				bookList.add(book);
			}else {
				System.out.println("An error occurred reading stock.txt");
			}
		}
		fileScanner.close();
		bookList.sort((b1, b2) -> Double.valueOf(b1.getPrice()).compareTo(Double.valueOf(b2.getPrice()))); //sorts the books by price ascending
		return bookList;
	}
	
}
