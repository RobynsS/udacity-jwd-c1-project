package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    private WebDriverWait wait;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "new-note-button")
    private WebElement newNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "noteSave")
    private WebElement noteSubmitButton;

    @FindBy(id = "notes-table")
    private List<WebElement> notesTable;

    @FindBy(id = "new-credential-button")
    private WebElement newCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "credentialSave")
    private WebElement credentialSubmitButton;

    @FindBy(id = "credentials-table")
    private List<WebElement> credentialsTable;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, 100);
    }

    public void logout(){
        logoutButton.click();
    }

    public void createNewNote(String title, String description) {
        notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(newNoteButton));
        newNoteButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(noteTitleField));
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        noteSubmitButton.click();
    }

    public WebElement getNoteEntry(int index){
        notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(newNoteButton));
        return notesTable.get(index);
    }

    public WebElement getLastNoteEntry(){
        return getNoteEntry(notesTable.size()-1);
    }

    public int getLastNoteIndex(){
        return notesTable.size() - 1;
    }

    public WebElement getEditNoteButton(WebElement entry){
        return entry.findElement(By.id("edit-note-button"));
    }

    public WebElement getDeleteNoteButton(WebElement entry){
        return entry.findElement(By.id("delete-note-button"));
    }

    public String getNoteEntryTitle(WebElement entry){
        return entry.findElement(By.id("note-entry-title")).getText();
    }

    public String getNoteEntryDescription(WebElement entry){
        return entry.findElement(By.id("note-entry-description")).getText();
    }

    public void editNote(String title, String description, int index){
        notesTab.click();
        getEditNoteButton(getNoteEntry(index)).click();
        noteTitleField.clear();
        noteTitleField.sendKeys(title);
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys(description);
        noteSubmitButton.click();
    }

    public void deleteNote(int index){
        notesTab.click();
        getDeleteNoteButton(getNoteEntry(index)).click();
    }

    public void createNewCredential(String url, String username, String password) {
        credentialsTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(newCredentialButton));
        newCredentialButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialUrlField));
        credentialUrlField.sendKeys(url);
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.sendKeys(password);
        credentialSubmitButton.click();
    }

    public WebElement getCredentialEntry(int index){
        credentialsTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(newCredentialButton));
        return credentialsTable.get(index);
    }

    public WebElement getLastCredentialEntry(){
        return getCredentialEntry(credentialsTable.size()-1);
    }

    public int getLastCredentialIndex(){
        return credentialsTable.size() - 1;
    }

    public WebElement getEditCredentialButton(WebElement entry){
        return entry.findElement(By.id("edit-credential-button"));
    }

    public WebElement getDeleteCredentialButton(WebElement entry){
        return entry.findElement(By.id("delete-credentials-button"));
    }

    public String getCredentialEntryUrl(WebElement entry){
        return entry.findElement(By.id("credential-entry-url")).getText();
    }

    public String getCredentialEntryUsername(WebElement entry){
        return entry.findElement(By.id("credential-entry-username")).getText();
    }

    public String getCredentialEntryPassword(WebElement entry){
        return entry.findElement(By.id("credential-entry-password")).getText();
    }

    public void editCredential(String url, String username, String password, int index){
        credentialsTab.click();
        getEditCredentialButton(getCredentialEntry(index)).click();
        credentialUrlField.clear();
        credentialUrlField.sendKeys(url);
        credentialUsernameField.clear();
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.clear();
        credentialPasswordField.sendKeys(password);
        credentialSubmitButton.click();
    }

    public void deleteCredential(int index){
        credentialsTab.click();
        getDeleteCredentialButton(getCredentialEntry(index)).click();
    }

}
