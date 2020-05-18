package io.kantt.model

import com.github.ajalt.clikt.core.PrintMessage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import io.kantt.cli.Options
import okio.Okio
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

@JsonClass(generateAdapter = true)
data class Project(var resources: Set<Resource> = setOf()) {
    companion object {
        fun empty() = Project(resources = setOf())
    }
}

interface ProjectService {
    fun load(path: Path): Project
    fun save(path: Path, project: Project)
}

class DefaultProjectService(private val jsonAdapter: JsonAdapter<Project>) : ProjectService {
    override fun load(path: Path): Project =
        if (Files.notExists(path)) {
            Project.empty().also { save(path, it) }
        } else {
            Okio.source(path).use {
                jsonAdapter.fromJson(Okio.buffer(it)) ?: throw PrintMessage(
                    "Failed to load $path"
                )
            }
        }

    override fun save(path: Path, project: Project) =
        Okio.buffer(
            Okio.sink(
                path,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
            )
        ).use {
            jsonAdapter.toJson(it, project)
        }
}

