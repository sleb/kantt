package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path

class ConfigSet(private val configService: OptionsService) : CliktCommand(name = "set") {
    private val options by requireObject<Options>()
    private val projectPath by option().path(canBeDir = false)

    override fun run() {
        val configPath = options.configPath ?: throw PrintMessage("Missing config path")
        configService.load(configPath).also {
            if (projectPath != null) {
                it.projectPath = projectPath
            }

            configService.save(configPath, it)
        }
    }
}

class ConfigShow(private val configService: OptionsService) : CliktCommand(name = "show") {
    private val options by requireObject<Options>()

    override fun run() {
        val configPath = options.configPath ?: throw PrintMessage("Missing config path")
        val options = configService.load(configPath)
        echo("Kantt Config (${configPath}):")
        echo("  project-path = ${options.projectPath}")
    }
}

class Config(configSet: ConfigSet, configShow: ConfigShow) : NoOpCliktCommand() {
    init {
        subcommands(configSet, configShow)
    }
}
