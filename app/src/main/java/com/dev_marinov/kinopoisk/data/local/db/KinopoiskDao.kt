package com.dev_marinov.kinopoisk.data.local.db

import androidx.room.*
import com.dev_marinov.kinopoisk.data.remote.dto.KinopoiskResponseDTO

@Dao
interface KinopoiskDao {

    @Query("SELECT * FROM kinopoisk WHERE page LIKE :page")
    fun getData(page: String) : KinopoiskResponseDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(detail: List<DetailEntity>)

    @Delete
    fun delete(detailEntity: DetailEntity)

}