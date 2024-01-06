package com.example.demo.Controller;

import com.example.demo.Model.Documents;
import com.example.demo.Service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ImageUploadController {

    @Autowired
    ImageUploadService imgServ;

    /*
     * Getting the base Paths from the Properties files from the properties files
     */

    @Value("${project.image}")
    private String path;

    @Value("${project.sendimage}")
    private String sendImage;

    /*
     * Api from Here
     */

    @PostMapping("/uploadImage")
    public String image(@RequestParam("image") MultipartFile image) throws IOException {

        return imgServ.imgUpload(path, image);

    }

    @PostMapping("/addDoc/{cid}/{type}")
    public ResponseEntity<String> docUpload(@RequestParam("image") MultipartFile image, @PathVariable int cid,
                                            @PathVariable String type) throws IOException {

        String result = imgServ.documentUpload(path, image, cid, type);

        if ("success".equals(result)) {
            return ResponseEntity.ok("Document uploaded successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @GetMapping("/getAllDoc/{cid}")
    public List<Documents> getAllDocsByCid(@PathVariable int cid) {
        return imgServ.getAllDoc(cid);
    }

    @PostMapping("/deleteDoc/{cid}/{type}/{status}")
    public String deleteTheDoc(@PathVariable int cid, @PathVariable String type, @PathVariable String status) {
        return imgServ.makeDocStatusInactive(cid, type, status);
    }

    @GetMapping("img/{path}")
    public ResponseEntity<FileSystemResource> getImage(@PathVariable String path) {
        return imgServ.getTheImage(path, sendImage);

    }

}
