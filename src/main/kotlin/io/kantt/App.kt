package io.kantt

import com.github.ajalt.clikt.core.subcommands
import io.kantt.cli.Config
import io.kantt.cli.Kantt
import io.kantt.cli.Resource

fun main(args: Array<String>) {
    Kantt().subcommands(
        Resource(),
        Config()
    ).main(args)
}
