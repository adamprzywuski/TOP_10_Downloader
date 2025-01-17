package com.example.top10downloader

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class ViewHolder(v:View)
{
    val tvName:TextView=v.findViewById(R.id.tvName)
    val tvArtist:TextView=v.findViewById(R.id.tvArtist)
    val tvSummary:TextView=v.findViewById(R.id.tvSummary)
}


class FeedAdapter(context: Context, private val resource:Int, private val applications:List<FeedEntry>)
    : ArrayAdapter<FeedEntry>(context,resource) {


    private val inflater=LayoutInflater.from(context)

    override fun insert(`object`: FeedEntry?, index: Int) {
        super.insert(`object`, index)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder:ViewHolder


        val view:View
        if(convertView==null) {

            view = inflater.inflate(resource, parent, false)
            viewHolder=ViewHolder(view)
            view.tag=viewHolder

        } else {
            view = convertView
            viewHolder=view.tag as ViewHolder

        }
      //  val tvName: TextView =view.findViewById(R.id.tvName)
      //  val tvArtist: TextView =view.findViewById(R.id.tvArtist)
     //   val tvSummary: TextView =view.findViewById(R.id.tvSummary)


        val curreentApp=applications[position]

            viewHolder.tvName.text=((position+1).toString()+". "+curreentApp.name)
            viewHolder.tvArtist.text=curreentApp.artist

        viewHolder.tvSummary.text=curreentApp.summary

        return view
    }

    override fun getCount(): Int {

        return applications.size
    }
}