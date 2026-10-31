package com.example.w6.data

import com.example.w6.model.Project
import com.example.w6.model.ProjectStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object ProjectRepository {

    private val initialProjects = listOf(
        Project(
            id = "proj-gdsc-01",
            communityId = "gdsc",
            communityName = "GDSC",
            title = "Smart Campus Navigation",
            description = "Sistem navigasi peta gedung kampus interaktif berbasis Jetpack Compose dan Google Maps SDK. Memfasilitasi mahasiswa dan dosen menemukan ruang perkuliahan, laboratorium komputer, perpustakaan, serta fasilitas umum kampus secara akurat disertai rute tercepat dan visualisasi lokasi real-time.",
            techStack = listOf("Kotlin", "Jetpack Compose", "Room", "Google Maps SDK"),
            status = ProjectStatus.IN_PROGRESS
        ),
        Project(
            id = "proj-gdsc-02",
            communityId = "gdsc",
            communityName = "GDSC",
            title = "Student Attendance QR Scanner",
            description = "Pindai barcode & QR presensi kehadiran workshop berbasis CameraX & Firebase sinkronisasi presensi mahasiswa cepat.",
            techStack = listOf("Jetpack Compose", "Firebase", "CameraX"),
            status = ProjectStatus.COMPLETED
        ),
        Project(
            id = "proj-gdsc-03",
            communityId = "gdsc",
            communityName = "GDSC",
            title = "Developer RSVP Portal",
            description = "Portal pendaftaran webinar dan bootcamp teknologi sinkronisasi Ktor API server untuk pendaftaran event.",
            techStack = listOf("Kotlin", "Ktor", "Compose"),
            status = ProjectStatus.IN_PROGRESS
        ),
        Project(
            id = "proj-cs-01",
            communityId = "cybersecurity",
            communityName = "CyberSecurity Guild",
            title = "Campus Network Vulnerability Scanner",
            description = "Tool audit keamanan jaringan lokal kampus untuk mendeteksi celah open port dan paket data rentan.",
            techStack = listOf("Python", "Ktor"),
            status = ProjectStatus.IN_PROGRESS
        )
    )

    private val _projects = MutableStateFlow<List<Project>>(initialProjects)
    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    fun getAllProjects(): List<Project> = _projects.value

    fun getProject(projectId: String): Project? {
        return _projects.value.find { it.id == projectId }
    }

    fun getProjectsForCommunity(communityId: String): List<Project> {
        return _projects.value.filter { it.communityId == communityId }
    }

    fun add(project: Project) {
        _projects.update { it + project }
    }

    fun deleteById(projectId: String) {
        _projects.update { list ->
            list.filterNot { it.id == projectId }
        }
    }

    fun toggleStatus(projectId: String) {
        _projects.update { list ->
            list.map { project ->
                if (project.id == projectId) {
                    val nextStatus = if (project.status == ProjectStatus.IN_PROGRESS) {
                        ProjectStatus.COMPLETED
                    } else {
                        ProjectStatus.IN_PROGRESS
                    }
                    project.copy(status = nextStatus)
                } else {
                    project
                }
            }
        }
    }
}
