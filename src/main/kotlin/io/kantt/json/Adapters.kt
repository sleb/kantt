package io.kantt.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.nio.file.Path
import java.time.Duration

class PathAdapter {
    @ToJson
    fun toJson(path: Path): String = path.toAbsolutePath().toString()

    @FromJson
    fun fromJson(string: String): Path = Path.of(string)
}

class DurationAdapter {
    @ToJson
    fun toJson(duration: Duration): String = duration.toString()

    @FromJson
    fun fromJson(string: String): Duration = Duration.parse(string)
}
