package com.diary.api.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import software.amazon.awssdk.regions.Region

@ConfigurationProperties(prefix = "aws")
data class AwsProperties(
    val s3: S3
)

data class S3(
    val bucket: String,
    val region: Region,
    val accessKey: String,
    val secretKey: String
)
