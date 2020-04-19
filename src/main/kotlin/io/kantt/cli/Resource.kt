package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.subcommands

class Resource : NoOpCliktCommand() {
    init {
        subcommands(Create())
    }
}

class Create : CliktCommand() {
    override fun run() {
        println("create")
    }
}
