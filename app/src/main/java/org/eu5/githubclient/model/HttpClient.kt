package org.eu5.githubclient.model

import android.app.Activity
import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.eu5.githubclient.*
import java.lang.Exception

class HttpClient(file:String = "fileRate.txt") {
    var filem = file
    fun get(caller: Activity, isJson:Boolean = false, urlm:String) {
        val fileio = FileIO(caller, filem)
        var responseBody:String? = null
        try{
            responseBody = fileio.readInternal()
        }catch (e:Exception){
            Log.i(tag, " file problem: ${e.message}")
        }
        if(responseBody!=null){
            extractFromResponseBodyRate(responseBody)
            caller.runOnUiThread(java.lang.Runnable {
                showData(caller)
            })
        }
        val client = OkHttpClient()
        var bldr = Request.Builder()
        val request = bldr.url(urlm).get().build()
        var response: Response? = null
        try {
            response = client.newCall(request).execute()
            // text response from server
            responseBody = response?.body!!.string()
            fileio.writeInternal(responseBody)
            ifOffline = false
        }catch (e:Exception){
            ifOffline = true
            Log.i(tag, " problem: ${e.message}")
        }
        if(isJson){
            extractFromResponseBodyRate(responseBody)
            caller.runOnUiThread(java.lang.Runnable {
                showData(caller)
            })
        }
    }

    fun showData(caller:Activity){
        (caller as MainActivity).updateView()
    }

    fun extractFromResponseBodyRate(responseBody: String?){
        if(responseBody != null){
            val mapperAll = ObjectMapper()
            val objData = mapperAll.readTree(responseBody)
            Log.i(tag, " _____objData: ${objData}")
            try {
                login = objData?.get("login").toString()
                id = objData?.get("id").toString()
                repos = objData?.get("repos").toString()
                followers = objData?.get("followers").toString()
            }catch (e:Exception){
                Log.i(tag, " error parsing $urlfull response: ${e.message}")
            }
        }
    }
}