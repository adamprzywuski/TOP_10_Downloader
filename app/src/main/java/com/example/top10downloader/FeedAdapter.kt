package com.example.top10downloader

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class FeedAdapter(context: Context, private val resource:Int, private val applications:List<FeedEntry>)
    : ArrayAdapter<FeedEntry>(context,resource) {

    private val TAG="FeedAdapter"
    private val inflater=LayoutInflater.from(context)

    override fun insert(`object`: FeedEntry?, index: Int) {
        super.insert(`object`, index)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        Log.d(TAG,"getView() called")

        val view:View
        view = if(convertView==null) {
            Log.d(TAG,"getView called with null convertView")
            inflater.inflate(resource, parent, false)
        } else { Log.d(TAG,"GetView provide with null convertView ")
            convertView

        }
        val tvName: TextView =view.findViewById(R.id.tvName)
        val tvArtist: TextView =view.findViewById(R.id.tvArtist)
        val tvSummary: TextView =view.findViewById(R.id.tvSummary)

        val curreentApp=applications[position]

        tvName.text=((position+1).toString()+". "+curreentApp.name)
        tvArtist.text=curreentApp.artist
        tvSummary.text=curreentApp.summary

        return view
    }

    override fun getCount(): Int {
        Log.d(TAG,"getCount() called")
        return applications.size
    }
}