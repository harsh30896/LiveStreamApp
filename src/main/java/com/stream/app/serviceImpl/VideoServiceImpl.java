package com.stream.app.serviceImpl;

import com.stream.app.entity.Video;
import com.stream.app.repositories.VideoRepositories;
import com.stream.app.service.VideoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
@Service
public class VideoServiceImpl implements VideoService {

    @Value("${files.video}")
    String DIR;

    @Autowired
    private VideoRepositories videoRepositories;

    @PostConstruct
    public void init(){
        File file = new File(DIR);
        if(!file.exists()){
            file.mkdir();
            System.out.println("Folder created");
        }else{
            System.out.println("Folder already created");
        }
    }

    @Override
    public Video save(Video video, MultipartFile file) {
        try {
            // original FileName
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();

            // File path: create
            String cleanFileName = StringUtils.cleanPath(filename);
            // folder path: create
            String cleanFolder=StringUtils.cleanPath(DIR);

            Path path = Paths.get(cleanFolder,cleanFileName);
            System.out.println(path);
            // copy file to the folder
            Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING);

            // create video metadata save
            video.setContentType(contentType);
            video.setFilePath(path.toString());
            return videoRepositories.save(video);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Video get(String videoId) {
        return null;
    }

    @Override
    public Video getByTitle(String title) {
        return null;
    }

    @Override
    public List<Video> getAllVideo() {
        return List.of();
    }
}
