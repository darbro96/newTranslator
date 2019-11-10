package pl.edu.utp.newTranslator2.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RequestMapping("/img")
@RestController
public class ImageController {
    @RequestMapping("/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
        File img = new File("src/main/resources/static/views/img/"+name);
        return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
    }
}
