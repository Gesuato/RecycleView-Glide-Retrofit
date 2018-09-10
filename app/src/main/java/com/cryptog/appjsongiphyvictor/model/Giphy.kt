package com.cryptog.appjsongiphyvictor.model

import java.io.Serializable

data class Giphy(val images : Images) : Serializable{

}

data class Images(val original : OriginalGiphy) : Serializable

data class OriginalGiphy(val url : String , val width : Int , val height : Int) : Serializable
