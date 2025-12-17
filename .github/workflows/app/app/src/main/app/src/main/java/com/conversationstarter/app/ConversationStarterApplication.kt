package com.conversationstarter.app

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ConversationStarterApplication : Application() {
    
    val applicationScope = CoroutineScope(SupervisorJob())
    
    val database by lazy { 
        Room.databaseBuilder(
            this,
            ConversationDatabase::class.java,
            "conversation_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                applicationScope.launch(Dispatchers.IO) {
                    populateDatabase()
                }
            }
        }).build()
    }
    
    private suspend fun populateDatabase() {
        val dao = database.questionDao()
        
        // Sample questions for each category
        val questions = listOf(
            ConversationQuestion(
                category = "CHILDREN",
                question = "If you could have any superpower, what would it be and why?",
                description = "A fun way to explore imagination and values",
                difficulty = 1
            ),
            ConversationQuestion(
                category = "TEENS", 
                question = "What's something you believe that most people your age don't?",
                description = "Explores individual thinking and values",
                difficulty = 2
            ),
            ConversationQuestion(
                category = "PARENTS",
                question = "What's one thing you wish you had known before becoming a parent?",
                description = "Reflects on parenting journey and wisdom", 
                difficulty = 2
            ),
            ConversationQuestion(
                category = "COUPLES",
                question = "What's one thing I do that makes you feel most loved?",
                description = "Strengthens emotional connection",
                difficulty = 2
            ),
            ConversationQuestion(
                category = "FRIENDS",
                question = "What's the most adventurous thing you want to do in the next year?",
                description = "Explores goals and shared experiences",
                difficulty = 1
            )
        )
        
        questions.forEach { dao.insertQuestion(it) }
    }
}
