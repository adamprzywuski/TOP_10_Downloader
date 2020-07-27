package com.example.top10downloader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TAG="Mainactivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"OnCreate called")

        val DownloadData=DownloadData()
        DownloadData.execute("URL goes here")
        Log.d(TAG,"onCreate:Done")
    }

    companion object {
        private class DownloadData: AsyncTask<String, Void, String>()
        {
            private val TAG="DownloadData"
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Log.d(TAG,"OnPostExcectuive parameter is $result")
            }

            override fun doInBackground(vararg params: String?): String {
                Log.d(TAG,"doInBackground: starts with ${params[0]}")
                return "DoInBackground completed"
            }

        }
    }
}