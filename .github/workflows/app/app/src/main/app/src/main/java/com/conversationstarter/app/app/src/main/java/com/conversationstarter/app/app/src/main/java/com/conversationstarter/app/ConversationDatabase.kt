package com.conversationstarter.app

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {
    @Query("SELECT * FROM conversation_questions ORDER BY createdAt DESC")
    fun getAllQuestions(): LiveData<List<ConversationQuestion>>
    
    @Query("SELECT * FROM conversation_questions WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomQuestionByCategory(category: String): ConversationQuestion?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: ConversationQuestion): Long
    
    @Update
    suspend fun updateQuestion(question: ConversationQuestion)
}

@Database(
    entities = [ConversationQuestion::class],
    version = 1,
    exportSchema = false
)
abstract class ConversationDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
}
