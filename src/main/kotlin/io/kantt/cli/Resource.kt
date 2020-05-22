package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import io.kantt.model.ModelService
import io.kantt.model.Project
import io.kantt.model.Resource

class ResourceCreate(private val projectService: ModelService<Project>) :
    CliktCommand(printHelpOnEmptyArgs = true, name = "create", help = "Create project resources") {

    private val options by requireObject<Options>()
    private val alias by argument()

    override fun run() {
        val projectPath = options.projectPath ?: throw PrintMessage("Missing project path")
        projectService.load(projectPath).also {
            it.resources += Resource(alias)
            projectService.save(projectPath, it)
        }
    }
}

class ResourceList(private val projectService: ModelService<Project>) :
    CliktCommand(name = "list", help = "List project resources") {

    private val options by requireObject<Options>()

    override fun run() {
        echo("Resources:")
        val projectPath = options.projectPath ?: throw PrintMessage("Missing project path")
        projectService.load(projectPath).resources
            .sortedBy { it.alias }
            .forEach { echo("  ${it.alias}") }
    }
}

class Resource(create: ResourceCreate, list: ResourceList) : NoOpCliktCommand(help = "Manage project resources") {
    init {
        subcommands(create, list)
    }

    override fun aliases(): Map<String, List<String>> = genAliases()
}
