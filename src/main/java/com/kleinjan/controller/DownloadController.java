package com.kleinjan.controller;

import com.kleinjan.returnWrappers.GroupPackage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class DownloadController {

    @RequestMapping("/download-grouping")
    public ResponseEntity<Resource> getRandomFile(@RequestBody GroupPackage groupPackage) {

        File file;

        try{
            file = generateGroupingFile(groupPackage);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try{
            Path path = Paths.get(file.getAbsolutePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            HttpHeaders headers = new HttpHeaders();

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/msword"))
                    .body(resource);

        } catch(IOException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private File generateGroupingFile(GroupPackage groupPackage){
        File file = new File("C:\\Users\\coryk\\Documents\\wordTemplate.docx");

        return file;
    }
}
