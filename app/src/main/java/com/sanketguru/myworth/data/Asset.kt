package com.sanketguru.myworth.data

import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.ForeignKey
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import android.os.Parcel
import android.os.Parcelable

//https://regexr.com/
/**
 * Created by sanket.sphere on 29-11-2018.
 */

data class AssetWithPercent(
        @PrimaryKey(autoGenerate = true) var uid: Int,
        @ColumnInfo(name = "_name") var name: String,
        @ColumnInfo(name = "desc") var description: String?,
        @ColumnInfo(name = "value") var value: Double,
        @ColumnInfo(name = "portFolioId") var portFolioId: Int,
        @ColumnInfo(name = "percent") var percent: Float
)
@Entity
data class Asset(
        @PrimaryKey(autoGenerate = true) var uid: Int,
        @ColumnInfo(name = "_name") var name: String,
        @ColumnInfo(name = "desc") var description: String?,
        @ColumnInfo(name = "value") var value: Double,
        @ForeignKey(
                entity = PortFolio::class,
                childColumns = ["portFolioId"],
                parentColumns = ["uid"],
                deferred = true,
                onDelete = ForeignKey.CASCADE
        )
        @ColumnInfo(name = "portFolioId") var portFolioId: Int=1
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt()
        )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uid)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeDouble(value)
        parcel.writeInt(portFolioId)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Asset> {
        override fun createFromParcel(parcel: Parcel): Asset {
            return Asset(parcel)
        }

        override fun newArray(size: Int): Array<Asset?> {
            return arrayOfNulls(size)
        }
    }
}

@Dao
interface AssetDao {
    @Query("SELECT * FROM asset ORDER BY value DESC")
    fun getAll(): List<Asset>

    @Query("select uid, _name, desc, value,portFolioId, (value*100 / (select sum(value) from asset)) as \"percent\" from  asset ")
    fun getAllWithPercent(): List<AssetWithPercent>

    @Query("SELECT * FROM asset WHERE uid IN (:userIds) ")
    fun loadAllByIds(userIds: IntArray): List<Asset>

    @Query("SELECT sum(value) FROM asset WHERE portFolioId == (:portFolioId) ")
    fun getPortFolioValue(portFolioId: Int): Double

    @Query("SELECT * FROM asset WHERE portFolioId == (:portFolioId)  ORDER BY value DESC")
    fun getAssetInPortFolio(portFolioId: Int): List<Asset>

    @Insert
    fun insertAll(vararg users: Asset)

    @Delete
    fun delete(user: Asset): Int

    @Update
    fun update(asset: Asset): Int
}

