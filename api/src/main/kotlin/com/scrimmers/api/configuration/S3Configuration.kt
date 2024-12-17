package com.scrimmers.api.configuration

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.scrimmers.api.properties.AwsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Configuration(
    private val awsProperties: AwsProperties
) {

    @Bean
    fun amazonS3(): AmazonS3 {
        val awsCredentials =
            BasicAWSCredentials(awsProperties.aws.credentials.accessKey, awsProperties.aws.credentials.secretKey)
        val credentialProvider = AWSStaticCredentialsProvider(awsCredentials)
        return AmazonS3ClientBuilder.standard()
            .withRegion(awsProperties.aws.region.static)
            .withCredentials(credentialProvider)
            .build()
    }
}
