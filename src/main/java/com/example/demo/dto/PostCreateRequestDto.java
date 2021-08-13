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

	public Post toEntity() {
		return new Post(title, contents);
	}
}
