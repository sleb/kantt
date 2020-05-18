package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import java.nio.file.Path

class Kantt(
    private val configService: OptionsService,
    config: Config,
    resource: Resource,
    task: Task
) : CliktCommand() {
    init {
        subcommands(config, resource, task)
    }

    private val options by findOrSetObject { Options() }
    private val configPath by option().path(canBeDir = false)
        .default(Path.of(System.getProperty("user.home", "."), ".kantt"))
    private val projectPath by option().path(canBeDir = false)

    override fun run() {
        val config = configService.load(configPath)
        options.projectPath = config.projectPath
        if (projectPath != null) {
            options.projectPath = projectPath
        }
        options.configPath = configPath
    }
}
