package book;

public class BookFactory {
	
	public Book registerBook(String author, String title) {
		return new Book(author, title);
	}
}
