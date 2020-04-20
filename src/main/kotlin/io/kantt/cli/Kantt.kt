package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.github.ajalt.clikt.sources.PropertiesValueSource
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

    private val projectPath by option().path()

    private fun kanttConfigPath(): Path = Path.of(
        System.getProperty("user.home", "."),
        ".kantt",
        "config.properties"
    )

    override fun run() {
        options.projectPath = projectPath
    }
}
