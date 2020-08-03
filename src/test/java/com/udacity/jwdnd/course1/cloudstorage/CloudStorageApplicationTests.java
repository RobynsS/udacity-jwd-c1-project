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

	private boolean loggedIn = false;

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
		login();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Home", driver.getTitle());

		logout();
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void createNoteTest() {
		String noteTitle = "Title of the note";
		String noteDescription = "Description of the note";

		login();

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		homePage.createNewNote(noteTitle, noteDescription);

		WebElement note = homePage.getLastNoteEntry();
		Assertions.assertEquals(noteTitle, homePage.getNoteEntryTitle(note));
		Assertions.assertEquals(noteDescription, homePage.getNoteEntryDescription(note));
	}

	public void login(){
		if(!loggedIn) {
			String firstName = "Adam";
			String lastName = "West";
			String username = "awest";
			String password = "password";

			driver.get("http://localhost:" + this.port + "/signup");
			signupPage = new SignupPage(driver);
			signupPage.signup(firstName, lastName, username, password);

			driver.get("http://localhost:" + this.port + "/login");
			loginPage = new LoginPage(driver);
			loginPage.login(username, password);

			loggedIn = true;
		}
	}

	public void logout(){
		if(loggedIn) {
			driver.get("http://localhost:" + this.port + "/home");
			homePage = new HomePage(driver);
			homePage.logout();
			loggedIn = false;
		}
	}

}
