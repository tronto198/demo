package com.example.demo.service;

import com.example.demo.Config;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

@Service
public class ImageService {
	public String saveImage(
		MultipartFile image
	) {
		if(image != null && !image.isEmpty()){
			try {
				String imagePath = UUID.randomUUID().toString();
				File file = new File(Config.imageRootPath + File.separator + imagePath);
				if(file.getParentFile() != null)
					file.getParentFile().mkdirs();
				FileCopyUtils.copy(image.getBytes(), file);
				return imagePath;
			} catch (IOException e){
				// ignore
				System.out.println("error - "+e.toString());
			}
		}
		return null;
	}

	public byte[] getImage(String path) throws IOException, FileNotFoundException {
		File imageFile = new File(Config.imageRootPath + File.separator + path);
		InputStream is = new FileInputStream(imageFile);
		byte[] data = is.readAllBytes();
		is.close();
		return data;
	}
}
