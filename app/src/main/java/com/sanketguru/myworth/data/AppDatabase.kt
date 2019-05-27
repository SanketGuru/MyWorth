package com.sanketguru.myworth.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by sanket.sphere on 12-12-2018.
 */
@Database(
        entities = [
            Asset::class,
            PortFolio::class,
            Liability::class
        ],
        version = 3,
        exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun portfolioDao(): PortfolioDao
    abstract fun liabilityDao(): LiabilityDao
}