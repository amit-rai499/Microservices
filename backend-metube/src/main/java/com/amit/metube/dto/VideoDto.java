package com.amit.metube.dto;

import java.util.Set;

import com.amit.metube.model.VideoStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
	
	private String id;
	private String title;
	private String description;
	private Set<String> tags;
	private String videoUrl;
	private VideoStatus videoStatus;
	private String userId;
	private String thumbnailUrl;
	private Integer likeCount;
	private Integer dislikeCount;
	private Integer viewCount;
}

