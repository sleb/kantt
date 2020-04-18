package io.kantt.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.findOrSetObject
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.path

class Kantt : CliktCommand() {
    private val configPath by option().path()
    private val opts by findOrSetObject { Options() }

    override fun run() {
        opts.configPath = configPath
    }
}
