package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path

class ConfigSet(private val configSvc: ConfigService) : CliktCommand(name = "set") {
    private val options by requireObject<Options>()
    private val projectPath by option().path(canBeDir = false)

    override fun run() {
        val configPath = options.configPath ?: throw PrintMessage("please specify a config path")
        val config = configSvc.loadOrCreate(configPath)

        projectPath?.let { config["project-path"] = it.toString() }

        configSvc.save(configPath, config)
    }
}

class ConfigShow(private val configSvc: ConfigService) : CliktCommand(name = "show") {
    private val options by requireObject<Options>()

    override fun run() {
        val configPath = options.configPath ?: throw PrintMessage("please specify a config path")
        val config = configSvc.loadOrCreate(configPath)

        for ((k, v) in config) {
            echo("$k=$v")
        }
    }
}

class Config(set: ConfigSet, show: ConfigShow) : NoOpCliktCommand() {
    init {
        subcommands(set, show)
    }
}
