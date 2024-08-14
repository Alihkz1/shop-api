package com.shop.controller;

import com.shop.service.UploadService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("api/v1/upload")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping
    public ResponseEntity<Response> upload(@RequestPart(name = "file") MultipartFile file) {
        return uploadService.uploadFile(file);
    }
}
