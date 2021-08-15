package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * ref : https://javabom.tistory.com/56
 */

@NoArgsConstructor
@Getter
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String title;
	private String contents;

	@Setter
	private String imagePath;

	@Setter
	private String videoPath;

	public Post(String title, String contents, String imagePath, String videoPath) {
		this.title = title;
		this.contents = contents;
		this.imagePath = imagePath;
		this.videoPath = videoPath;
	}

	public void update(String title,
					   String contents,
					   String imagePath,
					   boolean imageChanged,
					   String videoPath,
					   boolean videoChanged
	) {
		this.title = title;
		this.contents = contents;
		if(imageChanged)
			this.imagePath = imagePath;
		if(videoChanged)
			this.videoPath = videoPath;
	}

}

