package main;

import java.util.ArrayList;
import java.util.List;

import book.BookFactory;
import library.Library;
import patron.Patron;
import patron.PatronFactory;

public class Controller {
	
	private List<Patron> patrons;
	private Library library;
	private BookFactory bookFactory;
	private PatronFactory patronFactory;
	private int day;
	
	public Controller() {
		this.patrons = new ArrayList<Patron>();
		this.library = new Library();
		this.bookFactory = new BookFactory();
		this.patronFactory = new PatronFactory();
		this.day = 0;
	}
	
	public List<Patron> getPatrons() {
		return patrons;
	}
	
	public void createBook(String author, String title) {
		library.addBook(bookFactory.registerBook(author, title));
	}
	
	public void createPatron(String name) {
		patrons.add(patronFactory.registerPatron(name, library));
	}
	
	public Library getLibrary() {
		return library;
	}
	
	public int getDay() {
		return day;
	}
	
	public void runAll() {
		while(patrons.stream().map(Patron::getDone).anyMatch(d -> d == false))
		{
			try {
				library.setDay(day);
				final List<Thread> readers = new ArrayList<>();
				patrons.forEach(p -> {
					final Thread t = new Thread(p);
					readers.add(t);
				});
				
				readers.parallelStream().forEach(Thread::start);
				for (final Thread t : readers) {
					t.join();
				}
				day += 1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
