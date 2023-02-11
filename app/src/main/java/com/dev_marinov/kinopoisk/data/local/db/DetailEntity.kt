package com.dev_marinov.kinopoisk.data.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class DetailEntity(
    @PrimaryKey
    @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "temp_c") val temp_c: String?,
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "wind_kph") val wind_kph: String?,
    @ColumnInfo(name = "humidity") val humidity: String?,
    @ColumnInfo(name = "cloud") val cloud: String?
) {

    //  DetailEntity - слой дата, Detail- домен
//    companion object {
//        fun mapFromDomainToData(detail: Detail): DetailEntity {
//            return DetailEntity(
//                key = detail.city + detail.time?.substring(11, detail.time.length), // чтобы не перезаписывались объекты, а был список объетов
//                city = detail.city,
//                time = detail.time,
//                temp_c = detail.temp_c,
//                icon = detail.condition.icon,
//                text = detail.condition.text,
//                wind_kph = detail.wind_kph,
//                humidity = detail.humidity,
//                cloud = detail.cloud
//            )
//        }
//    }

//    fun mapToDomain(): Detail {
//        return Detail(
//            city = city,
//            time = time,
//            temp_c = temp_c,
//            condition = Condition(text, icon),
//            wind_kph = wind_kph,
//            humidity = humidity,
//            cloud = cloud
//        )
//    }
}