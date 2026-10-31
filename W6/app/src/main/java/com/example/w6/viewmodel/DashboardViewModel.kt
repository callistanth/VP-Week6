package com.example.w6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.w6.data.CommunityRepository
import com.example.w6.data.ProjectRepository
import com.example.w6.model.Community
import com.example.w6.model.Project
import com.example.w6.model.UserProfile
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class DashboardUiState(
    val userProfile: UserProfile = UserProfile(),
    val joinedClubs: List<Community> = emptyList(),
    val projects: List<Project> = emptyList()
) {
    val joinedClubsCount: Int get() = joinedClubs.size
    val totalProjectsCount: Int get() = projects.size
}

class DashboardViewModel(
    private val communityRepository: CommunityRepository = CommunityRepository,
    private val projectRepository: ProjectRepository = ProjectRepository
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = combine(
        communityRepository.communities,
        projectRepository.projects
    ) { communities, projects ->
        DashboardUiState(
            userProfile = UserProfile(),
            joinedClubs = communities.filter { it.isJoined },
            projects = projects
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DashboardUiState(
            userProfile = UserProfile(),
            joinedClubs = communityRepository.getCommunities().filter { it.isJoined },
            projects = projectRepository.getAllProjects()
        )
    )
}
