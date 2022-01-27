package io.velvetcreek.persistence

import androidx.room.*
import io.velvetcreek.projectx.Model.chuckNorris.Joke

@Dao
interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Query("SELECT * FROM Joke")
    suspend fun getAllJokes(): List<Joke>?

    // TODO: Fix key error
//    @Query("SELECT * FROM Joke WHERE id = :id_")
//    suspend fun getJoke(id_: String): Joke?

//    @Delete
//    suspend fun delete(id: String)
}