package io.velvetcreek.projectx.persistence

import androidx.room.*
import io.velvetcreek.projectx.Model.chuckNorris.Joke

@Dao
interface JokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Query("SELECT * FROM Joke")
    suspend fun getAllJokes(): List<Joke>?

    @Query("SELECT * FROM Joke WHERE id = :id")
    suspend fun getJoke(id: String): Joke?

//    @Insert & @Delete // Need to use entity or collection/array for @Insert/@Delete
    @Query("DELETE FROM Joke WHERE id = :id")
    suspend fun delete(id: String)
}