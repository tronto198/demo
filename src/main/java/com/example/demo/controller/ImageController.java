package com.example.demo.controller;

import com.example.demo.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileNotFoundException;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class ImageController {
	private final ImageService imageService;

	@CrossOrigin
	@GetMapping(value = "image/{path}",
		produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
	public ResponseEntity<byte[]> getImage(@PathVariable String path) {
		try {
			byte[] data = imageService.getImage(path);
			return new ResponseEntity<>(data, HttpStatus.OK);
		} catch (FileNotFoundException fe) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IOException ie) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
