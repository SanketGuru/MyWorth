package com.sanketguru.myworth.data

import androidx.room.*
import android.nfc.tech.NfcA
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by sanket.sphere on 11-12-2018.
 */
data class PortFolioValue(@PrimaryKey(autoGenerate = true) val uid: Int,
                     @ColumnInfo(name = "_name") val name: String = "",
                     @ColumnInfo(name = "desc") val desc: String = "Hi",
                     @ColumnInfo(name = "tValue") val value: Double
                          )
@Entity
data class PortFolio(@PrimaryKey(autoGenerate = true) val uid: Int,
                     @ColumnInfo(name = "_name") val name: String = "",
                     @ColumnInfo(name = "desc") val desc: String = "Hi") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uid)
        parcel.writeString(name)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PortFolio> {
        override fun createFromParcel(parcel: Parcel): PortFolio {
            return PortFolio(parcel)
        }

        override fun newArray(size: Int): Array<PortFolio?> {
            return arrayOfNulls(size)
        }
    }
}
@Dao
interface PortfolioDao {
    @Query("SELECT * FROM portfolio")
    fun getAll(): List<PortFolio>

    @Query("SELECT * FROM portfolio WHERE uid IN (:userIds) ")
    fun loadAllByIds(userIds: IntArray): List<PortFolio>
    @Query("SELECT SUM(value) FROM asset WHERE portFolioId = (:userIds) ")
    fun loadAllByIdsValue(userIds: Int): Double


    @Query("select p1.uid, p1._name,p1.desc,(select sum(value) from  asset  where PortFolioId == p1.uid) as \"tValue\"  from portfolio as p1")

    fun loadAllByIdsValueTotal(): List<PortFolioValue>

    @Insert
    fun insertAll(vararg users: PortFolio)

    @Delete
    fun delete(user: PortFolio): Int

    @Update
    fun update(asset: PortFolio): Int
}