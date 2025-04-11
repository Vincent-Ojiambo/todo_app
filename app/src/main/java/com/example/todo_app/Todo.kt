//import android.os.Parcel
//import android.os.Parcelable
//import org.json.JSONObject
//
//data class Todo(val id: Int, val userId: Int, val title: String, val completed: Boolean) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readInt(),
//        parcel.readString()!!,
//        parcel.readByte() != 0.toByte()
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(id)
//        parcel.writeInt(userId)
//        parcel.writeString(title)
//        parcel.writeByte(if (completed) 1 else 0)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Todo> {
//        override fun createFromParcel(parcel: Parcel): Todo {
//            return Todo(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Todo?> {
//            return arrayOfNulls(size)
//        }
//
//        fun fromJson(jsonObject: JSONObject): Todo {
//            return Todo(
//                jsonObject.getInt("id"),
//                jsonObject.getInt("userId"),
//                jsonObject.getString("title"),
//                jsonObject.getBoolean("completed")
//            )
//        }
//    }
//}


import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

data class Todo(val id: Int, val userId: Int, val title: String, val completed: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(userId)
        parcel.writeString(title)
        parcel.writeByte(if (completed) if (completed) 1 else 0 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Todo> {
        override fun createFromParcel(parcel: Parcel): Todo {
            return Todo(parcel)
        }

        override fun newArray(size: Int): Array<Todo?> {
            return arrayOfNulls(size)
        }

        fun fromJson(jsonObject: JSONObject): Todo {
            return Todo(
                jsonObject.getInt("id"),
                jsonObject.getInt("userId"),
                jsonObject.getString("title"),
                jsonObject.getBoolean("completed")
            )
        }
    }
}