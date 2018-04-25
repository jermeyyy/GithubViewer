package pl.jermey.githubviewer.model

data class UserModel(val id: Long,
                     val login: String,
                     val avatarUrl: String,
                     val type: String)