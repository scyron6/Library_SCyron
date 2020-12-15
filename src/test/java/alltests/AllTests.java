package alltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controllertests.ControllerTests;
import librarytests.LibraryTests;
import patrontests.PatronTests;

@RunWith(Suite.class)
@SuiteClasses({
	ControllerTests.class,
	LibraryTests.class,
	PatronTests.class
})
public class AllTests {

}
