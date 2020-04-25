package io.kantt.cli

import io.kantt.model.Project
import java.nio.file.Files
import java.nio.file.Path
import java.util.Properties

data class Options(
    var configPath: Path? = null,
    var project: Project? = null
)

interface ConfigService {
    fun loadOrCreate(path: Path): Properties
    fun save(configPath: Path, config: Properties)
}

class DefaultConfigService : ConfigService {
    override fun loadOrCreate(path: Path): Properties {
        val config = Properties()
        if (Files.exists(path)) {
            val reader = Files.newBufferedReader(path)
            config.load(reader)
            reader.close()
        } else {
            Files.createDirectories(path.parent)
            Files.createFile(path)
        }
        return config
    }

    override fun save(configPath: Path, config: Properties) {
        val writer = Files.newBufferedWriter(configPath)
        config.store(writer, "")
        writer.close()
    }
}
