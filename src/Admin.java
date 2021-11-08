import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Admin extends User {
	
	private Role role;

	public Admin(int userId, String username, String surname, int houseNumber, String postcode, String city, Role role) {
		super(userId, username, surname, houseNumber, postcode, city);
		this.role = role;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	//This either adds a new book to stock, or adds extra books to a book already in stock dependent on whether or not the book passed as a parameter matches an ISBN with a book in stock.txt
	//Also returns a boolean based on this so wherever this was called from can determine what action happened.
	public boolean addBook(Book book) throws IOException {
		File inputFile = new File("Stock.txt");
		Scanner fileScanner = new Scanner(inputFile);
		boolean found = false;
		
		while(fileScanner.hasNextLine() && found == false) { 
			String line = fileScanner.nextLine();
			String[] details = line.split(",");
			if(Integer.parseInt(details[0].trim()) == book.getIsbn()) {
				ArrayList<String> stockLines = new ArrayList<String>();
				File inputFile2 = new File("Stock.txt");
				Scanner fileScanner2 = new Scanner(inputFile2);
				while(fileScanner2.hasNextLine()) {
					String line2 = fileScanner2.nextLine();
					String[] details2 = line2.split(",");
					if(Integer.parseInt(details2[0].trim()) == book.getIsbn()) { //looks for if there's a book in stock that matches the book passed as a parameter
						String newLine = line2;
						newLine = details2[0] + "," + details2[1] + "," + details2[2] + "," + details2[3] + "," + details2[4] + "," + details2[5] + "," + details2[6] + ", "
								 + (Integer.parseInt(details2[7].trim()) + book.getQuantity()) + "," + details2[8] + "," + details2[9]; //updates the quantity of the book in stock
						stockLines.add(newLine);
					}else {
						stockLines.add(line2);
					}
				} 
				fileScanner2.close();
				FileWriter outputFile = new FileWriter("Stock.txt");
				BufferedWriter bw = new BufferedWriter(outputFile);
				for(String stock : stockLines) { 
					bw.write(stock + "\n"); 
				}
				bw.close();
				found = true;
			}
		} 
		fileScanner.close();
		
		if(found==false) { //if book wasn't found, adds a brand new entry to stock
			String bookLine = "";
			if(book instanceof Paperback) {
				Paperback pbook = (Paperback) book;
				bookLine = pbook.getIsbn() + ", " + pbook.getType().toLowerCase() + ", " + pbook.getTitle() + ", " + pbook.getLang().toString() + ", " + pbook.getGenre().toString() + ", " +
				pbook.getReleaseDate() + ", " + pbook.getPrice() + ", " + pbook.getQuantity() + ", " + pbook.getNumberOfPages() + ", " + pbook.getCondition().toString() + "\n";
			}else if(book instanceof Ebook){
				Ebook ebook = (Ebook) book;
				bookLine = ebook.getIsbn() + ", " + ebook.getType().toLowerCase() + ", " + ebook.getTitle() + ", " + ebook.getLang().toString() + ", " + ebook.getGenre().toString() + ", " +
				ebook.getReleaseDate() + ", " + ebook.getPrice() + ", " + ebook.getQuantity() + ", " + ebook.getNumberOfPages() + ", " + ebook.getFormat() + "\n";
			}else if(book instanceof Audiobook){
				Audiobook abook = (Audiobook) book;
				bookLine = abook.getIsbn() + ", " + abook.getType().toLowerCase() + ", " + abook.getTitle() + ", " + abook.getLang().toString() + ", " + abook.getGenre().toString() + ", " +
				abook.getReleaseDate() + ", " + abook.getPrice() + ", " + abook.getQuantity() + ", " + abook.getListeningLength() + ", " + abook.getFormat() + "\n";
			}
			FileWriter outputFile = new FileWriter("Stock.txt",true);
			BufferedWriter bw = new BufferedWriter(outputFile);
			bw.write(bookLine);
			bw.close();
		}
		
		return found;
	}

}
