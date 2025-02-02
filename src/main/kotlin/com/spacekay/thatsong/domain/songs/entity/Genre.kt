package com.spacekay.thatsong.domain.songs.entity

enum class Genre(val nameKr: String) {
    POP("팝"), DANCE("댄스"), BALLAD("발라드"), HIPHOP("힙합"), ROCK("락"), METAL("메탈"), PUNK("펑크"), RNB("알앤비"), ELECTRONIC("일렉"),
    HOUSE("하우스"), EDM("EDM");

    override fun toString(): String {
        return nameKr
    }
}