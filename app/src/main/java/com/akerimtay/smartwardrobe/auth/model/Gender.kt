package com.akerimtay.smartwardrobe.auth.model

enum class Gender(val serializedName: String) {
    MALE("male"),
    FEMALE("female");

    companion object {
        fun toGender(value: String?): Gender = values().firstOrNull { value == it.serializedName } ?: MALE
    }
}