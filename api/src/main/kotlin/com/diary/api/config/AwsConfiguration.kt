package com.diary.api.config

import com.diary.api.properties.AwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
class AwsConfiguration(
    private val awsProperties: AwsProperties
) {

    @Bean
    fun awsCredentials(): AwsBasicCredentials {
        // 자격 증명 설정
        // AWS 제공 access key/secret key 이용한 인증 객체
        // 이를 통해 S3와 통신할 수 있는 권한 획득
        return AwsBasicCredentials.create(awsProperties.s3.accessKey, awsProperties.s3.secretKey)
    }

    @Bean
    fun presigner(credentials: AwsBasicCredentials): S3Presigner {
        // AWS SDK 제공 presigned url 생성을 위한 빌더 객체
        return S3Presigner.builder()
            .region(awsProperties.s3.region)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }
}
