package com.stream.app.controller;

import com.stream.app.entity.Video;
import com.stream.app.payload.CustomMessage;
import com.stream.app.service.VideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    private VideoService videoService;
    public VideoController(VideoService videoService){
        this.videoService = videoService;
    }

    @PostMapping("/saveVideo")
    public ResponseEntity<?> create(@RequestParam("file")MultipartFile file, @RequestParam("title") String title,
                                                @RequestParam("description") String description){

        Video video = new Video();
        video.setTitle(title);
        video.setDescription(description);
        video.setVideoId(UUID.randomUUID().toString());
        Video saveVideo = videoService.save(video,file);
        if(saveVideo != null){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(video);
        }else{
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomMessage.builder()
                            .message("Video not loaded")
                            .success(false)
                            .build()
                    );
        }
    }
}
