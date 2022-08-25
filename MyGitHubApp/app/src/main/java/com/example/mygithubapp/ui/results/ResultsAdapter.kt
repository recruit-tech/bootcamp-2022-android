package com.example.mygithubapp.ui.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mygithubapp.R
import com.example.mygithubapp.data.UserInfomation
import com.squareup.picasso.Picasso

class ResultsAdapter() : RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {
    private var dataSet: List<UserInfomation> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userName: TextView = view.findViewById(R.id.user_name)
        val userScore: TextView = view.findViewById(R.id.user_score)
        val favorite: ImageButton = view.findViewById(R.id.imageFilterButton)
        val userIcon: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cassette_user_item , viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.userName.text = dataSet[position].name
        viewHolder.userScore.text = dataSet[position].score.toString()
        Picasso.get().load(dataSet[position].avaterUrl).into(viewHolder.userIcon)
    }

    override fun getItemCount() = dataSet.size

    fun setDataSet(list: List<UserInfomation>) {
        dataSet = list
        notifyDataSetChanged()
    }
}
