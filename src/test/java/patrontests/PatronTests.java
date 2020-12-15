package patrontests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import book.Book;
import library.Library;
import patron.Patron;

public class PatronTests {
	
	private Library library;
	private Patron patron;
	
	@Before
	public void setup() {
		library = new Library();
		patron = new Patron("Stephen", library);
		library.addBook(new Book("George", "Thrones"));
	}

	@Test
	public void test_addBookAddsABookToTheWishlist() {
		//Act
		patron.addBook("ToRead");
		
		//Assert
		assertEquals(1, patron.getWishlist().size());
		assertEquals("ToRead", patron.getWishlist().get(0));
	}
	
	@Test
	public void test_BorrowBookRemovesBookFromWishlistAddSwitchesBorrowedToTitle() {
		//Act
		patron.addBook("Thrones");
		patron.borrowBook();
		
		//Assert
		assertEquals(0, patron.getWishlist().size());
		assertEquals(Integer.valueOf(21), Integer.valueOf(patron.getBorrowed().get("Thrones")));
	}
	
	@Test
	public void test_BorrowBookRemovesSecondBookIfFirstBookIsNotAvailable() {
		//Act
		patron.addBook("Buckets");
		patron.addBook("Thrones");
		patron.borrowBook();
		
		//Assert
		assertEquals(1, patron.getWishlist().size());
		assertEquals(Integer.valueOf(21), Integer.valueOf(patron.getBorrowed().get("Thrones")));
		assertEquals("Buckets", patron.getWishlist().get(0));
	}
	
	@Test
	public void test_returnBookSwitchesBorrowedToAnEmptyString() {
		//Arrange
		patron.addBook("Thrones");
		patron.borrowBook();
		
		assertEquals(1, patron.getBorrowed().size());
		
		//Act
		patron.returnBook("Thrones");
		
		//Assert
		assertEquals(0, patron.getBorrowed().size());
	}
	
	@Test
	public void test_PatronWithEmptyBorrowedAndWishlistIsDone() {
		//Arrange
		Patron patron = new Patron("Done", library);
		
		assertTrue(patron.checkDone());
	}
	
	@Test
	public void test_run() {
		//Arrange
		patron.addBook("Thrones");
		final Thread t = new Thread(patron);
		
		//Act
		try {
			t.start();
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
		assertEquals(1,patron.getBorrowed().size());
		
		
	}

}
