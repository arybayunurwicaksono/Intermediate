package com.dguitarclassic.intermediate.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dguitarclassic.intermediate.Activities.DetailStoryActivity
import com.dguitarclassic.intermediate.Response.ListStoryItem
import com.dguitarclassic.intermediate.databinding.LayoutStoryItemBinding

class StoryListAdapter: PagingDataAdapter<ListStoryItem, StoryListAdapter.storyAdapter>(DIFF_CALLBACK) {
    inner class storyAdapter (val binding: LayoutStoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): storyAdapter {
        val binding = LayoutStoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return storyAdapter(binding)
    }

    override fun onBindViewHolder(holder: storyAdapter, position: Int) {
        val story = getItem(position)
        holder.binding.tvStoryUsername.text = story!!.name
        holder.binding.tvStoryDesc.text = story.description
        Glide.with(holder.itemView.context)
            .load(story.photoUrl)
            .fitCenter()
            .into(holder.binding.imgStoryImage)
        holder.itemView.setOnClickListener {
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.binding.imgStoryImage, "story_image"),
                    Pair(holder.binding.tvStoryUsername, "username"),
                    Pair(holder.binding.tvStoryDesc, "description")
                )

            val detailIntent = Intent(holder.itemView.context, DetailStoryActivity::class.java)
            detailIntent.putExtra(DetailStoryActivity.EXTRA_STORY, story)
            holder.itemView.context.startActivity(detailIntent, optionsCompat.toBundle())

        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}