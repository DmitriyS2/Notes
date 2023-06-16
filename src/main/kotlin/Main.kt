import java.lang.IndexOutOfBoundsException

class User(
    val name: String = "name",
    val notes: MutableList<Notes> = mutableListOf()
) {
    fun add(note: Notes): Boolean {
        ChangeItem.addItem(note, this.notes)
        return true
    }

    fun delete(index: Int): Boolean {
        ChangeItem.deleteItem(index, this.notes)
        for (item: Comments in notes[index].comments) {
            item.isAlive = false
        }
        return true
    }


    fun get() {
        ChangeItem.getItem(notes)
    }

    fun getById(index: Int) {
        try {
            println(this.notes[index])
        } catch (e: IndexOutOfBoundsException) {
            println("Invalid index")
        }
    }

    override fun toString(): String {
        return "name = $name, notes = $notes"
    }
}

object ChangeItem {

    fun <T> addItem(item: T, collection: MutableList<T>): Boolean {
        collection.add(item)
        return true
    }

    fun <T : NotesAndComments> deleteItem(index: Int, collection: MutableList<T>): Boolean {
        try {
            collection[index].isAlive = false
            return true
        } catch (e: IndexOutOfBoundsException) {
            println("Invalid index")
        }
        return false
    }

    fun <T : NotesAndComments> getItem(collection: MutableList<T>): Boolean {
        for (item: T in collection) {
            if (item.isAlive) {
                println(item.toString())
            }
        }
        return true
    }
}

open class NotesAndComments(
    val id: Int = 0,
    var title: String = "title",
    var text: String = "text",
    var isAlive: Boolean = true
)

class Notes(
    var comments: MutableList<Comments> = mutableListOf()
) : NotesAndComments() {
    fun createComment(comment: Comments): Boolean {
        ChangeItem.addItem(comment, this.comments)
        return true
    }

    fun deleteComment(index: Int): Boolean {
        ChangeItem.deleteItem(index, this.comments)
        return true
    }

    fun getComments() {
        ChangeItem.getItem(comments)
    }

    fun edit(title: String, text: String): Boolean {
        if (isAlive) {
            this.title = title
            this.text = text
            return true
        }
        return false
    }

    fun restoreComment(index: Int): Boolean {
        try {
            if (!comments[index].isAlive) {
                comments[index].isAlive = true
                return true
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Invalid index")
        }
        return false
    }

    override fun toString(): String {
        return "title = $title, text = $text, isAlive = $isAlive, comments = " + this.comments.toString()
    }
}

class Comments(
    var message: String = "message"
) : NotesAndComments() {

    fun editComment(message: String): Boolean {
        if (isAlive) {
            this.message = message
            return true
        }
        return false
    }

    override fun toString(): String {
        return if (isAlive) "message = $message, isAlive = $isAlive" else "deleted comment"
    }
}

fun main(args: Array<String>) {
    val user1 = User()
    println(user1)

    val comment1 = Comments(message = "Hello")
    val comment2 = Comments(message = "Hello2")
    val comment3 = Comments(message = "Hello3")
    val note1 = Notes()
    user1.add(note1)
    println(user1)

    println(note1.comments)
    //ChangeItem.addItem(comment1, note1.comments)
    note1.createComment(comment1)
    println(note1.comments)
    println(user1)

    note1.createComment(comment2)
    note1.createComment(comment3)
    note1.getComments()
    note1.deleteComment(5)
    note1.deleteComment(1)
    note1.getComments()
    note1.restoreComment(1)
    note1.getComments()
    user1.get()

    val note2 = Notes()
    val comment4 = Comments(message = "Bye!")
    note2.createComment(comment4)
    note2.getComments()
    user1.add(note2)
    user1.get()
    user1.delete(1)
    user1.get()

}