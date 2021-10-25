package com.example.morasel.room

import androidx.room.TypeConverter
import com.example.morasel.pojo.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}