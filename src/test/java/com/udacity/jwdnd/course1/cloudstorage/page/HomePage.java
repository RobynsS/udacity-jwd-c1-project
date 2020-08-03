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
        notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(newNoteButton));
        return notesTable.get(index);
    }

    public WebElement getLastNoteEntry(){
        return getNoteEntry(notesTable.size()-1);
    }

    public WebElement getEditNoteButton(WebElement entry){
        return entry.findElement(By.id("edit-note-button"));
    }

    public WebElement getDeleteNoteButton(WebElement entry){
        return entry.findElement(By.id("delete-note-button"));
    }

    public String getNoteEntryTitle(WebElement entry){
        WebElement note = entry.findElement(By.id("note-entry-title"));
        String text = note.getText();

        return entry.findElement(By.id("note-entry-title")).getText();
    }

    public String getNoteEntryDescription(WebElement entry){
        return entry.findElement(By.id("note-entry-description")).getText();
    }

    public void editNote(String title, String description, int index){
        notesTab.click();
        getEditNoteButton(getNoteEntry(index)).click();
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        noteSubmitButton.click();
    }

    public void deleteNote(int index){
        notesTab.click();
        getDeleteNoteButton(getNoteEntry(index)).click();
    }

}
