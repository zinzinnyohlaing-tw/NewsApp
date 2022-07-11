package eu.hanna.movieapp.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)