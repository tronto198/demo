package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostListResponseDto {
	public List<PostGetResponseDto> posts;
	public int page;
	public int pageSize;
	public boolean hasPrevious;
	public boolean hasNext;
}
