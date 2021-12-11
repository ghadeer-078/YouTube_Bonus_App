package com.example.youtubebonusapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

class GridVideosAdapter(
    private val videosList: List<Video>,
    private val player: YouTubePlayer,
    private val context: Context
) : BaseAdapter() {
    var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int = videosList.size

    override fun getItem(position: Int): Any = videosList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView

        if (layoutInflater == null)
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (convertView == null)
            convertView = layoutInflater!!.inflate(R.layout.item_video,parent,false)

        val button = convertView!!.findViewById<Button>(R.id.btVideo)
        button.text = videosList[position].title
        button.setOnClickListener {
            player.loadVideo(videosList[position].code, 0F)
        }

        return button

    }


}