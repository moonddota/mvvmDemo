package com.example.mvvmdemo.data

data class loginData(
        val admin: Boolean,
        val chapterTops: List<Any>,
        val collectIds:List<String>,
        val email:String,
        val icon:String,
        val id:String,
        val nickname:String,
        val password:String,
        val token:String,
        val publicName:String,
        val type:Int,
        val username:String
)