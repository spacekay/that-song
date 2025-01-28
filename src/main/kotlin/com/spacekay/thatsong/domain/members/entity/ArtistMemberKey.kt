package com.spacekay.thatsong.domain.members.entity

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ArtistMemberKey(
    val artistId: Long,
    val memberId: Long
) : Serializable
