package dev.trailsgroup.trailsproject.services;
import dev.trailsgroup.trailsproject.services.exceptions.ClientUploadException;
import dev.trailsgroup.trailsproject.services.exceptions.UploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;

import java.util.Date;

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

    private String getPath(){
        Path currentPath = Paths.get(".");
        Path absolutePath = currentPath.toAbsolutePath();
        return absolutePath + "/src/main/resources/static/uploads/";
    }

    public String save(MultipartFile file) {
        try {
            String path = getPath();
            byte[] bytes = file.getBytes();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmssS");

            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String replacedFileName = fileName.split("\\.")[0].replaceAll("[^a-zA-Z0-9_.-]+", "-");

            fileName = replacedFileName + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
            String newFileName = (sdf.format(new Date()) + "-" + fileName);

            Path finalPath = Paths.get(path + newFileName);
            Files.write(finalPath, bytes);
            return newFileName;
        } catch(NoSuchFileException e){
            try {
                Files.createDirectories(Path.of(getPath()));
                throw new UploadException("O problema foi corrigido automaticamente pelo sistema, tente novamente!");
            }catch (IOException x){
                throw new UploadException("Não foi possível criar o diretório para o envio de imagens. Comunique o responsável pelo sistema");
            }
        } catch(IOException e){
            e.printStackTrace();
            throw new UploadException("Houve um problema durante o envio do arquivo");
        }catch(AssertionError e){
            throw new ClientUploadException("O nome do arquivo é nulo. Envie um arquivo que contenha um nome!");
        }
    }

    public String update(MultipartFile file, String oldName){
        String path = getPath();
        try {
            if(!oldName.substring(0, oldName.indexOf("-")).contains("default")) {
                Files.deleteIfExists(Path.of(path + oldName));
            }
            return save(file);
        } catch (IOException e) {
            throw new UploadException("Houve um erro durante a atualização do arquivo");
        }
    }










}
