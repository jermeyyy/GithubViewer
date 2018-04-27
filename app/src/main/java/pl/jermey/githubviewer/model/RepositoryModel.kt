package pl.jermey.githubviewer.model

import android.databinding.BaseObservable

data class RepositoryModel(val id: Long,
                           val name: String,
                           val fullName: String,
                           val owner: UserModel,
                           val description: String,
                           val stargazersCount: Long,
                           val watchersCount: Long,
                           val forksCount: Long): BaseObservable()