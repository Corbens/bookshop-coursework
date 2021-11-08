import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private ArrayList<User> userList;
	private ArrayList<Book> bookList;
	private User loggedUser;
	private int row;
	//login
	private JPanel loginPanel;
	private JScrollPane userScrollPane;
	private JTable tblUsers;
	private DefaultTableModel dtmUsers;
	private JLabel lblLoginWelcome;
	private JLabel lblCurrentSelectUser;
	private JLabel lblLoginMessage;
	private JLabel lblLoginHelp;
	private JLabel lblHelp1;
	private JLabel lblHelp2;
	private JLabel lblHelp3;
	private JLabel lblHelp4;
	private JButton btnLogin;
	private String currentUserSelect;
	//view
	private JPanel viewPanel;
	private JScrollPane booksScrollPane;
	private JScrollPane basketScrollPane;
	private JTable tblBooks;
	private JTable tblBasket;
	private DefaultTableModel dtmBooks;
	private DefaultTableModel dtmBasket;
	private JLabel lblSelectedBook;
	private JLabel lblBasketOverview;
	private JLabel lblSelectQuantity;
	private JLabel lblPaymentMethod;
	private JLabel lblPaymentHelp;
	private JLabel lblPaymentAmount;
	private JLabel lblStatusMessage;
	private JSpinner spinnerQuantity;
	private JRadioButton paypalRButton;
	private JRadioButton creditcardRButton;
	private ButtonGroup paymentRButtons;	
	private JTextField txtSampleEmail;
	private JTextField txtDigitCard;
	private JTextField txtDigitSecurity;
	private JButton btnAddToBasket;
	private JButton btnCheckoutBasket;
	private JButton btnEmptyBasket;
	private JButton btnCreateBasket;
	private String currentBookSelect;
	private String currentISBN;
	private Boolean createdBasket;
	private Basket basket;
	private Book bookToAdd;
	//search
	private JPanel searchPanel;
	private JScrollPane searchScrollPane;
	private JTable tblSearchResults;
	private DefaultTableModel dtmSearch;
	private JLabel lblSearchFor;
	private JLabel lblAndOr;
	private JLabel lblSearchInfo;
	private JCheckBox genreTickBox;
	private JCheckBox audioTickBox;
	private JRadioButton politicsRButton;
	private JRadioButton medicineRButton;
	private JRadioButton businessRButton;
	private JRadioButton computerscienceRButton;
	private JRadioButton biographyRButton;
	private ButtonGroup genreRButtons;
	private JButton btnSearch;
	private ArrayList<Book> searchList;
	private Genre searchGenre;
	//add
	private JPanel addPanel;
	private JLabel lblAddMessage;
	private JLabel lblBookIsbn;
	private JLabel lblBookIsbnConstraint;
	private JLabel lblBookTitle;
	private JLabel lblBookLanguage;
	private JLabel lblBookGenre;
	private JLabel lblBookReleasedate;
	private JLabel lblBookReleasedateConstraint;
	private JLabel lblBookPrice;
	private JLabel lblBookQuantity;
	private JLabel lblBookType;
	private JLabel lblPaperPages;
	private JLabel lblPaperCondition;
	private JLabel lblEbookPages;
	private JLabel lblEbookFormat;
	private JLabel lblAudioListeninglength;
	private JLabel lblAudioFormat;
	private JLabel lblAddHelp1;
	private JLabel lblAddHelp2;
	private JLabel lblAddHelp3;
	private JLabel lblAddStatus;
	private JSpinner spinnerBookQuantity;
	private JSpinner spinnerPaperPages;
	private JSpinner spinnerEbookPages;
	private JRadioButton rdbtnAddPaperback;
	private JRadioButton rdbtnAddEbook;
	private JRadioButton rdbtnAddAudiobook;
	private ButtonGroup booktypeRButtons;
	private JTextField textBookIsbn;
	private JTextField textBookTitle;
	private JTextField textBookPrice;
	private JTextField textListeninglength;
	private JTextField textBookReleasedate;
	private JComboBox<Genre> comboBoxBookGenre;
	private JComboBox<Language> comboBoxBookLanguage;
	private JComboBox<Condition> comboBoxPaperCondition;
	private JComboBox<EbookFormat> comboBoxEbookFormat;
	private JComboBox<AudioFormat> comboBoxAudiobookFormat;
	private JButton btnAddBook;
	

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws FileNotFoundException 
	 */
	public MainFrame() throws FileNotFoundException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 610);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 864, 549);
		contentPane.add(tabbedPane);
		
		/*although below might look messy to begin with, I've grouped everything into tabs, and within these tabs, all labels are together, all buttons are together,
		 *and etc so hopefully it will be easy to understand and easy to find things once you get used to it.*/
		
		//-----------------------------------------------------------------------//
		//---------------------------start of login tab--------------------------//
		//-----------------------------------------------------------------------//
		loginPanel = new JPanel();
		tabbedPane.addTab("Login", null, loginPanel, null);
		loginPanel.setLayout(null);
		
		userList = new ArrayList<User>();
		File inputFile = new File("UserAccounts.txt");
		Scanner fileScanner = new Scanner(inputFile);
		while(fileScanner.hasNextLine()) { //fills the array userList with users from the text file UserAccounts
			String line = fileScanner.nextLine();
			String[] details = line.split(",");
			if(details[6].trim().equals("admin")) {
				Admin user = new Admin(Integer.parseInt(details[0].trim()), details[1].trim(), details[2].trim(), Integer.parseInt(details[3].trim()),
					details[4].trim(), details[5].trim(), Role.getRole(details[6].trim()));
				userList.add(user);
			}else if(details[6].trim().equals("customer")) {
				Customer user = new Customer(Integer.parseInt(details[0].trim()), details[1].trim(), details[2].trim(), Integer.parseInt(details[3].trim()),
					details[4].trim(), details[5].trim(), Role.getRole(details[6].trim()));
				userList.add(user);
			}
		}
		fileScanner.close();
		
		userScrollPane = new JScrollPane();
		userScrollPane.setBounds(10, 94, 839, 100);
		loginPanel.add(userScrollPane);
		tblUsers = new JTable();
		userScrollPane.setViewportView(tblUsers);
		dtmUsers = new DefaultTableModel();
		dtmUsers.setColumnIdentifiers(new Object[] {"UserID","Username","City","Role"}); //this creates the headers for the user table
		tblUsers.setModel(dtmUsers);
		tblUsers.addMouseListener(new MouseListener() {
			@Override
			public void mousePressed(MouseEvent e) { //this is run if a user is pressed in the table and will display what user is selected
				row = tblUsers.getSelectedRow();
				currentUserSelect = tblUsers.getModel().getValueAt(row, 1).toString();
				lblCurrentSelectUser.setText("Currently Selected: " + currentUserSelect);
			}
			@Override
			public void mouseClicked(MouseEvent e) {				
			}
			@Override
			public void mouseReleased(MouseEvent e) {				
			}
			@Override
			public void mouseEntered(MouseEvent e) {				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		
		lblLoginWelcome = new JLabel("Welcome To The Bookshop Management System");
		lblLoginWelcome.setBounds(20, 19, 370, 14);
		loginPanel.add(lblLoginWelcome);
		
		lblCurrentSelectUser = new JLabel("Currently Selected: ");
		lblCurrentSelectUser.setBounds(20, 69, 370, 14);
		loginPanel.add(lblCurrentSelectUser);
		
		lblLoginMessage = new JLabel("Select a user from the table below");
		lblLoginMessage.setBounds(20, 44, 370, 14);
		loginPanel.add(lblLoginMessage);
		for (User user : userList) { //this fills in the user table
			Object[] rowdata = new Object[] {user.getUserId(),user.getUsername(),user.getCity(),user.getRole()};
			dtmUsers.addRow(rowdata);
		}
		
		lblLoginHelp = new JLabel("General Information:");
		lblLoginHelp.setBounds(20, 215, 370, 14);
		loginPanel.add(lblLoginHelp);
		
		lblHelp1 = new JLabel("If you see an E1 next to a book, this means either number of pages (if paperback or ebook) or listening length in hours (if audiobook)");
		lblHelp1.setBounds(20, 240, 819, 14);
		loginPanel.add(lblHelp1);
		
		lblHelp2 = new JLabel("If you see an E2 next to a book, this means either condition (if paperback) or format (if ebook or audiobook)");
		lblHelp2.setBounds(20, 265, 819, 14);
		loginPanel.add(lblHelp2);
		
		lblHelp3 = new JLabel("Only an Admin can add a book to stock");
		lblHelp3.setBounds(20, 290, 819, 14);
		loginPanel.add(lblHelp3);
		
		lblHelp4 = new JLabel("Only a Customer can create and use a basket");
		lblHelp4.setBounds(20, 315, 819, 14);
		loginPanel.add(lblHelp4);

		btnLogin = new JButton("Login!");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //logs in the user as whoever was selected in the table
				for (User user : userList) {
					if(user.getUsername().equals(currentUserSelect)) {
						loggedUser = user;
						if(loggedUser instanceof Customer) { //determines what pages a customer can access
							tabbedPane.setEnabledAt(1, true);
							tabbedPane.setEnabledAt(2, true);
							tabbedPane.setEnabledAt(3, false);
						}else if(loggedUser instanceof Admin) { //determines what pages an admin can access
							tabbedPane.setEnabledAt(1, true);
							tabbedPane.setEnabledAt(2, false);
							tabbedPane.setEnabledAt(3, true);
						}
						dtmSearch.setRowCount(0); //clears the search table from any previous user's searches
						try { //updates the book viewing table
							bookList = loggedUser.getBooks();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						updateBookTable();
						break;
					}
				}
				lblLoginMessage.setText("You are logged in as: " + loggedUser.getUsername());
				//everything to do with the basket is initially set to invisible, so that way you can only see the basket stuff when you click create basket
				basketScrollPane.setVisible(false);
				lblBasketOverview.setText("Only Customers Can Create A Basket");
				lblSelectedBook.setVisible(false); 
				lblSelectQuantity.setVisible(false);
				lblPaymentMethod.setVisible(false);
				lblPaymentHelp.setVisible(false);
				lblPaymentAmount.setVisible(false);
				lblStatusMessage.setVisible(false);
				spinnerQuantity.setVisible(false);
				paypalRButton.setVisible(false);
				creditcardRButton.setVisible(false);
				txtSampleEmail.setVisible(false);
				txtDigitCard.setVisible(false);
				txtDigitSecurity.setVisible(false);
				btnAddToBasket.setVisible(false);
				btnCheckoutBasket.setVisible(false);
				btnEmptyBasket.setVisible(false);
				createdBasket = false;
			}
		});
		btnLogin.setBounds(727, 60, 112, 23);
		loginPanel.add(btnLogin);
		//----------------------------end of login tab---------------------------//
		
		
		
		//-----------------------------------------------------------------------//
		//---------------------------start of view tab---------------------------//
		//-----------------------------------------------------------------------//
		viewPanel = new JPanel();
		tabbedPane.addTab("View Books & Basket", null, viewPanel, null);
		viewPanel.setLayout(null);
		
		booksScrollPane = new JScrollPane();
		booksScrollPane.setBounds(10, 11, 839, 220);
		viewPanel.add(booksScrollPane);
		dtmBooks = new DefaultTableModel();
		dtmBooks.setColumnIdentifiers(new Object[] {"ISBN","Type","Title","Language","Genre","Release Date","Price £","Quantity","E1","E2"}); //creates the headers for the book table
		tblBooks = new JTable();
		booksScrollPane.setViewportView(tblBooks);
		tblBooks.setModel(dtmBooks);
		tblBooks.addMouseListener(new MouseListener() { //if the user presses on a book, and has created a basket, then it will display what book is selected
			@Override
			public void mousePressed(MouseEvent e) {
				if(createdBasket) {
					row = tblBooks.getSelectedRow();
					currentBookSelect = tblBooks.getModel().getValueAt(row, 2).toString();
					lblSelectedBook.setText("Currently Selected Book: " + currentBookSelect);
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {				
			}
			@Override
			public void mouseReleased(MouseEvent e) {				
			}
			@Override
			public void mouseEntered(MouseEvent e) {				
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		updateBookTable(); 
		
		basketScrollPane = new JScrollPane();
		basketScrollPane.setBounds(432, 242, 417, 258);
		viewPanel.add(basketScrollPane);
		dtmBasket = new DefaultTableModel();
		dtmBasket.setColumnIdentifiers(new Object[] {"ISBN","Type","Title","Quantity","Total Price £"}); //creates the headers for the basket table
		tblBasket = new JTable();
		basketScrollPane.setViewportView(tblBasket);
		tblBasket.setModel(dtmBasket);
		
		lblBasketOverview = new JLabel("Only Customers Can Create A Basket");
		lblBasketOverview.setBounds(170, 243, 252, 19);
		viewPanel.add(lblBasketOverview);
		lblSelectedBook = new JLabel("Currently Selected Book: ");
		lblSelectedBook.setBounds(10, 273, 412, 14);
		viewPanel.add(lblSelectedBook);
		
		lblSelectQuantity = new JLabel("Select Quantity");
		lblSelectQuantity.setBounds(10, 302, 100, 14);
		viewPanel.add(lblSelectQuantity);
		
		lblPaymentMethod = new JLabel("Choose Payment Method");
		lblPaymentMethod.setBounds(10, 332, 150, 14);
		viewPanel.add(lblPaymentMethod);
		
		lblPaymentHelp = new JLabel("Please Enter A Valid Email Address");
		lblPaymentHelp.setBounds(10, 411, 412, 14);
		viewPanel.add(lblPaymentHelp);
		
		lblPaymentAmount = new JLabel("£0.0 to be paid using PayPal");
		lblPaymentAmount.setBounds(10, 436, 412, 14);
		viewPanel.add(lblPaymentAmount);
		
		lblStatusMessage = new JLabel("");
		lblStatusMessage.setBounds(10, 495, 412, 14);
		viewPanel.add(lblStatusMessage);
		
		spinnerQuantity = new JSpinner();
		spinnerQuantity.setBounds(116, 299, 44, 21);
		viewPanel.add(spinnerQuantity);
		
		paypalRButton = new JRadioButton("PayPal ");
		paypalRButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPaymentAmount.setText("£" + basket.getValue() + " to be paid using PayPal");
				lblPaymentHelp.setText("Please Enter A Valid Email Address");
			}
		});
		paypalRButton.setBounds(10, 355, 100, 23);
		viewPanel.add(paypalRButton);
		paypalRButton.setSelected(true);
		
		creditcardRButton = new JRadioButton("Credit Card ");
		creditcardRButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPaymentAmount.setText("£" + basket.getValue() + " to be paid using Credit Card");
				lblPaymentHelp.setText("Please Enter 6 Digit Card No And 3 Digit Security Code");
			}
		});
		creditcardRButton.setBounds(10, 381, 100, 23);
		viewPanel.add(creditcardRButton);
		paymentRButtons = new ButtonGroup();
		paymentRButtons.add(paypalRButton);
		paymentRButtons.add(creditcardRButton);
		
		txtSampleEmail = new JTextField();
		txtSampleEmail.setText("bookshop@bookstore.co.uk");
		txtSampleEmail.setBounds(116, 356, 306, 20);
		viewPanel.add(txtSampleEmail);
		txtSampleEmail.setColumns(10);
		
		txtDigitCard = new JTextField();
		txtDigitCard.setText("123456");
		txtDigitCard.setBounds(116, 382, 153, 20);
		viewPanel.add(txtDigitCard);
		txtDigitCard.setColumns(10);
		
		txtDigitSecurity = new JTextField();
		txtDigitSecurity.setText("789");
		txtDigitSecurity.setBounds(279, 382, 143, 20);
		viewPanel.add(txtDigitSecurity);
		txtDigitSecurity.setColumns(10);
		
		btnAddToBasket = new JButton("Add To Basket");
		btnAddToBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //adds a book to the customers basket
				currentISBN = tblBooks.getModel().getValueAt(row, 0).toString();
				for(Book book : bookList) {
					if(Integer.toString(book.getIsbn()).equals(currentISBN)) {
						Integer currentQuantity = (Integer) spinnerQuantity.getValue();
						if(book.getQuantity() < 1) {
							lblStatusMessage.setText("Status: This Book Is Out Of Stock");
							break;
						}
						if((currentQuantity > book.getQuantity()) || (currentQuantity < 1)) {
							lblStatusMessage.setText("Status: Please Select A Valid Quantity");
							break;
						}
						lblStatusMessage.setText("Status: Added to Basket");
						basket.addBook(book, currentQuantity); //adds the book to the basket object 
						updateBasketTable(); //refreshes the basket table
						book.updateQuantity(book.getQuantity()-currentQuantity); //updates the book table data
						updateBookTable(); //refreshes the book table
						if(paypalRButton.isSelected()) {
							lblPaymentAmount.setText("£" + basket.getValue() + " to be paid using PayPal");
						}else if(creditcardRButton.isSelected()) { 
							lblPaymentAmount.setText("£" + basket.getValue() + " to be paid using Credit Card");
						}
						break;
					}
				}
			}
		});
		btnAddToBasket.setBounds(170, 298, 150, 23);
		viewPanel.add(btnAddToBasket);
		
		btnCheckoutBasket = new JButton("Checkout Basket");
		btnCheckoutBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //checkouts the customers basket.
				if(basket.isEmpty()) {
					lblStatusMessage.setText("Status: Can't Checkout Empty Basket");
				}else {
					if(paypalRButton.isSelected()) {
						String regex = "^(.+)@(.+)$";
						if(txtSampleEmail.getText().matches(regex)) {
							try {
								basket.checkoutBasket(PaymentMethod.PAYPAL);
								lblStatusMessage.setText("Status: Sucessfully Checked Out Basket");
								lblPaymentAmount.setText("£0.0 to be paid using PayPal");
								dtmBasket.setRowCount(0);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}else {
							lblStatusMessage.setText("Status: Invalid Email Address");
						}
					}else if(creditcardRButton.isSelected()) {
						if((txtDigitCard.getText().length()==6) && (txtDigitSecurity.getText().length()==3) && (isNumeric(txtDigitCard.getText())) && (isNumeric(txtDigitSecurity.getText()))) {
							try {
								basket.checkoutBasket(PaymentMethod.CREDITCARD); 
								lblStatusMessage.setText("Status: Sucessfully Checked Out Basket");
								lblPaymentAmount.setText("£0.0 to be paid using Credit Card");
								dtmBasket.setRowCount(0);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}else {
							lblStatusMessage.setText("Status: Invalid Credit Card Details");
						}
					}
				}
			}
		});
		btnCheckoutBasket.setBounds(10, 461, 150, 23);
		viewPanel.add(btnCheckoutBasket);
		
		btnEmptyBasket = new JButton("Empty Basket");
		btnEmptyBasket.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { //empties the customers basket
				if(basket.isEmpty()) {
					lblStatusMessage.setText("Status: Can't Empty Empty Basket");
				}else {
					try {
						basket.emptyBasket();
						lblStatusMessage.setText("Status: Sucessfully Emptied Basket");
						dtmBasket.setRowCount(0);
						bookList = loggedUser.getBooks();
						updateBookTable();
						if(creditcardRButton.isSelected()) {
							lblPaymentAmount.setText("£0.0 to be paid using Credit Card");
						}else if(paypalRButton.isSelected()) {
							lblPaymentAmount.setText("£0.0 to be paid using PayPal");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnEmptyBasket.setBounds(170, 461, 150, 23);
		viewPanel.add(btnEmptyBasket);
		
		btnCreateBasket = new JButton("Create Basket");
		btnCreateBasket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(createdBasket == false) { //
					if(loggedUser instanceof Customer) { //to check you're a customer and not an admin
						//makes it so that you can see all the basket stuff
						basketScrollPane.setVisible(true);
						lblBasketOverview.setText("This Table Contains Your Basket  >  >  >  >  >");
						lblSelectedBook.setVisible(true);
						lblSelectQuantity.setVisible(true);
						lblPaymentMethod.setVisible(true);
						lblPaymentHelp.setVisible(true);
						lblPaymentAmount.setVisible(true);
						lblStatusMessage.setVisible(true);
						lblStatusMessage.setText("");
						spinnerQuantity.setVisible(true);
						paypalRButton.setVisible(true);
						creditcardRButton.setVisible(true);
						txtSampleEmail.setVisible(true);
						txtDigitCard.setVisible(true);
						txtDigitSecurity.setVisible(true);
						btnAddToBasket.setVisible(true);
						btnCheckoutBasket.setVisible(true);
						btnEmptyBasket.setVisible(true);
						dtmBasket.setRowCount(0);
						basket = new Basket((Customer) loggedUser);
						createdBasket = true; //so you can't run this code above and reset your basket accidentally (until you log in as a new customer).
					}else {
					}
				}

			}
		});
		btnCreateBasket.setBounds(10, 239, 150, 23);
		viewPanel.add(btnCreateBasket);
		//----------------------------end of view tab----------------------------//
		
		
		
		//-----------------------------------------------------------------------//
		//--------------------------start of search tab--------------------------//
		//-----------------------------------------------------------------------//
		searchPanel = new JPanel();
		tabbedPane.addTab("Search", null, searchPanel, null);
		searchPanel.setLayout(null);
		
		searchList = new ArrayList<Book>();
		
		searchScrollPane = new JScrollPane();
		searchScrollPane.setBounds(10, 127, 839, 383);
		searchPanel.add(searchScrollPane);
		tblSearchResults = new JTable();
		searchScrollPane.setViewportView(tblSearchResults);
		dtmSearch = new DefaultTableModel();
		dtmSearch.setColumnIdentifiers(new Object[] {"ISBN","Type","Title","Language","Genre","Release Date","Price £","Quantity","E1","E2"}); //creates the headers for the search table
		dtmSearch.addRow(new Object[] {});
		dtmSearch.setRowCount(0);
		tblSearchResults.setModel(dtmSearch);
		
		lblSearchFor = new JLabel("Search For:");
		lblSearchFor.setBounds(10, 11, 69, 14);
		searchPanel.add(lblSearchFor);
		
		lblAndOr = new JLabel("And/Or: ");
		lblAndOr.setBounds(10, 62, 46, 14);
		searchPanel.add(lblAndOr);
		
		lblSearchInfo = new JLabel("Please tick at least one checkbox otherwise nothing will return");
		lblSearchInfo.setBounds(270, 97, 389, 14);
		searchPanel.add(lblSearchInfo);
		
		genreTickBox = new JCheckBox("Genre");
		genreTickBox.setBounds(75, 7, 97, 23);
		searchPanel.add(genreTickBox);
		
		audioTickBox = new JCheckBox("Audiobook with listening length > 5 hours");
		audioTickBox.setBounds(75, 58, 304, 23);
		searchPanel.add(audioTickBox);
		
		politicsRButton = new JRadioButton("Politics");
		politicsRButton.setBounds(10, 32, 109, 23);
		searchPanel.add(politicsRButton);
		politicsRButton.setSelected(true);
		
		medicineRButton = new JRadioButton("Medicine");
		medicineRButton.setBounds(140, 32, 109, 23);
		searchPanel.add(medicineRButton);
		
		businessRButton = new JRadioButton("Business");
		businessRButton.setBounds(270, 32, 109, 23);
		searchPanel.add(businessRButton);
		
		computerscienceRButton = new JRadioButton("Computer Science");
		computerscienceRButton.setBounds(400, 32, 128, 23);
		searchPanel.add(computerscienceRButton);
		
		biographyRButton = new JRadioButton("Biography");
		biographyRButton.setBounds(550, 32, 109, 23);
		searchPanel.add(biographyRButton);
		
		genreRButtons = new ButtonGroup();
		genreRButtons.add(politicsRButton);
		genreRButtons.add(medicineRButton);
		genreRButtons.add(businessRButton);
		genreRButtons.add(computerscienceRButton);
		genreRButtons.add(biographyRButton);
		
		btnSearch = new JButton("Search!");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //if search button is pressed, request the searchList to be updated with the selected search criteria and update search table
				searchList.clear(); 
				dtmSearch.setRowCount(0);
				if(politicsRButton.isSelected()) {
					searchGenre = Genre.POLITICS;
				}else if(medicineRButton.isSelected()) {
					searchGenre = Genre.MEDICINE;
				}else if(businessRButton.isSelected()) {
					searchGenre = Genre.BUSINESS;
				}else if(computerscienceRButton.isSelected()) {
					searchGenre = Genre.COMPUTERSCIENCE;
				}else if(biographyRButton.isSelected()) {
					searchGenre = Genre.BIOGRAPHY;
				}
				if(loggedUser instanceof Customer) {
					try { //update the searchList
						searchList = ((Customer) loggedUser).getSearchList(genreTickBox.isSelected(), searchGenre, audioTickBox.isSelected());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
					
				if(searchList.isEmpty() == false) { 
					for(Book result : searchList) { //fills in the search table with the results of the searchList
						if(result instanceof Paperback) {
							Paperback pbook = (Paperback) result;
							Object[] rowdata = new Object[] {pbook.getIsbn(),pbook.getType(),pbook.getTitle(),pbook.getLang(),pbook.getGenre(),
									pbook.getReleaseDate(),pbook.getPrice(),pbook.getQuantity(),pbook.getNumberOfPages(),pbook.getCondition()};
							dtmSearch.addRow(rowdata);
						}else if(result instanceof Ebook) {
							Ebook ebook = (Ebook) result;
							Object[] rowdata = new Object[] {ebook.getIsbn(),ebook.getType(),ebook.getTitle(),ebook.getLang(),ebook.getGenre(),
									ebook.getReleaseDate(),ebook.getPrice(),ebook.getQuantity(),ebook.getNumberOfPages(),ebook.getFormat()};
							dtmSearch.addRow(rowdata);
						}else if(result instanceof Audiobook) {
							Audiobook abook = (Audiobook) result;
							Object[] rowdata = new Object[] {abook.getIsbn(),abook.getType(),abook.getTitle(),abook.getLang(),abook.getGenre(),
									abook.getReleaseDate(),abook.getPrice(),abook.getQuantity(),abook.getListeningLength(),abook.getFormat()};
							dtmSearch.addRow(rowdata);
						}
					}
				}else {
					dtmSearch.addRow(new Object[] {});
				}
				repaint();
			}
		});
		btnSearch.setBounds(10, 93, 171, 23);
		searchPanel.add(btnSearch);
		//---------------------------end of search tab---------------------------//
		
		
		
		//-----------------------------------------------------------------------//
		//---------------------------start of add tab----------------------------//
		//-----------------------------------------------------------------------//
		addPanel = new JPanel();
		tabbedPane.addTab("Add Books", null, addPanel, null);
		addPanel.setLayout(null);
		
		lblAddMessage = new JLabel("Add Books Tab");
		lblAddMessage.setBounds(10, 11, 217, 14);
		addPanel.add(lblAddMessage);
		
		lblBookIsbn = new JLabel("Book ISBN");
		lblBookIsbn.setBounds(20, 42, 109, 14);
		addPanel.add(lblBookIsbn);
		
		lblBookIsbnConstraint = new JLabel("(8 Digit Integer)");
		lblBookIsbnConstraint.setBounds(237, 42, 252, 14);
		addPanel.add(lblBookIsbnConstraint);
		
		lblBookTitle = new JLabel("Book Title");
		lblBookTitle.setBounds(20, 73, 111, 14);
		addPanel.add(lblBookTitle);
		
		lblBookLanguage = new JLabel("Book Language");
		lblBookLanguage.setBounds(20, 104, 111, 14);
		addPanel.add(lblBookLanguage);
		
		lblBookGenre = new JLabel("Book Genre");
		lblBookGenre.setBounds(20, 135, 111, 14);
		addPanel.add(lblBookGenre);
		
		lblBookReleasedate = new JLabel("Book Release Date");
		lblBookReleasedate.setBounds(20, 166, 111, 14);
		addPanel.add(lblBookReleasedate);
		
		lblBookReleasedateConstraint = new JLabel("(Input as dd-MM-yyyy)");
		lblBookReleasedateConstraint.setBounds(237, 166, 252, 14);
		addPanel.add(lblBookReleasedateConstraint);
		
		lblBookPrice = new JLabel("Book Price £");
		lblBookPrice.setBounds(20, 197, 111, 14);
		addPanel.add(lblBookPrice);
		
		lblBookQuantity = new JLabel("Book Quantity");
		lblBookQuantity.setBounds(20, 228, 111, 14);
		addPanel.add(lblBookQuantity);
		
		lblBookType = new JLabel("Book Type");
		lblBookType.setBounds(20, 259, 111, 14);
		addPanel.add(lblBookType);
		
		lblPaperPages = new JLabel("Number of Pages");
		lblPaperPages.setBounds(141, 290, 111, 14);
		addPanel.add(lblPaperPages);
		
		lblPaperCondition = new JLabel("Condition");
		lblPaperCondition.setBounds(141, 321, 111, 14);
		addPanel.add(lblPaperCondition);
		
		lblEbookPages = new JLabel("Number of Pages");
		lblEbookPages.setBounds(378, 290, 111, 14);
		addPanel.add(lblEbookPages);
		
		lblEbookFormat = new JLabel("Format");
		lblEbookFormat.setBounds(378, 321, 111, 14);
		addPanel.add(lblEbookFormat);
		
		lblAudioListeninglength = new JLabel("Listening Length");
		lblAudioListeninglength.setBounds(635, 290, 111, 14);
		addPanel.add(lblAudioListeninglength);
		
		lblAudioFormat = new JLabel("Format");
		lblAudioFormat.setBounds(635, 321, 111, 14);
		addPanel.add(lblAudioFormat);
		
		lblAddHelp1 = new JLabel("If you are trying to add stock to a book already in the system, then simply enter the ISBN of that book, and the quantity you wish to add.");
		lblAddHelp1.setBounds(20, 375, 822, 14);
		addPanel.add(lblAddHelp1);
		
		lblAddHelp2 = new JLabel("The other details don't matter, as long as they are valid inputs. However, if you wish to add a brand new book, please ensure that the ISBN");
		lblAddHelp2.setBounds(20, 400, 822, 14);
		addPanel.add(lblAddHelp2);
		
		lblAddHelp3 = new JLabel("is unique and not shared by a book already in the system.");
		lblAddHelp3.setBounds(20, 425, 822, 14);
		addPanel.add(lblAddHelp3);
		
		lblAddStatus = new JLabel("");
		lblAddStatus.setBounds(141, 486, 541, 14);
		addPanel.add(lblAddStatus);
		
		spinnerBookQuantity = new JSpinner();
		spinnerBookQuantity.setBounds(141, 225, 86, 20);
		addPanel.add(spinnerBookQuantity);
		
		spinnerPaperPages = new JSpinner();
		spinnerPaperPages.setBounds(262, 287, 86, 20);
		addPanel.add(spinnerPaperPages);
		
		spinnerEbookPages = new JSpinner();
		spinnerEbookPages.setBounds(499, 287, 86, 20);
		addPanel.add(spinnerEbookPages);
		
		//makes it so you can only see the details needed to add a paperback book.
		rdbtnAddPaperback = new JRadioButton("Paperback");
		rdbtnAddPaperback.setBounds(141, 254, 109, 23);
		rdbtnAddPaperback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPaperPages.setVisible(true);
				lblPaperCondition.setVisible(true);
				lblEbookPages.setVisible(false);
				lblEbookFormat.setVisible(false);
				lblAudioListeninglength.setVisible(false);
				lblAudioFormat.setVisible(false);
				spinnerPaperPages.setVisible(true);
				comboBoxPaperCondition.setVisible(true);
				spinnerEbookPages.setVisible(false);
				comboBoxEbookFormat.setVisible(false);
				textListeninglength.setVisible(false);
				comboBoxAudiobookFormat.setVisible(false);
			}
		});
		addPanel.add(rdbtnAddPaperback);
		rdbtnAddPaperback.setSelected(true);
		
		//makes it so you can only see the details needed to add an ebook.
		rdbtnAddEbook = new JRadioButton("Ebook");
		rdbtnAddEbook.setBounds(379, 254, 109, 23);
		rdbtnAddEbook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPaperPages.setVisible(false);
				lblPaperCondition.setVisible(false);
				lblEbookPages.setVisible(true);
				lblEbookFormat.setVisible(true);
				lblAudioListeninglength.setVisible(false);
				lblAudioFormat.setVisible(false);
				spinnerPaperPages.setVisible(false);
				comboBoxPaperCondition.setVisible(false);
				spinnerEbookPages.setVisible(true);
				comboBoxEbookFormat.setVisible(true);
				textListeninglength.setVisible(false);
				comboBoxAudiobookFormat.setVisible(false);
			}
		});
		addPanel.add(rdbtnAddEbook);
		
		//makes it so you can only see the details needed to add an audiobook.
		rdbtnAddAudiobook = new JRadioButton("Audiobook");
		rdbtnAddAudiobook.setBounds(636, 254, 109, 23);
		rdbtnAddAudiobook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblPaperPages.setVisible(false);
				lblPaperCondition.setVisible(false);
				lblEbookPages.setVisible(false);
				lblEbookFormat.setVisible(false);
				lblAudioListeninglength.setVisible(true);
				lblAudioFormat.setVisible(true);
				spinnerPaperPages.setVisible(false);
				comboBoxPaperCondition.setVisible(false);
				spinnerEbookPages.setVisible(false);
				comboBoxEbookFormat.setVisible(false);
				textListeninglength.setVisible(true);
				comboBoxAudiobookFormat.setVisible(true);
			}
		});
		addPanel.add(rdbtnAddAudiobook);
		
		booktypeRButtons = new ButtonGroup();
		booktypeRButtons.add(rdbtnAddPaperback);
		booktypeRButtons.add(rdbtnAddEbook);
		booktypeRButtons.add(rdbtnAddAudiobook);
		
		textBookIsbn = new JTextField();
		textBookIsbn.setText("11111111");
		textBookIsbn.setBounds(141, 39, 86, 20);
		addPanel.add(textBookIsbn);
		textBookIsbn.setColumns(10);
		
		textBookTitle = new JTextField();
		textBookTitle.setBounds(141, 70, 86, 20);
		addPanel.add(textBookTitle);
		textBookTitle.setColumns(10);
		
		textBookPrice = new JTextField();
		textBookPrice.setBounds(141, 194, 86, 20);
		addPanel.add(textBookPrice);
		textBookPrice.setColumns(10);
		
		textListeninglength = new JTextField();
		textListeninglength.setBounds(756, 287, 86, 20);
		addPanel.add(textListeninglength);
		textListeninglength.setColumns(10);
		
		textBookReleasedate = new JTextField();
		textBookReleasedate.setText("01-01-2021");
		textBookReleasedate.setBounds(141, 163, 86, 20);
		addPanel.add(textBookReleasedate);
		
		comboBoxBookGenre = new JComboBox<>(Genre.values());
		comboBoxBookGenre.setBounds(141, 132, 144, 20);
		addPanel.add(comboBoxBookGenre);
		
		comboBoxBookLanguage = new JComboBox<>(Language.values());
		comboBoxBookLanguage.setBounds(141, 101, 86, 20);
		addPanel.add(comboBoxBookLanguage);
		
		comboBoxPaperCondition = new JComboBox<>(Condition.values());
		comboBoxPaperCondition.setBounds(262, 318, 86, 20);
		addPanel.add(comboBoxPaperCondition);
		
		comboBoxEbookFormat = new JComboBox<>(EbookFormat.values());
		comboBoxEbookFormat.setBounds(499, 318, 86, 20);
		addPanel.add(comboBoxEbookFormat);
		
		comboBoxAudiobookFormat = new JComboBox<>(AudioFormat.values());
		comboBoxAudiobookFormat.setBounds(756, 318, 86, 20);
		addPanel.add(comboBoxAudiobookFormat);
		
		btnAddBook = new JButton("Add Book");
		btnAddBook.addActionListener(new ActionListener() { //verifies all the details are correct and if so will add a book, otherwise will update lblAddStatus to tell you what's wrong
			public void actionPerformed(ActionEvent e) {
				if(textBookIsbn.getText().length()==8 && isNumeric(textBookIsbn.getText())) { 
					if(textBookTitle.getText().length() > 0) { 
						String regex = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
						if(textBookReleasedate.getText().matches(regex)) { //to check for a valid date (dd-MM-yyyy)
							if(isDouble(textBookPrice.getText()) && Double.parseDouble(textBookPrice.getText()) > 0) { 
								Integer currentQuantity = (Integer) spinnerBookQuantity.getValue();
								if(currentQuantity > 0) { 
									if(rdbtnAddPaperback.isSelected()) {
										Integer paperPages = (Integer) spinnerPaperPages.getValue();
										if(paperPages > 0) {
											bookToAdd = new Paperback(Integer.parseInt(textBookIsbn.getText()), textBookTitle.getText(), (Language) comboBoxBookLanguage.getSelectedItem(), (Genre) comboBoxBookGenre.getSelectedItem(),
													textBookReleasedate.getText(), Double.parseDouble(textBookPrice.getText()), currentQuantity, (Integer) spinnerPaperPages.getValue(), (Condition) comboBoxPaperCondition.getSelectedItem());
											try {
												if(((Admin) loggedUser).addBook(bookToAdd)) { //adds a book and determines whether a new entry was made to stock, or more stock was added to an already existing entry.
													lblAddStatus.setText("Status: Book With Matching ISBN Found, Adding " +  currentQuantity +  " Copies Of " + textBookIsbn.getText() + " To Stock");
												}else {
													lblAddStatus.setText("Status: Adding " + currentQuantity + " Copies Of " + textBookIsbn.getText() + " To Stock");
												}
												bookList = loggedUser.getBooks();
												updateBookTable();
											} catch (IOException e1) {
												e1.printStackTrace();
												lblAddStatus.setText("Status: Error Adding Book");
											}
										}else {
											lblAddStatus.setText("Status: Please Enter A Page Count Greater Than 0");
										}
									}else if(rdbtnAddEbook.isSelected()) {
										Integer ebookPages = (Integer) spinnerEbookPages.getValue();
										if(ebookPages > 0) { 
											bookToAdd = new Ebook(Integer.parseInt(textBookIsbn.getText()), textBookTitle.getText(), (Language) comboBoxBookLanguage.getSelectedItem(), (Genre) comboBoxBookGenre.getSelectedItem(),
													textBookReleasedate.getText(), Double.parseDouble(textBookPrice.getText()), currentQuantity, (Integer) spinnerEbookPages.getValue(), (EbookFormat) comboBoxEbookFormat.getSelectedItem());
											try {
												if(((Admin) loggedUser).addBook(bookToAdd)) {
													lblAddStatus.setText("Status: Book With Matching ISBN Found, Adding " +  currentQuantity +  " Copies Of " + textBookIsbn.getText() + " To Stock");
												}else {
													lblAddStatus.setText("Status: Adding " + currentQuantity + " Copies Of " + textBookIsbn.getText() + " To Stock");
												}
												bookList = loggedUser.getBooks();
												updateBookTable();
											} catch (IOException e1) {
												e1.printStackTrace();
												lblAddStatus.setText("Status: Error Adding Book");
											}
											
										}else {
											lblAddStatus.setText("Status: Please Enter A Page Count Greater Than 0");
										}
									}else if(rdbtnAddAudiobook.isSelected()) { 
										if(isDouble(textListeninglength.getText()) && Double.parseDouble(textListeninglength.getText()) > 0) { //to check the listening length is > 0
											bookToAdd = new Audiobook(Integer.parseInt(textBookIsbn.getText()), textBookTitle.getText(), (Language) comboBoxBookLanguage.getSelectedItem(), (Genre) comboBoxBookGenre.getSelectedItem(),
													textBookReleasedate.getText(), Double.parseDouble(textBookPrice.getText()), currentQuantity, Double.parseDouble(textListeninglength.getText()), (AudioFormat) comboBoxAudiobookFormat.getSelectedItem());
											try {
												if(((Admin) loggedUser).addBook(bookToAdd)) {
													lblAddStatus.setText("Status: Book With Matching ISBN Found, Added " +  currentQuantity +  " Copies Of ISBN " + textBookIsbn.getText() + " To Stock");
												}else {
													lblAddStatus.setText("Status: Added " + currentQuantity + " Copies Of ISBN " + textBookIsbn.getText() + " To Stock");
												}
												bookList = loggedUser.getBooks();
												updateBookTable();
											} catch (IOException e1) {
												e1.printStackTrace();
												lblAddStatus.setText("Status: Error Adding Book");
											}
										} else {
											lblAddStatus.setText("Status: Please Enter A Listening Length Greater Than 0");
										}
									}
								}else {
									lblAddStatus.setText("Status: Please Enter A Quantity Greater Than 0");
								}
							} else {
								lblAddStatus.setText("Status: Please Enter A Valid Price");
							}
						}else {
							lblAddStatus.setText("Status: Please Enter A Valid Date");
						}
					}else {
						lblAddStatus.setText("Status: Please Enter A Book Title");
					}
				}else {
					lblAddStatus.setText("Status: Please Enter A Valid ISBN");
				}
			}
		});
		btnAddBook.setBounds(10, 477, 111, 33);
		addPanel.add(btnAddBook);
		
		//as by default paperback is selected, makes it so you can only see the details needed to add a paperback book, and hides the rest.
		lblPaperPages.setVisible(true);
		lblPaperCondition.setVisible(true);
		lblEbookPages.setVisible(false);
		lblEbookFormat.setVisible(false);
		lblAudioListeninglength.setVisible(false);
		lblAudioFormat.setVisible(false);
		spinnerPaperPages.setVisible(true);
		comboBoxPaperCondition.setVisible(true);
		spinnerEbookPages.setVisible(false);
		comboBoxEbookFormat.setVisible(false);
		textListeninglength.setVisible(false);
		comboBoxAudiobookFormat.setVisible(false);
		//----------------------------end of add tab-----------------------------//
		
		//by default you can only access the login tab, until you log in as a user and you will then be able to access the other tabs, depending on what your role is.
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
		tabbedPane.setEnabledAt(3, false);
	}

	public void updateBookTable() { //clears and updates the book table
		dtmBooks.setRowCount(0);
		if(bookList != null) {
			for(Book book : bookList) {
				if(book instanceof Paperback) {
					Paperback pbook = (Paperback) book;
					Object[] rowdata = new Object[] {pbook.getIsbn(),pbook.getType(),pbook.getTitle(),pbook.getLang(),pbook.getGenre(),
							pbook.getReleaseDate(),pbook.getPrice(),pbook.getQuantity(),pbook.getNumberOfPages(),pbook.getCondition()};
					dtmBooks.addRow(rowdata);
				}else if(book instanceof Ebook) {
					Ebook ebook = (Ebook) book;
					Object[] rowdata = new Object[] {ebook.getIsbn(),ebook.getType(),ebook.getTitle(),ebook.getLang(),ebook.getGenre(),
							ebook.getReleaseDate(),ebook.getPrice(),ebook.getQuantity(),ebook.getNumberOfPages(),ebook.getFormat()};
					dtmBooks.addRow(rowdata);
				}else if(book instanceof Audiobook) {
					Audiobook abook = (Audiobook) book;
					Object[] rowdata = new Object[] {abook.getIsbn(),abook.getType(),abook.getTitle(),abook.getLang(),abook.getGenre(),
							abook.getReleaseDate(),abook.getPrice(),abook.getQuantity(),abook.getListeningLength(),abook.getFormat()};
					dtmBooks.addRow(rowdata);
				}
			}
		}
	}
	
	public void updateBasketTable() { //clears and updates the basket table
		dtmBasket.setRowCount(0);
		for(int i=0; i<basket.getContents().size(); i++) {
			Book currentBook = basket.getContents().get(i);
			Integer currentQuantity = basket.getQuantities().get(i);
			Object[] rowdata = new Object[] {currentBook.getIsbn(), currentBook.getType(), currentBook.getTitle(), currentQuantity, currentBook.getPrice()*currentQuantity }; //adds the book details to the basket table
			dtmBasket.addRow(rowdata); 
		}
	}
	
	public boolean isNumeric(String str) { //checks if a string can be cast to an integer
		try {
		    Integer testInt = Integer.parseInt(str);
		    return true;
		} catch (NumberFormatException e) {
		    return false;
		}
	}
	
	public boolean isDouble(String str) { //checks if a string can be cast to a double
		try { 
			Double testDouble = Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
