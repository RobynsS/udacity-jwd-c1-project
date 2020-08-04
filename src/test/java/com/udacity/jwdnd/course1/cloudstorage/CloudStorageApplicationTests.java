package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.firefoxdriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new FirefoxDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void unauthorizedAccessTest() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void signUpLoginLogoutTest(){
		String firstName = "Adam";
		String lastName = "West";
		String username = "awest";
		String password = "awestpass";

		signUp(firstName, lastName, username, password);
		login(username, password);

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		logout();
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void createNoteTest() {
		String firstName = "Ben";
		String lastName = "West";
		String username = "bwest";
		String password = "bwestpass";

		String noteTitle = "Title of the note";
		String noteDescription = "Description of the note";

		signUp(firstName, lastName, username, password);
		login(username, password);

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createNewNote(noteTitle, noteDescription);

		WebElement note = homePage.getLastNoteEntry();
		Assertions.assertEquals(noteTitle, homePage.getNoteEntryTitle(note));
		Assertions.assertEquals(noteDescription, homePage.getNoteEntryDescription(note));
	}

	@Test
	public void editNoteTest(){
		String firstName = "Cheryl";
		String lastName = "West";
		String username = "cwest";
		String password = "cwestpass";

		String noteTitle = "Title of the note";
		String noteDescription = "Description of the note";
		String noteTitleNew = "Change in title";
		String noteDescriptionNew = "Change in description";

		signUp(firstName, lastName, username, password);
		login(username, password);

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createNewNote(noteTitle, noteDescription);
		int index = homePage.getLastNoteIndex();

		homePage.editNote(noteTitleNew, noteDescriptionNew, index);

		WebElement note = homePage.getLastNoteEntry();
		Assertions.assertEquals(noteTitleNew, homePage.getNoteEntryTitle(note));
		Assertions.assertEquals(noteDescriptionNew, homePage.getNoteEntryDescription(note));
	}

	@Test
	public void deleteNoteTest(){
		String firstName = "David";
		String lastName = "West";
		String username = "dwest";
		String password = "dwestpass";

		String noteTitle = "Title of the note";
		String noteDescription = "Description of the note";

		signUp(firstName, lastName, username, password);
		login(username, password);

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		int indexStart = homePage.getLastNoteIndex();
		homePage.createNewNote(noteTitle, noteDescription);
		int index = homePage.getLastNoteIndex();
		Assertions.assertEquals(indexStart + 1, index);

		homePage.deleteNote(index);
		index = homePage.getLastNoteIndex();
		Assertions.assertEquals(indexStart, index);
	}

	@Test
	public void createCredentialTest() {
		String firstName = "Earl";
		String lastName = "West";
		String username = "ewest";
		String password = "ewestpass";

		String credentialUrl = "Url of the credential";
		String credentialUsername = "Username for the credential";
		String credentialPassword = "Decrypted password of for the credential";

		signUp(firstName, lastName, username, password);
		login(username, password);

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createNewCredential(credentialUrl, credentialUsername, credentialPassword);

		WebElement credential = homePage.getLastCredentialEntry();
		Assertions.assertEquals(credentialUrl, homePage.getCredentialEntryUrl(credential));
		Assertions.assertEquals(credentialUsername, homePage.getCredentialEntryUsername(credential));
		Assertions.assertNotEquals(credentialPassword, homePage.getCredentialEntryPassword(credential));
	}

	@Test
	public void editCredentialTest(){
		String firstName = "Florance";
		String lastName = "West";
		String username = "fwest";
		String password = "fwestpass";

		String credentialUrl = "Url of the credential";
		String credentialUsername = "Username for the credential";
		String credentialPassword = "Decrypted password";
		String credentialUrlNew = "Change in url";
		String credentialUsernameNew = "Change in username";
		String credentialPasswordNew = "Changed password";

		signUp(firstName, lastName, username, password);
		login(username, password);

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createNewCredential(credentialUrl, credentialUsername, credentialPassword);
		int index = homePage.getLastCredentialIndex();

		String oldDecryptedPassword = homePage.editCredential(credentialUrlNew, credentialUsernameNew, credentialPasswordNew, index);
		WebElement credential = homePage.getLastCredentialEntry();
		Assertions.assertEquals(credentialPassword, oldDecryptedPassword);
		Assertions.assertEquals(credentialUrlNew, homePage.getCredentialEntryUrl(credential));
		Assertions.assertEquals(credentialUsernameNew, homePage.getCredentialEntryUsername(credential));
	}

	@Test
	public void deleteCredentialTest(){
		String firstName = "George";
		String lastName = "West";
		String username = "gwest";
		String password = "gwestpass";

		String credentialUrl = "Url of the credential";
		String credentialUsername = "Username for the credential";
		String credentialPassword = "Decrypted password";

		signUp(firstName, lastName, username, password);
		login(username, password);

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		int indexStart = homePage.getLastCredentialIndex();
		homePage.createNewCredential(credentialUrl, credentialUsername, credentialPassword);
		int index = homePage.getLastCredentialIndex();
		Assertions.assertEquals(indexStart + 1, index);

		homePage.deleteCredential(index);
		index = homePage.getLastCredentialIndex();
		Assertions.assertEquals(indexStart, index);
	}

	public void signUp(String firstName, String lastName, String username, String password){
		driver.get("http://localhost:" + this.port + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);
	}

	public void login(String username, String password){
		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	public void logout(){
		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.logout();
	}
}
