package com.amit.metube.model;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "Video")
public class Video {

	@Id
	private String id;
	private String title;
	private String description;
	private String userId;
	private AtomicInteger likes = new AtomicInteger(0);
	private AtomicInteger disLikes = new AtomicInteger(0);
	private Set<String> tags;
	private String videoUrl;
	private VideoStatus videoStatus;
	private AtomicInteger viewCount = new AtomicInteger(0);
	private String thumbnailUrl;
	private List<Comment> commentList = new CopyOnWriteArrayList<>() ;
	
	public void incrementLikes() {
		likes.incrementAndGet();
	}
	
	public void decrementLikes() {
		likes.decrementAndGet();
	}
	
	public void incrementDislikes() {
		disLikes.incrementAndGet();
	}
	
	public void decrementDislikes() {
		disLikes.decrementAndGet();
	}

	public void incrementViewCount() {
		viewCount.incrementAndGet();
	}

	public void addComment(Comment comment) {
		commentList.add(comment);
		
	}
}
