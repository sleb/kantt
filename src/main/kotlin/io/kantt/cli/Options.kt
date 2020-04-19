package io.kantt.cli

import java.nio.file.Path

data class Options(
    var configPath: Path? = null,
    var projectPath: Path? = null
)
