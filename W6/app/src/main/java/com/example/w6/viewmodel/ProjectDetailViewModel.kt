package com.example.w6.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.w6.data.ProjectRepository
import com.example.w6.model.Project
import com.example.w6.navigation.ProjectDetailRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ProjectDetailUiState(
    val project: Project? = null
)

class ProjectDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val projectRepository: ProjectRepository = ProjectRepository
) : ViewModel() {

    val projectId: String = savedStateHandle.toRoute<ProjectDetailRoute>().projectId

    val uiState: StateFlow<ProjectDetailUiState> = projectRepository.projects
        .map { projects ->
            ProjectDetailUiState(project = projects.find { it.id == projectId })
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProjectDetailUiState(project = projectRepository.getProject(projectId))
        )

    fun toggleStatus() {
        projectRepository.toggleStatus(projectId)
    }
}
