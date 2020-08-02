package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final UserService userService;

    public NoteService(NoteMapper noteMapper, UserService userService){
        this.noteMapper = noteMapper;
        this.userService = userService;
    }

    public List<Note> getNotes(String username){
        Integer userId = userService.getUser(username).getUserId();
        return noteMapper.getNotes(userId);
    }

    public int addNote(Note note, String username){
        Integer userId = userService.getUser(username).getUserId();

        if(note.getNoteId() == null) {
            return noteMapper.insertNote(new Note(null, note.getNoteTitle(), note.getNoteDescription(), userId));
        }else{
            return noteMapper.updateNote(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), userId));
        }
    }

}
