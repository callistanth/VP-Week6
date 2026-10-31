package com.example.w6.model

enum class ProjectStatus(val displayName: String) {
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed")
}

data class Project(
    val id: String,
    val communityId: String,
    val communityName: String,
    val title: String,
    val description: String,
    val techStack: List<String>,
    val status: ProjectStatus = ProjectStatus.IN_PROGRESS
)
