package io.kantt.model

import com.github.ajalt.clikt.core.PrintMessage
import com.squareup.moshi.JsonAdapter
import okio.Okio
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

interface ModelService<T> {
    fun load(path: Path): T
    fun save(path: Path, model: T)
}

class JsonFileModelService<T>(private val factory: () -> T, private val jsonAdapter: JsonAdapter<T>) : ModelService<T> {
    override fun load(path: Path): T =
        if (Files.notExists(path)) {
            factory().also { save(path, it) }
        } else {
            Okio.source(path).use {
                jsonAdapter.fromJson(Okio.buffer(it)) ?: throw PrintMessage(
                    "Failed to load $path"
                )
            }
        }

    override fun save(path: Path, model: T) =
        Okio.buffer(
            Okio.sink(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            )
        ).use {
            jsonAdapter.toJson(it, model)
        }
}
