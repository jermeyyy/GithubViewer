package pl.jermey.githubviewer.model

data class SearchResults<out T>(val items: List<T>,
                                val incompleteResults: Boolean,
                                val totalCount: Long)

