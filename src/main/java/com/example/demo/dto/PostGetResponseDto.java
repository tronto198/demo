package com.example.demo.dto;

import com.example.demo.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostGetResponseDto {
	public long id;
	public String title;
	public String contents;

	public String imagePath;
	public String videoPath;

	public PostGetResponseDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.contents = post.getContents();
		this.imagePath = post.getImagePath();
		this.videoPath = post.getVideoPath();
	}
}
