package com.diary.api.service.uploads

import com.diary.api.properties.AwsProperties
import io.hypersistence.tsid.TSID
import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class UploadService(
    private val awsProperties: AwsProperties,
    private val presigner: S3Presigner
) {

    fun generatePresignedUrl(contentType: String, filename: String): String {
        val key: String = generateKey(filename = filename)

        // presigned url 실행될 실제 요청의 내용을 구성
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(awsProperties.s3.bucket)
            // 저장 경로
            .key(key)
            // mime type 설정
            .contentType(contentType)
            .build()

        // 요청 구성을 기반으로 URL 생성
        val presignedRequest: PresignedPutObjectRequest = presigner.presignPutObject { it
            // 유효 시간
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(putObjectRequest)
        }

        return presignedRequest.url().toString()
    }

    private fun generateKey(filename: String): String {
        val now = LocalDate.now()
        val datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        return "$datePath/$filename"
    }
}
