package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import io.kantt.model.ProjectService
import io.kantt.model.Resource

class ResourceCreate(private val projectService: ProjectService) :
    CliktCommand(printHelpOnEmptyArgs = true, name = "create") {
    private val options by requireObject<Options>()
    private val alias by argument()

    override fun run() {
        val projectPath = options.projectPath ?: throw PrintMessage("Missing project path")
        val project = projectService.load(projectPath)
        project.resources += Resource(alias)
        projectService.save(projectPath, project)
    }
}

class ResourceList(private val projectService: ProjectService) : CliktCommand(name = "list") {
    private val options by requireObject<Options>()

    override fun run() {
        echo("Resources:")
        val projectPath = options.projectPath ?: throw PrintMessage("Missing project path")
        projectService.load(projectPath).resources
            .sortedBy { it.alias }
            .forEach { echo("  ${it.alias}") }
    }
}

class Resource(create: ResourceCreate, list: ResourceList) : NoOpCliktCommand() {
    init {
        subcommands(create, list)
    }
}
