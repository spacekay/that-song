package com.spacekay.thatsong.domain.members.entity

enum class Gender(val nameKr: String) {
    WOMAN("여성"), MAN("남성"), ETC("기타");

    override fun toString(): String {
        return nameKr
    }
}