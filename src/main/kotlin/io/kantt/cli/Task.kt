package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.PrintMessage
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.convert
import io.kantt.model.ModelService
import io.kantt.model.Project
import io.kantt.model.Task
import java.time.Duration

class TaskList(private val projectService: ModelService<Project>) : CliktCommand(name = "list") {
    private val options by requireObject<Options>()

    override fun run() {
        echo("Tasks:")
        val projectPath = options.projectPath ?: throw PrintMessage("Missing project path")
        projectService.load(projectPath).tasks
            .sortedBy { it.description }
            .forEach { echo("  ${it.description}, ${it.effort}") }
    }
}

class TaskCreate(private val projectService: ModelService<Project>) : CliktCommand(name = "create") {
    private val options by requireObject<Options>()
    private val description by argument()
    private val effort by argument().convert { Duration.parse(it) }


    override fun run() {
        val projectPath = options.projectPath ?: throw PrintMessage("Missing project path")
        projectService.load(projectPath).also {
            it.tasks += Task(description, effort)
            projectService.save(projectPath, it)
        }
    }
}

class Task(taskList: TaskList, taskCreate: TaskCreate) : NoOpCliktCommand() {
    init {
        subcommands(taskList, taskCreate)
    }
}
