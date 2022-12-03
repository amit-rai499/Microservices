package com.amit.metube.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.amit.metube.model.Video;

public interface VideoRepository extends MongoRepository<Video, String> {

}
