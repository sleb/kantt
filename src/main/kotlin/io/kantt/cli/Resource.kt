package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.arguments.argument

class Create : CliktCommand(printHelpOnEmptyArgs = true) {
    private val name by argument()
    override fun run() {
        echo("resource: $name")
    }
}

class Resource : NoOpCliktCommand() {
    init {
        subcommands(Create())
    }
}
