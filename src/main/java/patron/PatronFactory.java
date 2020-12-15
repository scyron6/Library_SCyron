package patron;

import library.Library;

public class PatronFactory {
	
	public Patron registerPatron(String name, Library library) {
		return new Patron(name, library);
	}
}
