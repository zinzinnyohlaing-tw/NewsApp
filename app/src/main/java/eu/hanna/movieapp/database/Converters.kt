package eu.hanna.movieapp.database

import androidx.room.TypeConverter
import eu.hanna.movieapp.models.Source

class Converters {

    @TypeConverter
    fun fromSourceToString(source: Source): String{
        return source.name
    }

    @TypeConverter
    fun fromStringToSource(name: String): Source {
        return Source(name,name)
    }
}