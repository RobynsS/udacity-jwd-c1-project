package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller()
@RequestMapping("/home")
public class HomeController {
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final FileService fileService;

    public HomeController(NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
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

    @PostMapping(params={"newFile"})
    public String addFile(@RequestParam("fileUpload") MultipartFile multipartFile,
                          Model model, Authentication authentication) throws IOException {
        String username = authentication.getName();

        //Error handling
        String uploadError = null;
        if(multipartFile.getOriginalFilename().equals("")){
            uploadError = "Please select a file before uploading";
        }
        if(fileService.doesFileExist(multipartFile)){
            uploadError = "This file already exists, please select a different file for upload";
        }

        if(uploadError == null){
            fileService.addFile(multipartFile, username);
        }

        updateData(model, username);
        model.addAttribute("uploadError", uploadError);

        return "home";
    }

    @PostMapping(params={"deleteFile"})
    public String deleteFile(@ModelAttribute File file, Model model, Authentication authentication){
        String username = authentication.getName();
        fileService.deleteFile(file.getFileId());
        updateData(model, username);
        return "home";
    }

    @PostMapping(params={"viewFile"})
    public ResponseEntity<Resource> downloadFile(@ModelAttribute File file, Model model, Authentication authentication){
        //Based on filename, get file from database
        File fileFromDB = fileService.getFile(file.getFilename());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileFromDB.getFilename() + "\"")
                .body(new ByteArrayResource(fileFromDB.getFileData()));
    }

    private void updateData(Model model, String username){
        model.addAttribute("homeController", this);
        List<Note> noteList = noteService.getNotes(username);
        model.addAttribute("noteList", noteList);
        List<Credential> credentialList = credentialService.getCredentials(username);
        model.addAttribute("credentialList", credentialList);
        List<File> fileList = fileService.getFiles(username);
        model.addAttribute("fileList", fileList);
    }

    public String getDecryptedPassword(int credentialId){
        return credentialService.getDecryptedPassword(credentialId);
    }
}
