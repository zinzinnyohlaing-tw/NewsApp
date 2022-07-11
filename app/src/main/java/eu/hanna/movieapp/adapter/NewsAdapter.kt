package eu.hanna.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.hanna.movieapp.databinding.NewsArticleItemBinding
import eu.hanna.movieapp.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsArticleViewHolder>() {

  //  private var onItemClickListener: ((Article) -> Unit)? = null

 /*   fun setItemClickListener(listener:(Article) -> Unit) {
        onItemClickListener = listener
    }*/

    private var articleList = ArrayList<Article>()
    lateinit var onItemClick:((Article) -> Unit)

    fun setArticle(articleList: ArrayList<Article>){
        this.articleList = articleList
    }

    class NewsArticleViewHolder(val itemBinding: NewsArticleItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // To compare two lists and update only the update list
    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
       return NewsArticleViewHolder(NewsArticleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        Glide.with(holder.itemView)
            .load(article.urlToImage)
            .into(holder.itemBinding.ivArticleImage)

        holder.itemBinding.tvTitle.text = article.title
        holder.itemBinding.tvSource.text = article.source.name
        holder.itemBinding.tvPublishedAt.text = article.publishedAt

       /* holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(article)
            }
        }*/
        holder.itemView.setOnClickListener {
            onItemClick.invoke(article)
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}