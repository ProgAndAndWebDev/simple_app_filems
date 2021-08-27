package com.example.ubox.model

data class Data(
    val id: Int,
    val video_main_info_id: Int,
    val album_id: Any?  = null,
    val tutorial_id: Any?  = null,
    val type: String,
    val image: String,
    val image_path: String,
    val video_genres: List<String>,
    val album_genres: List<String>?  = null,
    val tutorial_category: Any?  = null,
    val tutorial_subcategory: Any?  = null,
    val album: Any? = null,
    val video: Video,
    val tutorial: Any?  = null,
)