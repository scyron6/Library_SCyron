package library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import book.Book;
import patron.Patron;

public class Library {
	private List<Book> books;
	private List<String> available;
	private Map<String, String> records;
	private final Lock lock = new ReentrantLock();
	private int day;

	public Library() {
		this.books = new ArrayList<Book>();
		this.available = new ArrayList<String>();
		this.records = new HashMap<String, String>();
	}

	public void addBook(Book book) {
		books.add(book);
		available.add(book.getTitle());
	}

	public List<Book> getBooks() {
		return books;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public List<String> getAvailable() {
		return available;
	}

	public Map<String, String> getRecords() {
		return records;
	}

	public boolean search(String title) {
		return available.contains(title);
	}

	public void returnBook(String title) {
		lock.lock();

		try {
			records.remove(title);
			available.add(title);
		} finally {
			lock.unlock();
		}
	}

	public Optional<String> addRecord(Patron patron) {

		lock.lock();

		try {
			for (String title : patron.getWishlist()) {
				if (this.search(title)) {
					available.remove(title);
					records.put(title, patron.getName());
					return Optional.of(title);
				}
			}
			return Optional.empty();
		} finally {
			lock.unlock();
		}
	}
}
