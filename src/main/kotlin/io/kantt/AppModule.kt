package io.kantt

import io.kantt.cli.Config
import io.kantt.cli.ConfigService
import io.kantt.cli.ConfigSet
import io.kantt.cli.ConfigShow
import io.kantt.cli.DefaultConfigService
import io.kantt.cli.Kantt
import io.kantt.cli.Resource
import org.koin.dsl.module

val appModule = module {
    single<ConfigService> { DefaultConfigService() }
    single { Resource() }
    single { ConfigSet(get()) }
    single { ConfigShow(get()) }
    single { Config(get(), get()) }
    single { Kantt(get(), get()) }
}
