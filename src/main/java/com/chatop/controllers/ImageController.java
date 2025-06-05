package com.chatop.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
				return ResponseEntity.ok(fileName);
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'upload");
			}

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur FTP : " + e.getMessage());
		}
	}

	@GetMapping(value = "/file/{path}",
			produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getFile(@PathVariable String path) throws IOException {
		try {
			FTPClient ftpClient = new FTPClient();
			ftpClient.connect("localhost", 21);
			ftpClient.login("ftpuser", "1234");
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			String remotePath = "/ftp/uploads/" + path;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			boolean success = ftpClient.retrieveFile(remotePath, outputStream);

			ftpClient.logout();
			ftpClient.disconnect();

			if (success) {
				byte[] fileBytes = outputStream.toByteArray();
				return fileBytes;
			} else {
				return null;
			}

		} catch (IOException e) {
			return null;
		}
	}

	@GetMapping(value = "/path/{path}")
	public @ResponseBody String getImage(@PathVariable String path) throws IOException {
		return path;
//        return IOUtils.toByteArray(in);
	}
}
