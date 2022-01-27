package io.velvetcreek.projectx.Model.chuckNorris

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Joke(
    @PrimaryKey val id: String,
    @ColumnInfo val icon_url: String?,
    @ColumnInfo val url: String?,
    @ColumnInfo val value: String?,
)