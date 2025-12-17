package com.conversationstarter.app

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    
    private lateinit var database: ConversationDatabase
    private lateinit var currentQuestionText: TextView
    private var currentQuestion: ConversationQuestion? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        database = (application as ConversationStarterApplication).database
        
        createLayout()
        loadRandomQuestion()
    }
    
    private fun createLayout() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(64, 64, 64, 64)
        }
        
        // Title
        val title = TextView(this).apply {
            text = "💬 Conversation Starters"
            textSize = 24f
            setPadding(0, 0, 0, 32)
        }
        layout.addView(title)
        
        // Current question display
        currentQuestionText = TextView(this).apply {
            text = "Loading your first conversation starter..."
            textSize = 18f
            setPadding(0, 0, 0, 32)
        }
        layout.addView(currentQuestionText)
        
        // Get new question button
        val newQuestionButton = Button(this).apply {
            text = "Get New Question"
            setOnClickListener { loadRandomQuestion() }
        }
        layout.addView(newQuestionButton)
        
        // Categories section
        val categoriesTitle = TextView(this).apply {
            text = "\n📋 Categories"
            textSize = 20f
            setPadding(0, 32, 0, 16)
        }
        layout.addView(categoriesTitle)
        
        // Category buttons
        QuestionCategory.values().forEach { category ->
            val button = Button(this).apply {
                text = "${category.emoji} ${category.displayName}"
                setOnClickListener { loadQuestionFromCategory(category.name) }
            }
            layout.addView(button)
        }
        
        setContentView(layout)
    }
    
    private fun loadRandomQuestion() {
        lifecycleScope.launch {
            try {
                val categories = QuestionCategory.values()
                val randomCategory = categories.random()
                val question = database.questionDao().getRandomQuestionByCategory(randomCategory.name)
                
                currentQuestion = question
                currentQuestionText.text = if (question != null) {
                    "💡 ${question.question}\n\n📝 ${question.description}"
                } else {
                    "🎯 Ready to start meaningful conversations!\n\nTap a category below to get started."
                }
            } catch (e: Exception) {
                currentQuestionText.text = "Welcome to Conversation Starters! 🎉\n\nTap 'Get New Question' or choose a category below."
            }
        }
    }
    
    private fun loadQuestionFromCategory(category: String) {
        lifecycleScope.launch {
            try {
                val question = database.questionDao().getRandomQuestionByCategory(category)
                
                currentQuestion = question
                currentQuestionText.text = if (question != null) {
                    "💡 ${question.question}\n\n📝 ${question.description}"
                } else {
                    "No questions available for this category yet. More coming soon!"
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error loading question", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
