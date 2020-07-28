package com.example.top10downloader

import android.util.Log
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

class ParseApplications {
    private val TAG="ParseApplications"
    val applications=ArrayList<FeedEntry>()

    fun parse(xmlData:String):Boolean{
        Log.d(TAG,"parse called with $xmlData")
        var status=false
        var inEntry=false
        var textValue=""

        try {
            val factory=XmlPullParserFactory.newInstance()
            factory.isNamespaceAware=true
            val xpp=factory.newPullParser()
            xpp.setInput(xmlData.reader())
            var evenType=xpp.eventType
            var currentRecord=FeedEntry()
            while(evenType!= XmlPullParser.END_DOCUMENT){

            }

        }catch (e:Exception)
        {e.printStackTrace()
        status=false}
    }
}