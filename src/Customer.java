import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Customer extends User {

	private Role role;

	public Customer(int userId, String username, String surname, int houseNumber, String postcode, String city, Role role) {
		super(userId, username, surname, houseNumber, postcode, city);
		this.role = role;
	}

	public Role getRole() {
		return this.role;
	}
	
	public ArrayList<Book> getSearchList(Boolean checkedGenre, Genre genre, Boolean checkedAudio) throws FileNotFoundException { //given search parameters, will return a book list matching these.
		ArrayList<Book> searchList = new ArrayList<Book>();
		ArrayList<Book> bookList = this.getBooks();
		for(Book book : bookList) {
			if(checkedGenre) {
				if(checkedAudio) {
					if(book instanceof Audiobook) {
						Audiobook abook = (Audiobook) book;
						if(abook.getGenre() == genre && abook.getListeningLength() > 5) {
							searchList.add(book);
						}
					}
				}else {
					if(book.getGenre() == genre) {
						searchList.add(book);
					}
				}
			}else if(checkedAudio) {
				if(book instanceof Audiobook) {
					Audiobook abook = (Audiobook) book;
					if(abook.getListeningLength() > 5) {
						searchList.add(book);
					}
				}
			}
		}
		return searchList;
	}

}
