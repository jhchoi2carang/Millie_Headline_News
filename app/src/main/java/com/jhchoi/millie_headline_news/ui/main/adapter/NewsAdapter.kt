package com.jhchoi.millie_headline_news.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jhchoi.millie_headline_news.R
import com.jhchoi.millie_headline_news.network.News

class NewsAdapter(private val items: List<News>, private val onNewsItemClickListener: ((String)-> Unit)) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
//    interface onNewsItemClickListener {
//        fun onItemClick(url: String)
//    }

    private var itemList: ArrayList<News> = ArrayList()
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val ivImage : ImageView = itemView.findViewById(R.id.ivNews)
        val tvPublished :TextView = itemView.findViewById(R.id.tvPublished)
    }

    private var selectedPositions: MutableSet<Int> = HashSet()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        itemList.addAll(items)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.tvTitle.text = item.title
        holder.tvPublished.text = item.publishedAt
        Glide.with(holder.ivImage)
            .load(item.urlToImage)
            .placeholder(R.drawable.no_image) // 이미지 로딩 중에 보여질 임시 이미지
            .centerCrop() // 이미지를 중앙으로 크롭
            .diskCacheStrategy(DiskCacheStrategy.ALL) // 캐싱 전략 설정
            .into(holder.ivImage)
        holder.itemView.setOnClickListener {
            onNewsItemClickListener.invoke(item.url)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setData(items: List<News>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }
}
