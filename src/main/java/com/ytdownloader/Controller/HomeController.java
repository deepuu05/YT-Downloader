package com.ytdownloader.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    private YoutubeService youTubeService;

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadVideo(@RequestParam String url) throws IOException {

        File videoFile = youTubeService.downloadVideo(url);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(videoFile));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + videoFile.getName());
       return ResponseEntity.ok().headers(headers).contentLength(videoFile.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }
}
