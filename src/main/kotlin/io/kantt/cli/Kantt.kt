package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.github.ajalt.clikt.sources.PropertiesValueSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.kantt.model.Project
import java.nio.file.Files
import java.nio.file.Path

class Kantt : CliktCommand() {
    private val options by findOrSetObject {
        Options(configPath = kanttConfigPath())
    }

    init {
        context {
            valueSource = PropertiesValueSource.from(kanttConfigPath().toFile())
        }
    }

    private val projectPath by option().path(canBeDir = false)

    private fun kanttConfigPath(): Path = Path.of(
        System.getProperty("user.home", "."),
        ".kantt",
        "config.properties"
    )

    override fun run() {
        options.configPath = kanttConfigPath()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val projectAdapter = moshi.adapter(Project::class.java)
        val projPath = projectPath
        if (projPath != null) {
            val absoluteProjectPath = projPath.toAbsolutePath()
            val project = if (Files.exists(absoluteProjectPath)) {
                projectAdapter.fromJson(Files.newBufferedReader(absoluteProjectPath).readText())
            } else {
                Project().also {
                    Files.createDirectories(absoluteProjectPath.parent)
                    Files.newBufferedWriter(projPath).write(projectAdapter.toJson(it))
                }
            }
            options.project = project
        }
    }
}
