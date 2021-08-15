package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PostUpdateRequestDto {
	private String title;
	private String contents;
	private String imagePath;
	private boolean imageChanged;
	private String videoPath;
	private boolean videoChanged;
}
