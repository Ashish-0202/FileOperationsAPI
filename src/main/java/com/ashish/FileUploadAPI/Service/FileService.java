package com.ashish.FileUploadAPI.Service;

import com.ashish.FileUploadAPI.Entity.FileEntity;
import com.ashish.FileUploadAPI.Repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class FileService {

    private static final String path="D:\\Java_SpringBoot\\Demo\\Resumes\\";

    @Autowired
    private FileRepository fileRepository;

    public FileEntity storeFile(MultipartFile file) throws IOException{
        FileEntity fileEntity=new FileEntity();

        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setData(file.getBytes());

        File f1=new File(path);
        if (!f1.exists()){
            f1.mkdir();
        }
        String filepath= path + file.getOriginalFilename();
        byte[] content = file.getBytes();

        try(OutputStream outputStream=new FileOutputStream(filepath)) {
            outputStream.write(content);
        }

        return fileRepository.save(fileEntity);
    }

    public FileEntity load(long id){
        return fileRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
