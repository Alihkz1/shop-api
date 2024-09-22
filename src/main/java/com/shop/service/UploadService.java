package com.shop.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.shop.shared.Exceptions.BadRequestException;
import com.shop.shared.classes.BaseService;
import com.shop.shared.classes.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class UploadService extends BaseService {

    @Value("${s3.url}")
    private String url;

    @Value("${s3.bucket}")
    private String bucketName;

    @Value("${s3.access-key}")
    private String accessKey;

    @Value("${s3.secret-key}")
    private String secretKey;

    private AmazonS3 s3Client;

    public ResponseEntity<Response> uploadFile(MultipartFile file) {
        long size_10mb = 10 * 1024 * 1024;
        if (file.getSize() > size_10mb) {
            throw new BadRequestException("حجم فایل آپلود شده بیشتر از 10 مگابایت است");
        }
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(url, "us-east-1"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withPathStyleAccessEnabled(true)
                .build();

        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, file.getOriginalFilename(), inputStream, null);
            s3Client.putObject(putObjectRequest);
            String fileUrl = s3Client.getUrl(bucketName, file.getOriginalFilename()).toString();
            return successResponse(fileUrl);
        } catch (IOException e) {
            return null;
        }
    }
}
