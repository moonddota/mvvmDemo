package com.example.mvvmdemo.room

import androidx.room.*

@Entity(indices = [Index("name", "password", unique = true)])
data class Person(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val password: String
) {
    @Ignore
    var sex: String? = null
}