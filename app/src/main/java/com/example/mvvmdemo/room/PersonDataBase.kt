package com.example.mvvmdemo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1)
abstract class PersonDataBase : RoomDatabase(){

    abstract fun personDao(): PersonDao

    companion object {
        @Volatile
        private var sInstance: PersonDataBase? = null
        private const val DATA_BASE_NAME = "jetpack_person.db"

        @JvmStatic
        fun getInstance(context: Context): PersonDataBase? {
            if (sInstance == null) {
                synchronized(PersonDataBase::class.java) {
                    if (sInstance == null) {
//                        sInstance = createInstance(context)
                    }
                }
            }
            return sInstance
        }

//        private fun createInstance(context: Context): PersonDataBase {
//            return Room.databaseBuilder(context.applicationContext, PersonDataBase::class.java, DATA_BASE_NAME)
//            ...
//            .build()
//        }
    }
}