import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Basket {
	private ArrayList<Book> contents;
	private ArrayList<Integer> quantities;
	private double value;
	private LocalDate dateObj;
	private Customer basketUser;
	
	public Basket(Customer basketUser) {
		this.contents = new ArrayList<Book>(); 
		this.quantities = new ArrayList<Integer>();
		this.value = 0;
		this.dateObj = LocalDate.now();
		this.basketUser = basketUser;
	}
	
	public void addBook(Book book, int quantity) { //given a book and quantity, adds them to the basket contents and quantities ArrayList's respectively.
		for(int i=0; i<this.contents.size(); i++) { 
			Book currentBook = this.contents.get(i);
			if(currentBook.getIsbn() == book.getIsbn()) { //checks if book is already in basket, if so just updates the quantity of books you put in your basket and the value. 
				Integer newQuantity = this.quantities.get(i) + quantity;
				this.quantities.remove(i);
				this.quantities.add(i, newQuantity);
				this.value = this.value + (book.getPrice() * quantity);
				return;
			}
		} //if book is not already in basket then simply adds the book and quantity to the contents and quantities of the basket.
		this.contents.add(book);
		this.quantities.add(quantity);
		this.value = this.value + (book.getPrice() * quantity);
	}
	
	public double getValue() {
		return this.value;
	}
	
	public ArrayList<Book> getContents(){
		return this.contents;
	}
	
	public ArrayList<Integer> getQuantities(){
		return this.quantities;
	}
	
	public boolean isEmpty() {
		if(this.contents.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	public void emptyBasket() throws IOException { //empties the basket and writes a cancelled log for each book in the basket
		String formattedDate = this.dateObj.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
		for(int i = 0; i<this.contents.size(); i++) {
			Book currentBook = this.contents.get(i);
			String log = this.basketUser.getUserId() + ", " + this.basketUser.getPostcode() + ", " + currentBook.getIsbn() + ", " + currentBook.getPrice() + ", " + this.quantities.get(i) + ", cancelled, , " + formattedDate + "\n";
			this.writeLog(log);
		}
		
		this.contents.clear();
		this.quantities.clear();
		this.value = 0;
	}
	
	public void checkoutBasket(PaymentMethod paymentMethod) throws IOException { //empties the basket and writes a checkout log for each book in the basket
		String formattedDate = this.dateObj.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
		for(int i = 0; i<this.contents.size(); i++) {
			Book currentBook = this.contents.get(i);
			String log = this.basketUser.getUserId() + ", " + this.basketUser.getPostcode() + ", " + currentBook.getIsbn() + ", " + currentBook.getPrice() + ", " + this.quantities.get(i) + ", purchased, " + paymentMethod + ", " + formattedDate + "\n";
			this.writeLog(log);
			
			//the following updates the stock in the stock file, as if the book was checked out, it is no longer in stock
			ArrayList<String> stockLines = new ArrayList<String>();
			File inputFile = new File("Stock.txt");
			Scanner fileScanner = new Scanner(inputFile);
			while(fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				String[] details = line.split(",");
				if(Integer.parseInt(details[0].trim()) == currentBook.getIsbn()) {
					String newLine = line;
					newLine = details[0] + "," + details[1] + "," + details[2] + "," + details[3] + "," + details[4] + "," + details[5] + "," + details[6] + ", "
							 + (Integer.parseInt(details[7].trim()) -this.quantities.get(i)) + "," + details[8] + "," + details[9];
					stockLines.add(newLine);
				}else {
					stockLines.add(line);
				}
			} 
			fileScanner.close();
			FileWriter outputFile = new FileWriter("Stock.txt");
			BufferedWriter bw = new BufferedWriter(outputFile);
			for(String stock : stockLines) {
				bw.write(stock + "\n");
			}
			bw.close();
		}
		
		this.contents.clear();
		this.quantities.clear();
		this.value = 0;
	}
	
	private void writeLog(String newLog) throws IOException { //private so it can only be accessed by the methods above. used to write a log to the activityLog
		ArrayList<String> logLines = new ArrayList<String>();
		File inputFile = new File("ActivityLog.txt");
		Scanner fileScanner = new Scanner(inputFile);
		while(fileScanner.hasNextLine()) { //gets the current logs into an arrayList
			String line = fileScanner.nextLine();
			logLines.add(line);
		}
		fileScanner.close();
		FileWriter outputFile = new FileWriter("ActivityLog.txt");
		BufferedWriter bw = new BufferedWriter(outputFile);
		bw.write(newLog); //writes the newLog to the file ActivityLog
		for(String log : logLines) { //writes the rest of the logs to the ActivityLog
			bw.write(log + "\n");
		}
		bw.close();
	}

}
