package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping()
    public String homeView() {
        return "home";
    }

    @PostMapping(params={"newNote"})
    public String newNote(@ModelAttribute Note note, Model model, Authentication authentication){
        String username = authentication.getName();
        String title = note.getNoteTitle();
        noteService.createNote(note, username);
        List<Note> noteList = noteService.getNotes(username);
        model.addAttribute("noteList", noteList);
        return "home";
    }

}
