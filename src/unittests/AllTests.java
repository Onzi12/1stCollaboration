package unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LoginAlreadyLoggedinTest.class, LoginLockedTest.class,
		LoginSuccessfulTest.class, LoginWrongPasswordLockTest.class,
		LoginWrongPasswordTest.class })
public class AllTests {

}
