package com.chatop.controllers;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();

        try {

            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("localhost", 21);
            ftpClient.login("ftpuser", "1234");
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            String remotePath = "/ftp/uploads/" + fileName;
            boolean done = ftpClient.storeFile(remotePath, file.getInputStream());
            ftpClient.logout();
            ftpClient.disconnect();

            if (done) {
                String url = "ftp://ftpuser@localhost/ftp/uploads/" + fileName;
                return ResponseEntity.ok(url);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'upload");
            }

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur FTP : " + e.getMessage());
        }
    }
}
