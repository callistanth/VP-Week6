package com.example.w6.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.w6.data.CommunityRepository
import com.example.w6.model.Community
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class CommunitiesUiState(
    val communities: List<Community> = emptyList()
)

class CommunitiesViewModel(
    private val communityRepository: CommunityRepository = CommunityRepository
) : ViewModel() {

    val uiState: StateFlow<CommunitiesUiState> = communityRepository.communities
        .map { CommunitiesUiState(communities = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CommunitiesUiState(communities = communityRepository.getCommunities())
        )

    fun toggleCommunityJoin(communityId: String) {
        communityRepository.toggleJoin(communityId)
    }
}
