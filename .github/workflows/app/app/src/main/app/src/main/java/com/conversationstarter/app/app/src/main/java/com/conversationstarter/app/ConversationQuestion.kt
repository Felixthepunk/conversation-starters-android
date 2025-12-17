package com.conversationstarter.app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversation_questions")
data class ConversationQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val question: String,
    val description: String? = null,
    val difficulty: Int = 1,
    val isFavorite: Boolean = false,
    val isUsed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class QuestionCategory(val displayName: String, val emoji: String) {
    CHILDREN("Children", "👶"),
    TEENS("Teens", "🧒"),
    PARENTS("Parents", "👨‍👩‍👧‍👦"),
    SIBLINGS("Siblings", "👫"),
    COUPLES("Couples", "💕"),
    FRIENDS("Friends", "👥"),
    FAMILY("Family", "🏠"),
    WORKPLACE("Workplace", "💼"),
    DEEP_THOUGHTS("Deep Thoughts", "🤔"),
    LIGHT_HEARTED("Light & Fun", "😄")
}
