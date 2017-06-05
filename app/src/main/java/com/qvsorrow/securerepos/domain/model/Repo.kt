package com.qvsorrow.securerepos.domain.model


data class Repo(
        val id: Int,
        val name: String,
        val description: String,
        val htmlUrl: String,
        val owner: Owner
)


data class Owner(val login: String)
