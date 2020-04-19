package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import com.github.ajalt.clikt.sources.PropertiesValueSource
import java.nio.file.Path

class Kantt : CliktCommand() {
    private val kanttConfPath = Path.of(
        System.getProperty("user.home", "."),
        ".kantt",
        "config.properties"
    )

    init {
        context {
            valueSource = PropertiesValueSource.from(kanttConfPath.toFile())
        }
    }

    private val projectPath by option().path().default(Path.of("project.kantt"))
    private val options by findOrSetObject { Options() }

    override fun run() {
        options.projectPath = projectPath
    }
}
