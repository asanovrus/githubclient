package org.eu5.githubclient.model

import android.content.Context
import java.io.*

class FileIO(context: Context, fileNm:String = "fileRate.txt"){
    var fileRate:File = File(context.getFilesDir(), fileNm)
    fun writeInternal(data: String){
        try{
            PrintWriter(fileRate).use { out -> out.println(data) }
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    fun readInternal():String?{
        var jsonNode: String? = null
        if(fileRate.exists()){
            try{
                jsonNode = fileRate.readText()
            }catch (e:java.lang.Exception){
                e.printStackTrace()
            }
        }
        return jsonNode
    }
}
