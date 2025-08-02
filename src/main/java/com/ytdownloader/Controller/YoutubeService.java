package com.ytdownloader.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
@Service
public class YoutubeService {
      public File downloadVideo(String url) throws IOException {
        // Use yt-dlp or youtube-dl command line tool
        String fileName =  ".";
        ProcessBuilder builder = new ProcessBuilder(
        "c:\\yt-dlp\\yt-dlp.exe",
        "--no-playlist",
        "-f", "best",
        "-o", "%(title)s.%(ext)s",
        url


        );
        builder.redirectErrorStream(true);
        Process process = builder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try (Stream<Path> files = Files.list(Paths.get(fileName))) {
        return files
            .filter(f -> !Files.isDirectory(f))
            .filter(f -> f.toString().endsWith(".mp4")) // or use more flexible checks
            .sorted((a, b) -> Long.compare(
                b.toFile().lastModified(),
                a.toFile().lastModified()))
            .findFirst()
            .map(Path::toFile)
            .orElseThrow(() -> new IOException("Downloaded file not found"));

    }
}
}
    