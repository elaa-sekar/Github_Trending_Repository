package com.demo.trendinggithubrepo.data.api_models

import com.google.gson.annotations.SerializedName

data class Contributor(
    @SerializedName("href") var userProfileLink: String?,
    @SerializedName("username") var userName: String?,
    @SerializedName("avatar") var iconUrl: String?
)