package com.spacekay.thatsong.domain.albums.entity

enum class AlbumType(val nameKr: String) {
    SINGLE("싱글"), EP("미니"), LP("정규"), COLLABORATION("콜라보레이션"), OST("OST");

    override fun toString(): String {
        return nameKr
    }
}