package io.kantt.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Resource(val alias: String)
