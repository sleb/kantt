package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
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
        val kanttConfig = Properties()
        kanttConfig.load(Files.newBufferedReader(options.configPath))
        projectPath?.let { kanttConfig["project-path"] = it.toString() }
        kanttConfig.store(Files.newBufferedWriter(options.configPath), "")
    }
}

class Config : NoOpCliktCommand() {
    init {
        subcommands(Set())
    }
}
