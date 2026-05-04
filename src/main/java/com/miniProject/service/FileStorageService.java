package com.miniProject.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path uploadDir;

    public FileStorageService(@Value("${app.upload.dir:uploads}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String originalName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (originalName.contains("..")) {
            throw new IllegalArgumentException("Invalid file name");
        }
        String extension = "";
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > -1) {
            extension = originalName.substring(dotIndex);
        }
        String fileName = UUID.randomUUID() + extension;
        try {
            Files.createDirectories(uploadDir);
            Path targetLocation = uploadDir.resolve(fileName);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to store file", ex);
        }
        return "/uploads/" + fileName;
    }
}

