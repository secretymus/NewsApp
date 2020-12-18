package secretymus.id.newsapp.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URL

interface NewsApi {
    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Single<News>
}