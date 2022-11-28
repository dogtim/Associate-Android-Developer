package com.example.associate.training

import android.os.Parcel
import android.os.Parcelable
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * To have a custom model object which implements the interface Parcelable in order to pass objects between components
 * (activities, fragments, services, etc…)
 * See [medium](https://medium.com/@dbottillo/android-junit-parcelable-model-test-37a2f2ae18b1).
 */
@RunWith(AndroidJUnit4::class)
class ParcelableTest {

    @Test
    fun object_equal() {
        val book = Book("author", "title")

        val parcel = Parcel.obtain()
        book.writeToParcel(parcel, book.describeContents())
        parcel.setDataPosition(0)

        val createdFromParcel = Book.createFromParcel(parcel)
        assertEquals("author", createdFromParcel.author)
        assertEquals("title", createdFromParcel.title)
    }

    @Test
    fun test_subclass() {
        val book = SubBook("author", "title", true)

        val parcel = Parcel.obtain()
        book.writeToParcel(parcel, book.describeContents())
        parcel.setDataPosition(0)

        val createdFromParcel = SubBook.createFromParcel(parcel)
        assertEquals("author", createdFromParcel.author)
        assertEquals("title", createdFromParcel.title)
        assertEquals(true, createdFromParcel.isAdult)
    }
}

open class Book : Parcelable {
    val author: String
    val title: String

    constructor(author: String, title: String) {
        this.author = author
        this.title = title
    }

    constructor(`in`: Parcel) {
        author = `in`.readString().toString()
        title = `in`.readString().toString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(author)
        dest.writeString(title)
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel) = Book(parcel)
        override fun newArray(size: Int): Array<Book?> = arrayOfNulls(size)
    }
}

class SubBook : Book {
    var isAdult: Boolean = false

    constructor(author: String, title: String, isAdult: Boolean) : super(author, title) {
        this.isAdult = isAdult
    }

    constructor(`in`: Parcel) : super(`in`) {
        isAdult = `in`.readBoolean()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeBoolean(isAdult)
    }

    companion object CREATOR : Parcelable.Creator<SubBook> {
        override fun createFromParcel(parcel: Parcel) = SubBook(parcel)
        override fun newArray(size: Int): Array<SubBook?> = arrayOfNulls(size)
    }
}