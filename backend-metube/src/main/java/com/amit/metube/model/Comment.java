package com.amit.metube.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	
	@Id
	private String id;
	private String authorId;
	private String text;
	private Integer likeCount;
	private Integer disLikeCount;
	
}
