package com.sanketguru.myworth.data

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by sanket.sphere on 31-12-2018.
 */
@Entity
data class Liability(@PrimaryKey(autoGenerate = true) val uid: Int,
                     @ColumnInfo(name = "_name") val name: String,
                     @ColumnInfo(name = "desc") val desc: String,
                     @ColumnInfo(name = "value") val emi: Double,
                     @ColumnInfo(name = "noEmi") val noOfEmi: Int,
                     @ColumnInfo(name = "dueDate") val dateOfMonth: Short,
                     @ColumnInfo(name = "calculate") val calculateInGrand: Boolean) : Parcelable {

    fun isEmi(): Boolean = noOfEmi > 1

    fun value(): Double = if (isEmi()) {
        emi * noOfEmi
    } else emi

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readInt().toShort(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uid)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeDouble(emi)
        parcel.writeInt(noOfEmi)
        parcel.writeInt(dateOfMonth.toInt())
        parcel.writeByte(if (calculateInGrand) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Liability> {
        override fun createFromParcel(parcel: Parcel): Liability {
            return Liability(parcel)
        }

        override fun newArray(size: Int): Array<Liability?> {
            return arrayOfNulls(size)
        }
    }
}

@Dao
interface LiabilityDao {
    @Query("SELECT * FROM liability")
    fun getAll(): List<Liability>

    @Query("SELECT * FROM liability WHERE uid IN (:userIds) ")
    fun loadAllByIds(userIds: IntArray): List<Liability>

    @Insert
    fun insertAll(vararg users: Liability)

    @Delete
    fun delete(user: Liability): Int

    @Update
    fun update(asset: Liability): Int
}