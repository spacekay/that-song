package com.spacekay.thatsong.domain.members.repository

import com.spacekay.thatsong.domain.members.entity.ArtistMember
import com.spacekay.thatsong.domain.members.entity.ArtistMemberKey
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArtistMemberRepository : JpaRepository<ArtistMember, ArtistMemberKey> {
    fun findAllByArtistId(artistId: Long): List<ArtistMember>
}
