package io.kantt.cli

import com.github.ajalt.clikt.core.PrintMessage
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import okio.Okio
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

@JsonClass(generateAdapter = true)
data class Options(var configPath: Path? = null, var projectPath: Path? = null)
