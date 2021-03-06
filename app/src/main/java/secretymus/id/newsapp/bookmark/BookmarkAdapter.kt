package secretymus.id.newsapp.bookmark

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_news.view.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.databinding.ItemNewsBookmarkBinding
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.model.Source
import secretymus.id.newsapp.news.ItemClickListener
import secretymus.id.newsapp.views.NewsListFragmentDirections

class BookmarkAdapter(
        private val articleList: ArrayList<Article>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        ItemClickListener {
    private val dummyArticle = Article(
            null,
            "",
            "",
            "",
            "",
            "",
            "",
            "")

    fun retrieveBookmarkList(nArticleList: List<Article>) {
        articleList.clear()
        articleList.addAll(nArticleList)
        articleList.add(dummyArticle)
        articleList.removeAt(articleList.lastIndex)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(DataBindingUtil.inflate(inflater, R.layout.item_news_bookmark, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            holder.view.article = articleList[position]
            holder.view.listener = this
        }
    }

    class NewsViewHolder(var view: ItemNewsBookmarkBinding) : RecyclerView.ViewHolder(view.root)

    override fun getItemCount(): Int = articleList.size

    override fun onNewsClicked(view: View) {
        val titles = view.titleText.text.toString()
        val article = Article(
                source = Source(id = "b_c_v", name = titles),
                author = view.author.text.toString(),
                title = titles,
                description = view.description.text.toString(),
                url = view.url.text.toString(),
                urlToImage = view.urlText.text.toString(),
                publishedAt = view.publishTimeText.text.toString(),
                content = view.contentText.text.toString()
        )
        val action = NewsListFragmentDirections.actionDetailFragment(article)
        Navigation.findNavController(view).navigate(action)
    }

    override fun onBookmarkClicked(view: View) {
        //No Implement
    }
}


