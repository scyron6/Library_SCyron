package main;

import java.util.List;
import book.Book;
import library.Library;
import patron.Patron;

public class Demo {
	public static void main(String[] args) {
		Controller controller = new Controller();
		Library library = controller.getLibrary();

		String[] names = { "Patron1", "Patron2", "Patron3", "Patron4", "Patron5" };

		for (String name : names) {
			controller.createPatron(name);
		}

		controller.createBook("JK Rowling", "Harry Potter");
		controller.createBook("George RR Martin", "Game of Thrones");
		controller.createBook("Patrick Rothfuss", "Name of the Wind");
		controller.createBook("Sir Conan Doyle", "Sherlock Holmes");
		controller.createBook("Suzanne Collins", "The Hunger Games");

		List<Book> books = library.getBooks();

		for (Patron patron : controller.getPatrons()) {
			for (Book book : books) {
				patron.addBook(book.getTitle());
			}
			patron.shuffle();
		}

		System.out.println("Starting simulation");
		controller.runAll();

	}

}
