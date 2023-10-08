package com.example.data.dto

internal enum class AuthorAssociation(val value: String) {
    COLLABORATOR("COLLABORATOR"),
    CONTRIBUTOR("CONTRIBUTOR"),
    NONE("NONE"),
    OWNER("OWNER"),
}
