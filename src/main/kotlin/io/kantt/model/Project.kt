package io.kantt.model

import com.github.ajalt.clikt.core.PrintMessage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import io.kantt.cli.Options
import okio.Okio
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

@JsonClass(generateAdapter = true)
data class Project(var resources: Set<Resource> = setOf(), var tasks: List<Task> = listOf())
