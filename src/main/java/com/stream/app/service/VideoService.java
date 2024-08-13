package com.stream.app.service;

import com.stream.app.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {

    //saveVideo
    Video save(Video video, MultipartFile file);
    //getVideoById
    Video get(String videoId);
    //getVideoByTitle
    Video getByTitle(String title);

    //getAllVideo
    List<Video> getAllVideo();



}
