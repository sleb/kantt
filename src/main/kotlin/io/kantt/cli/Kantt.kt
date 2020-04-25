package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.github.ajalt.clikt.sources.PropertiesValueSource
import java.nio.file.Path

class Kantt(config: Config, resource: Resource) : CliktCommand() {
    init {
        subcommands(config, resource)
    }

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
    }
}
