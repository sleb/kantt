package io.kantt

import io.kantt.cli.Kantt
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.inject

class App : KoinComponent {
    private val kantt by inject<Kantt>()

    fun run(args: Array<String>) {
        kantt.main(args)
    }
}

fun main(args: Array<String>) {
    startKoin { modules(appModule) }
    App().run(args)
}
