package com.example.recipe_sharing.controller;

import com.example.recipe_sharing.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class ImageController {
    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, List<String>>> uploadFile(@RequestBody List<MultipartFile> files) {
        return ResponseEntity.ok(fileStorageService.uploadMultipartFile(files));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("file") String publicId) {
        try {
            fileStorageService.deleteFile(publicId);

            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body("Failed to delete file: " + e.getMessage());
        }
    }
}