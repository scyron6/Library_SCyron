package controllertests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import library.Library;
import main.Controller;

public class ControllerTests {
	
	private Controller controller;
	
	@Before
	public void setup() {
		controller = new Controller();
	}

	@Test
	public void test_createPatronProperlyAddsPatronToList() {
		//Arrange
		assertEquals(0, controller.getPatrons().size());
		
		//Act
		controller.createPatron("John");
		
		//Assert
		assertEquals(1, controller.getPatrons().size());
	}
	
	@Test
	public void test_createBookProperlyCreatesAndAddsBookToList() {
		//Arrange
		Library library = controller.getLibrary();
		
		//Act
		controller.createBook("Stephen", "Adventures");
		
		//Assert
		assertEquals(1, library.getBooks().size());
	}
	
	@Test
	public void test_runAllDoesntAlterDayIfThereAreNoPatrons() {
		//Arrange
		controller.runAll();
		
		assertEquals(0, controller.getDay());
	}

	@Test
	public void test_runAllIncreasesDayIfPatronsHaveToBorrowBooks() {
		//Arrange
		controller.createPatron("Matt");
		
		controller.createBook("Matt", "Cool Book");
		controller.getPatrons().get(0).addBook("Cool Book");
		
		controller.runAll();
		int test = controller.getDay();
		
		assertTrue(test > 0);
	}
}
