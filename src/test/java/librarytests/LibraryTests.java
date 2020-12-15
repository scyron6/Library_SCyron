package librarytests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import book.Book;
import library.Library;
import patron.Patron;

public class LibraryTests {
	
	private Library library;
	private Patron patron;
	
	@Before
	public void setup() {
		library = new Library();
		library.addBook(new Book("Stephen", "Adventures"));
		patron = new Patron("Matt", library);
		patron.addBook("Adventures");
	}

	@Test
	public void test_searchReturnsTrueIfBookIsAvailable() {
		//Assert
		assertTrue(library.search("Adventures"));
	}
	
	@Test
	public void test_searchReturnsFalseIfBookIsNotAvailable() {
		//Assert
		assertFalse(library.search("BookTitle"));
	}
	
	@Test
	public void test_addRecordProperlyChecksOutABookAndAddsRecord() {
		//Act
		library.addRecord(patron);
		
		//Assert
		assertEquals(0, library.getAvailable().size());
		assertEquals(1, library.getRecords().size());
		assertEquals("Matt", library.getRecords().get("Adventures"));
	}
	
	@Test
	public void test_returnBookRemovesRecordFromLedgerAndAddsBookToAvailable() {
		//Act
		library.addRecord(patron);
		library.returnBook("Adventures");
		
		//Assert
		assertEquals(1, library.getAvailable().size());
		assertEquals(0, library.getRecords().size());
		assertEquals("Adventures", library.getAvailable().get(0));
	}

}
