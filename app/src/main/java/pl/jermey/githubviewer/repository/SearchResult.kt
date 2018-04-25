package pl.jermey.githubviewer.repository

data class SearchResult<out T>(val items: List<T>,
                               val incompleteResults: Boolean,
                               val totalCount: Long)

