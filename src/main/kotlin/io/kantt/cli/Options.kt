package io.kantt.cli

import io.kantt.model.Project
import java.nio.file.Path

data class Options(
    var configPath: Path? = null,
    var project: Project? = null
)
