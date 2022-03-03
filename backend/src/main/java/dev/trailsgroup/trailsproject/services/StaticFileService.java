package dev.trailsgroup.trailsproject.services;
import dev.trailsgroup.trailsproject.services.exceptions.UploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StaticFileService {

    @Value("${trails.static.url}")
    private String ip;

    private static StaticFileService instance;

    @PostConstruct
    public void init(){
        StaticFileService.instance = this;
    }

    public static StaticFileService getInstance(){
        return instance;
    }

    public String getIp() {
        return ip;
    }

    public String save(MultipartFile file){
        try {
            Path currentPath = Paths.get(".");
            Path absolutePath = currentPath.toAbsolutePath();
            String path = absolutePath + "/src/main/resources/static/uploads/";
            byte[] bytes = file.getBytes();
            String fileName =  file.getOriginalFilename();
            Path finalPath = Paths.get(path + fileName);
            Files.write(finalPath, bytes);

            return fileName;
        }catch(IOException e){
            e.printStackTrace();
            throw new UploadException("a");
        }
    }







}
