package com.example.top10downloader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.Buffer


class MainActivity : AppCompatActivity() {
    private val TAG="Mainactivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"OnCreate called")

        val DownloadData=DownloadData()
        DownloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
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

            override fun doInBackground(vararg url: String): String {
                Log.d(TAG,"doInBackground: starts with ${url[0]}")
                val rssFeed=downloadXML(url[0])
                if(rssFeed.isEmpty())
                {
                    Log.e(TAG,"doInBackground:Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath:String):String{
                val xmlResult=StringBuffer()

                try {
                    val url= URL(urlPath)
                    val connection: HttpURLConnection =url.openConnection() as HttpURLConnection
                    val response=connection.responseCode
                    Log.d(TAG,"The response code was $response")

                    /*  val inputStream=connection.inputStream
                      val inputStreamReader=InputStreamReader(inputStream)
                      val reader=BufferedReader(inputStreamReader)
                     */
                    val reader=BufferedReader(InputStreamReader(connection.inputStream))

                    val inputBuffer=CharArray(500)
                    var charsRead=0
                    while(charsRead>=0)
                    {
                        charsRead=reader.read(inputBuffer)
                        if(charsRead>0)
                        {
                            xmlResult.append(String(inputBuffer,0,charsRead))
                        }

                    }
                    reader.close()
                    Log.d(TAG,"Receiver ${xmlResult.length} bytes")
                    return xmlResult.toString()
                }catch (e:MalformedURLException){
                    Log.e(TAG,"downloadXML: Invalid URL ${e.message}")
                }catch(e:IOException){
                    Log.e(TAG,"downloadXML:IO Exception reading data: ${e.message}")
                }catch(e:SecurityException)
                {
                    Log.e(TAG,"downloadXML: Security exception. Needs permition?: ${e.message}")
                }
                catch(e:Exception){
                    Log.e(TAG,"downloadXML:Unknown error: ${e.message}")
                }
                

                return "" //IF goes here it is some problem
            }

        }
    }

}