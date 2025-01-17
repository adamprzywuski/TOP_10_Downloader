package com.example.top10downloader

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.properties.Delegates

class FeedEntry {
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageURL: String = ""

    override fun toString(): String {
        return """"
            name= $name
            artist= $artist
            releaseDate= $releaseDate
            imageURL= $imageURL
            """.trimIndent()
    }


}


class MainActivity : AppCompatActivity() {
    private val TAG = "Mainactivity"


    private var downloadData: DownloadData? = null
    private var feedUrl: String =
        "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
    private var feedLimit: Int = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            feedLimit = savedInstanceState.getInt("feedLimit")
            feedUrl = savedInstanceState.getString("feedUrl")!!
        }
        downloadUrl(feedUrl.format(feedLimit))
        Log.d(TAG, "Oncreate: done")


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)


        Log.d(TAG, "Data has been realaes")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {

        savedInstanceState.putInt("feedLimit", feedLimit)
        savedInstanceState.putString("feedUrl", feedUrl)
        savedInstanceState.putBoolean("blocked", true)
        Log.d(TAG, "Data has been saved")
        super.onSaveInstanceState(savedInstanceState)
    }


    private fun downloadUrl(feedUrl: String) {
        Log.d(TAG, "downloadUrl starting AsyncTask")
        val DownloadData = DownloadData(this, xmlListView)
        downloadData = DownloadData(this, xmlListView)
        DownloadData.execute(feedUrl)
        Log.d(TAG, "downloadUrl :Done")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)

        if (feedLimit == 10) {
            menu.findItem(R.id.mnu10)?.isChecked = true
        } else {
            menu.findItem(R.id.mnu25)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.mnuFree ->
                feedUrl =
                    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml"
            R.id.mnuPaid ->
                feedUrl =
                    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml"
            R.id.mnuSongs ->
                feedUrl =
                    "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml"
            R.id.mnu10, R.id.mnu25 -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    feedLimit = 35 - feedLimit
                    Log.d(
                        TAG,
                        "onOptionItemSelected: ${item.title} setting feedLimit to $feedLimit"
                    )
                } else {
                    Log.d(TAG, "onOptionItemSelected: ${item.title} setting feedLimit to onchanged")
                }

            }
            else ->
                return super.onOptionsItemSelected(item)
        }
        downloadUrl(feedUrl.format(feedLimit))
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        downloadData?.cancel(true)

    }


    companion object {
        private class DownloadData(context: Context, listView: ListView) :
            AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            var propContext: Context by Delegates.notNull()
            var propListView: ListView by Delegates.notNull()

            init {
                propContext = context
                propListView = listView
            }


            override fun onPostExecute(result: String) {
                super.onPostExecute(result)

                val parseApplications = ParseApplications()
                parseApplications.parse(result)


                val feedAdapter =
                    FeedAdapter(propContext, R.layout.list_record, parseApplications.applications)
                propListView.adapter = feedAdapter

            }

            override fun doInBackground(vararg url: String): String {
                Log.d(TAG, "doInBackground: starts with ${url[0]}")
                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground:Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String): String {
                return URL(urlPath).readText()
            }

        }
    }
}