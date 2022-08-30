package com.example.mvvmdemo.room

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonDao {
    @Insert
    fun insert(vararg person: Person?): LongArray?

    @Delete
    fun delete(person: Person?): Int

    @Update
    fun update(vararg person: Person?): Int

    @get:Query("SELECT * FROM person")
    val allMovies: LiveData<List<Person?>?>
}