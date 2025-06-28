package com.example.recipe_sharing.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.recipe_sharing.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    private final Cloudinary cloudinary;

    @Override
    public String resizeAndUpload(MultipartFile file) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream())
                .size(800, 600)
                .outputFormat("jpg")
                .toOutputStream(outputStream);

        byte[] resizedImage = outputStream.toByteArray();

        Map uploadResult = cloudinary.uploader().upload(
                resizedImage,
                ObjectUtils.asMap("public_id", "recipe_sharing/" + UUID.randomUUID())
        );

        return uploadResult.get("secure_url").toString();
    }

    @Override
    public Map<String, List<String>> uploadMultipartFile(List<MultipartFile> files) {
        List<String> uploadedUrls = new ArrayList<>();
        List<String> failedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                String url = resizeAndUpload(file);
                uploadedUrls.add(url);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file: " + file.getOriginalFilename(), e);
            }
        }

        Map<String, List<String>> result = new HashMap<>();
        result.put("success", uploadedUrls);
        result.put("failed", failedUrls);

        return result;
    }

    @Override
    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file with publicId: " + publicId, e);
        }
    }
}
