package com.example.w6.data

import com.example.w6.model.Community
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object CommunityRepository {

    private val initialCommunities = listOf(
        Community(
            id = "gdsc",
            name = "GDSC",
            fullName = "Google Developer Student Club",
            description = "Fokus pada ekosistem Google: Android Compose, Flutter, Firebase, dan Google Cloud Platform.",
            memberCount = 142,
            isJoined = true,
            iconKey = "code"
        ),
        Community(
            id = "cybersecurity",
            name = "CyberSecurity Guild",
            fullName = "Security & PenTesting",
            description = "Eksplorasi ethical hacking, CTF challenges, reverse engineering, dan pertahanan jaringan kampus.",
            memberCount = 88,
            isJoined = true,
            iconKey = "security"
        ),
        Community(
            id = "ai-society",
            name = "AI Society",
            fullName = "Machine Learning & LLM",
            description = "Riset terapan deep learning, computer vision, agen cerdas, dan fine-tuning large language models.",
            memberCount = 115,
            isJoined = false,
            iconKey = "ai"
        ),
        Community(
            id = "mobile-dev",
            name = "Mobile Dev Community",
            fullName = "Native Jetpack Compose & iOS",
            description = "Native Jetpack Compose & iOS Swift development serta arsitektur mobile modern.",
            memberCount = 95,
            isJoined = true,
            iconKey = "mobile"
        ),
        Community(
            id = "cloud",
            name = "Cloud Computing Club",
            fullName = "AWS & GCP Cloud Architecture",
            description = "Infrastruktur cloud AWS & GCP, DevOps, Docker containerization, dan Kubernetes cluster.",
            memberCount = 76,
            isJoined = false,
            iconKey = "cloud"
        ),
        Community(
            id = "game-dev",
            name = "Game Dev Laboratory",
            fullName = "Interactive 3D & Game Engine",
            description = "Pengembangan game 2D & 3D dengan Unity, Godot engine, dan optimasi performa grafis.",
            memberCount = 64,
            isJoined = false,
            iconKey = "game"
        )
    )

    private val _communities = MutableStateFlow<List<Community>>(initialCommunities)
    val communities: StateFlow<List<Community>> = _communities.asStateFlow()

    fun getCommunities(): List<Community> = _communities.value

    fun getCommunity(communityId: String): Community? {
        return _communities.value.find { it.id == communityId }
    }

    fun toggleJoin(communityId: String) {
        _communities.update { list ->
            list.map { community ->
                if (community.id == communityId) {
                    val newJoined = !community.isJoined
                    val delta = if (newJoined) 1 else -1
                    community.copy(
                        isJoined = newJoined,
                        memberCount = (community.memberCount + delta).coerceAtLeast(0)
                    )
                } else {
                    community
                }
            }
        }
    }
}
