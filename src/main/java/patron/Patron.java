package patron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Library;

public class Patron implements Runnable {
	
	private List<String> wishlist;
	private Map<String, Integer> borrowed;
	private Library library;
	private String name;
	private Logger logger;
	private boolean done;
	
	public Patron(String name, Library library) {
		this.wishlist = new ArrayList<String>();
		this.borrowed = new HashMap<>();
		this.name = name;
		this.library = library;
		this.logger = LogManager.getLogger("BookRecords");
		this.done = false;
	}
	
	public List<String> getWishlist() {
		return wishlist;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String, Integer> getBorrowed() {
		return borrowed;
	}
	
	public boolean getDone() {
		return done;
	}
	
	@Override
	public String toString() {
		return "Patron [wishlist=" + wishlist + ", name=" + name + "]";
	}
	
	public void shuffle() {
		Collections.shuffle(wishlist);
	}

	public void addBook(String title) {
		wishlist.add(title);
	}
	
	public void borrowBook() {
		Optional<String> book = library.addRecord(this); 
		if (book.isPresent()) {
			borrowed.put(book.get(), library.getDay()+21);
			wishlist.remove(book.get());
			logger.info(name + " borrowed " + book.get() + " from the library on day " + library.getDay());
		}	
		else {
			logger.info(name + " couldn't find a book from his wish list" + wishlist);
		}
	}
	
	public void returnBook(String title) {
		if(!borrowed.isEmpty()) {
			library.returnBook(title);
			logger.info(name + " returned " + title + " to the library on day " + library.getDay());
			borrowed.remove(title);
		}
	}
	
	
	public void run() {
		if (!done) {
			if (library.getDay() % 7 == 0) {
				this.borrowBook();
			}
			
			List<String> titles = new ArrayList<>();
			for (String each : borrowed.keySet()) {
				titles.add(each);
			}
			
			Random random = new Random();
			for (String title : titles) {
				if (random.nextInt(21) >= borrowed.get(title) - library.getDay()) {
					this.returnBook(title);
					if (checkDone()) {
						done = true;
						System.out.println(name + " read every book in " + library.getDay() + " days!");
					}
					else
						this.borrowBook();
				}	
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean checkDone() {
		return (wishlist.size() == 0 && borrowed.size() == 0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patron other = (Patron) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
