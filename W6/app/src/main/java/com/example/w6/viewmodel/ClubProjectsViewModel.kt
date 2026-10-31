package com.example.w6.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.w6.data.CommunityRepository
import com.example.w6.data.ProjectRepository
import com.example.w6.model.Community
import com.example.w6.model.Project
import com.example.w6.navigation.ClubProjectsRoute
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ClubProjectsUiState(
    val community: Community? = null,
    val projects: List<Project> = emptyList()
)

class ClubProjectsViewModel(
    savedStateHandle: SavedStateHandle,
    private val communityRepository: CommunityRepository = CommunityRepository,
    private val projectRepository: ProjectRepository = ProjectRepository
) : ViewModel() {

    val communityId: String = savedStateHandle.toRoute<ClubProjectsRoute>().communityId

    val uiState: StateFlow<ClubProjectsUiState> = combine(
        communityRepository.communities,
        projectRepository.projects
    ) { communities, projects ->
        ClubProjectsUiState(
            community = communities.find { it.id == communityId },
            projects = projects.filter { it.communityId == communityId }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ClubProjectsUiState(
            community = communityRepository.getCommunity(communityId),
            projects = projectRepository.getProjectsForCommunity(communityId)
        )
    )

    fun addProject(title: String, description: String, techStack: List<String>) {
        val community = communityRepository.getCommunity(communityId)
        val communityName = community?.name ?: communityId
        val nextNumber = projectRepository.getProjectsForCommunity(communityId).size + 1
        val newId = "proj-$communityId-${nextNumber.toString().padStart(2, '0')}"

        val newProject = Project(
            id = newId,
            communityId = communityId,
            communityName = communityName,
            title = title.trim(),
            description = description.trim(),
            techStack = techStack
        )
        projectRepository.add(newProject)
    }

    fun deleteProject(projectId: String) {
        projectRepository.deleteById(projectId)
    }
}
