package io.kantt

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.nio.file.Path

class PathAdapter {
    @ToJson
    fun toJson(path: Path): String = path.toAbsolutePath().toString()

    @FromJson
    fun fromJson(string: String): Path = Path.of(string)
}
