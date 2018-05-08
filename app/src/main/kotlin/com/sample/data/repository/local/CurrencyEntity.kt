package com.sample.data.repository.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = LocalContract.TABLE_CURRENCIES)
data class CurrencyEntity(
        @PrimaryKey(autoGenerate = true) val id: Long,
        var countryCode: String,
        var countryName: String
)

