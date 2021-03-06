package io.kantt

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import io.kantt.cli.Config
import io.kantt.cli.ConfigSet
import io.kantt.cli.ConfigShow
import io.kantt.cli.Kantt
import io.kantt.cli.Options
import io.kantt.cli.Resource
import io.kantt.cli.ResourceCreate
import io.kantt.cli.ResourceList
import io.kantt.cli.Task
import io.kantt.cli.TaskCreate
import io.kantt.cli.TaskList
import io.kantt.json.DurationAdapter
import io.kantt.json.PathAdapter
import io.kantt.model.JsonFileModelService
import io.kantt.model.ModelService
import io.kantt.model.Project
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single { Moshi.Builder().add(PathAdapter()).add(DurationAdapter()).build() }
    single<JsonAdapter<Project>>(named("project")) { get<Moshi>().adapter(Project::class.java).indent("  ") }
    single<JsonAdapter<Options>>(named("options")) { get<Moshi>().adapter(Options::class.java).indent("  ") }
    single<ModelService<Project>>(named("project")) { JsonFileModelService({ Project() }, get(named("project"))) }
    single<ModelService<Options>>(named("options")) { JsonFileModelService({ Options() }, get(named("options"))) }
    single { ConfigShow(get(named("options"))) }
    single { ConfigSet(get(named("options"))) }
    single { Config(get(), get()) }
    single { ResourceCreate(get(named("project"))) }
    single { ResourceList(get(named("project"))) }
    single { Resource(get(), get()) }
    single { TaskCreate(get(named("project"))) }
    single { TaskList(get(named("project"))) }
    single { Task(get(), get()) }
    single { Kantt(get(named("options")), get(), get(), get()) }
}
