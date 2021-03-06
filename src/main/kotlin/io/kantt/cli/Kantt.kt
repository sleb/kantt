package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path
import io.kantt.model.ModelService
import java.nio.file.Path

fun CliktCommand.genAliases(): Map<String, List<String>> {
    val prefixes = mutableMapOf<String, List<String>>()
    for (name in registeredSubcommandNames()) {
        if (name.length > 3) {
            for (i in 1..name.lastIndex) {
                val prefix = name.substring(0..i)
                prefixes[prefix] = listOf(name)
            }
        }
    }

    return prefixes.filterKeys { prefixes.getValue(it).size == 1 }
}

class Kantt(
    private val configService: ModelService<Options>,
    config: Config,
    resource: Resource,
    task: Task
) : CliktCommand(help = "Create and manage projects") {

    init {
        subcommands(config, resource, task)
    }

    private val options by findOrSetObject { Options() }
    private val configPath by option().path(canBeDir = false)
        .default(Path.of(System.getProperty("user.home", "."), ".kantt"))
    private val projectPath by option().path(canBeDir = false)

    override fun aliases(): Map<String, List<String>> = genAliases()

    override fun run() {
        val config = configService.load(configPath)
        options.projectPath = config.projectPath
        if (projectPath != null) {
            options.projectPath = projectPath
        }
        options.configPath = configPath
    }
}
