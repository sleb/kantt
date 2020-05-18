package io.kantt

import com.squareup.moshi.Moshi
import io.kantt.cli.Config
import io.kantt.cli.ConfigSet
import io.kantt.cli.ConfigShow
import io.kantt.cli.DefaultOptionsService
import io.kantt.cli.Kantt
import io.kantt.cli.Options
import io.kantt.cli.OptionsService
import io.kantt.cli.Resource
import io.kantt.cli.ResourceCreate
import io.kantt.cli.ResourceList
import io.kantt.json.PathAdapter
import io.kantt.model.DefaultProjectService
import io.kantt.model.Project
import io.kantt.model.ProjectService
import org.koin.dsl.module

val appModule = module {
    single { Moshi.Builder().add(PathAdapter()).build() }
    single<ProjectService> { DefaultProjectService(get<Moshi>().adapter(Project::class.java).indent("  ")) }
    single<OptionsService> { DefaultOptionsService(get<Moshi>().adapter(Options::class.java).indent("  ")) }
    single { ConfigShow(get()) }
    single { ConfigSet(get()) }
    single { Config(get(), get()) }
    single { ResourceCreate(get()) }
    single { ResourceList(get()) }
    single { Resource(get(), get()) }
    single { Kantt(get(), get(), get()) }
}
