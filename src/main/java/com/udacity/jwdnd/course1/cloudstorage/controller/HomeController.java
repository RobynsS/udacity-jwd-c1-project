package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;
    private final CredentialService credentialService;

    public HomeController(NoteService noteService, CredentialService credentialService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping()
    public String homeView(Model model, Authentication authentication) {
        String username = authentication.getName();
        updateData(model, username);
        return "home";
    }

    @PostMapping(params={"newNote"})
    public String addNote(@ModelAttribute Note note, Model model, Authentication authentication){
        String username = authentication.getName();
        noteService.addNote(note, username);
        updateData(model, username);
        return "home";
    }

    @PostMapping(params={"deleteNote"})
    public String deleteNote(@ModelAttribute Note note, Model model, Authentication authentication){
        String username = authentication.getName();
        noteService.deleteNote(note.getNoteId());
        updateData(model, username);
        return "home";
    }

    @PostMapping(params={"newCredential"})
    public String addCredential(@ModelAttribute Credential credential, Model model, Authentication authentication){
        String username = authentication.getName();
        credentialService.addCredential(credential, username);
        updateData(model, username);
        return "home";
    }

    @PostMapping(params={"deleteCredential"})
    public String deleteCredential(@ModelAttribute Credential credential, Model model, Authentication authentication){
        String username = authentication.getName();
        credentialService.deleteCredential(credential.getCredentialId());
        updateData(model, username);
        return "home";
    }

    private void updateData(Model model, String username){
        model.addAttribute("homeController", this);
        List<Note> noteList = noteService.getNotes(username);
        model.addAttribute("noteList", noteList);
        List<Credential> credentialList = credentialService.getCredentials(username);
        model.addAttribute("credentialList", credentialList);
    }

    public String getDecryptedPassword(int credentialId){
        return credentialService.getDecryptedPassword(credentialId);
    }
}
