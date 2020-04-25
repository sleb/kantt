package io.kantt.model

data class Resource(val name: String)

data class Project(val resources: List<Resource> = listOf())
