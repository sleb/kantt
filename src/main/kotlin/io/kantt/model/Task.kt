package io.kantt.model

import com.squareup.moshi.JsonClass
import java.time.Duration

@JsonClass(generateAdapter = true)
data class Task(val description: String, val effort: Duration)
