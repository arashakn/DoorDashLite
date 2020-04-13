package com.doordash.models

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("cover_img_url")
    val coverImageUrl: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("delivery_fee")
    val deliveryFee: Int
)

