package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import java.nio.file.Files
import java.util.Properties

class Set : CliktCommand() {
    private val options by requireObject<Options>()
    private val projectPath by option().path()

    override fun run() {
        val configPath = options.configPath ?: throw PrintMessage("please specify a config path")

        val kanttConfig = Properties()
        if (Files.exists(configPath)) {
            kanttConfig.load(Files.newBufferedReader(options.configPath))
        } else {
            Files.createDirectories(configPath.parent)
        }
        projectPath?.let { kanttConfig["project-path"] = it.toString() }
        kanttConfig.store(Files.newBufferedWriter(configPath), "")
    }
}

class Config : NoOpCliktCommand() {
    init {
        subcommands(Set())
    }
}
