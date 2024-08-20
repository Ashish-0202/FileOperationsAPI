package com.ashish.FileUploadAPI.Controller;

import com.ashish.FileUploadAPI.Entity.FileEntity;
import com.ashish.FileUploadAPI.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file")MultipartFile file){
        try {
            FileEntity savedFile= fileService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body("File Uploaded Successfully:"+savedFile.getFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> loadfile(@PathVariable("id") long id){
        FileEntity fileEntity=fileService.load(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                .body(fileEntity.getData());

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewImage(@PathVariable Long id) {
        FileEntity fileEntity = fileService.load(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                .body(fileEntity.getData());
    }
}
