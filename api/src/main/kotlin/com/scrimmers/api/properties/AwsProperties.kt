package com.scrimmers.api.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "cloud")
data class AwsProperties(
    val aws: Aws
)

data class Aws(
    val s3: S3,
    val stack: Stack,
    val region: Region,
    val credentials: Credentials
)

data class S3(
    val bucket: String
)

data class Stack(
    val auto: Boolean
)

data class Region(
    val static: String
)

data class Credentials(
    val accessKey: String,
    val secretKey: String
)
