package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.NoOpCliktCommand
import com.github.ajalt.clikt.core.requireObject
import com.github.ajalt.clikt.core.subcommands
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path

class Set : CliktCommand() {
    private val opts by requireObject<Options>()
    private val projectFilePath by option().path()

    override fun run() {
        println("opts: $opts")
    }
}

class Config : NoOpCliktCommand() {
    init {
        subcommands(Set())
    }
}
