package com.berslenakkaya.videogame

data class Token (
    val success:Boolean,
    val expires_at:String,
    val request_token:String
)