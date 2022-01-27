package io.velvetcreek.projectx.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import io.velvetcreek.projectx.Model.chuckNorris.Joke

// Use built in database inspector to check
@Database(entities = [Joke::class], version = 1) //, exportSchema = true)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun jokeDao(): JokeDao
}