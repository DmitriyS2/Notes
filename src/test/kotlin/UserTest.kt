import org.junit.Assert.*
import java.lang.IndexOutOfBoundsException
import kotlin.test.Test

class UserTest {

    @Test
    fun addNote() {
        val note1 = Notes()
        val notes: MutableList<Notes> = mutableListOf()
        val result = ChangeItem.addItem(note1, notes)
        assertTrue(result)
    }

    @Test
    fun deleteNote() {
        val note1 = Notes()
        val notes: MutableList<Notes> = mutableListOf()
        notes.add(note1)
        val result = ChangeItem.deleteItem(0, notes)
        assertTrue(result)
    }

    @Test
    fun deleteNoteInvalidIndex() {
        val notes: MutableList<Notes> = mutableListOf()
        val result = ChangeItem.deleteItem(5, notes)
    }

    @Test
    fun editNote() {
        val note1 = Notes()
        //note1.isAlive = false
        val result = note1.edit("new title", "new text")
        assertTrue(result)
    }

    @Test
    fun getListComments() {
        val comment1 = Comments()
        //val comment2 = Comments()
        val note1 = Notes()
        note1.createComment(comment1)
        val result = note1.getComment()
        val notes: MutableList<Comments> = mutableListOf(comment1)
        assertEquals(notes, result)
    }

    @Test
    fun restoreDeletedComment() {
        val comment1 = Comments(message = "Hello")
        val note1 = Notes()
        note1.createComment(comment1)
        note1.deleteComment(0)
        val result = note1.restoreComment(0)
        assertTrue(result)

    }

    @Test
    fun restoreDeletedCommentInvalidIndex() {
        val comment1 = Comments(message = "Hello")
        val note1 = Notes()
        note1.createComment(comment1)
        note1.deleteComment(0)
        val result = note1.restoreComment(5)

    }
}