package io.kantt.cli

import com.github.ajalt.clikt.core.PrintMessage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import okio.Okio
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

@JsonClass(generateAdapter = true)
data class Options(var configPath: Path? = null, var projectPath: Path? = null)

interface OptionsService {
    fun load(path: Path): Options
    fun save(path: Path, options: Options)
}

class DefaultOptionsService(private val jsonAdapter: JsonAdapter<Options>) : OptionsService {
    override fun load(path: Path): Options =
        if (Files.notExists(path)) {
            Options().also { save(path, it) }
        } else {
            Okio.source(path).use {
                jsonAdapter.fromJson(Okio.buffer(it)) ?: throw PrintMessage(
                    "Failed to load $path"
                )
            }
        }

    override fun save(path: Path, options: Options) =
        Okio.buffer(
            Okio.sink(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            )
        ).use {
            jsonAdapter.toJson(it, options)
        }
}
