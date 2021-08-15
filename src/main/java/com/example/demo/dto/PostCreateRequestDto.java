package com.example.demo.dto;

import com.example.demo.model.Post;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequestDto {
	private String title;
	private String contents;

	private String imagePath;
	private String videoPath;

	public Post toEntity() {
		Post post = new Post(title, contents, imagePath, videoPath);
		return post;
	}
}
