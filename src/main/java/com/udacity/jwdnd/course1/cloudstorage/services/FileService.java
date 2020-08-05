package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserService userService;

    public FileService(FileMapper fileMapper, UserService userService){
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public List<File> getFiles(String username){
        Integer userId = userService.getUser(username).getUserId();
        return fileMapper.getFiles(userId);
    }

    public int addFile(MultipartFile multipartFile, String username) throws IOException {
        Integer userId = userService.getUser(username).getUserId();
        String filename = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        byte[] fileData = multipartFile.getBytes();

        return fileMapper.insertFile(new File(null, filename, contentType, fileSize, userId, fileData));
    }

    public void deleteFile(Integer fileId){
        fileMapper.deleteFile(fileId);
    }

    public boolean doesFileExist(MultipartFile file){
        String filename = file.getOriginalFilename();
        return fileMapper.getFileByFilename(filename) != null;
    }
}
