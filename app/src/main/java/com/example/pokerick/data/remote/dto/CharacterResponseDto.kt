package com.example.pokerick.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CharacterResponseDto(
    @SerializedName("info") val info: InfoResponseDto,
    @SerializedName("results") val results: List<CharacterDto>
)