package io.kantt.model

import com.google.common.jimfs.Jimfs
import com.squareup.moshi.Moshi
import io.kantt.json.DurationAdapter
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Files
import java.time.Duration

class JsonFileModelServiceTest : StringSpec({
    val design = Task("design task", Duration.ofDays(5))
    val adapter = Moshi.Builder()
        .add(DurationAdapter())
        .build()
        .adapter(Task::class.java)
    val svc = JsonFileModelService<Task>({ design }, adapter)

    "svc.load(new_path) should return the result of the factory" {
        val fs = Jimfs.newFileSystem()
        svc.load(fs.getPath("/does_not_exist_yet")) shouldBe design
    }

    "svc.load(new_path) should create a new file" {
        val fs = Jimfs.newFileSystem()
        val path = fs.getPath("/does_not_exist_yet")
        svc.load(path)
        Files.exists(path) shouldBe true
        withContext(Dispatchers.IO) {
            Files.readString(path) shouldBe adapter.toJson(design)
        }
    }

    "svc.load(path) should load an existing file" {
        val fs = Jimfs.newFileSystem()
        val path = fs.getPath("/project.gantt")
        withContext(Dispatchers.IO) {
            Files.writeString(path, adapter.toJson(design))
        }
        svc.load(path) shouldBe design
    }

    "svc.save(path) should save a json file" {
        val fs = Jimfs.newFileSystem()
        val path = fs.getPath("/project.gantt")
        svc.save(path, design)
        withContext(Dispatchers.IO) {
            Files.readString(path) shouldBe adapter.toJson(design)
        }
    }
})
