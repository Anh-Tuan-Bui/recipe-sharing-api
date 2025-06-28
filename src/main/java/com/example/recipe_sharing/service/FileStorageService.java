package com.example.recipe_sharing.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface FileStorageService {
    String resizeAndUpload(MultipartFile file) throws IOException;
    Map<String, List<String>> uploadMultipartFile(List<MultipartFile> file);
    void deleteFile(String publicId);
}
