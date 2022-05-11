package fr.esipe.barrouxrodriguez.plutus.model

interface NoteBookRepository {
    fun listNoteBookIdentifiers(): List<String>
    fun createNoteBook(): NoteBook
    fun getNoteBook(identifier: String): NoteBook
    fun deleteNoteBook(identifier: String): Unit
}