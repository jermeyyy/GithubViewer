package pl.jermey.githubviewer.model

import android.databinding.BaseObservable

data class UserModel(val id: Long,
                     val login: String,
                     val avatarUrl: String,
                     val type: String) : BaseObservable()