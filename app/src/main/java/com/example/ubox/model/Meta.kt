package com.example.ubox.model

data class Meta(
    val downloadUrl: String,
    val message: String,
    val response: String,
    val status: Boolean,
    val statusNumber: Int,
    val storageUrl: String,
    val totalProcessingTime: Double
)