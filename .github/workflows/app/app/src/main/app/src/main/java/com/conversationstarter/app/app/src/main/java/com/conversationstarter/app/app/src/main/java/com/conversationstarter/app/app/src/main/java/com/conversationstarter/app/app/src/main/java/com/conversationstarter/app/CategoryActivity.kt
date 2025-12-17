package com.conversationstarter.app

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CategoryActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(64, 64, 64, 64)
        }
        
        val title = TextView(this).apply {
            text = "📱 Category View"
            textSize = 24f
        }
        layout.addView(title)
        
        val description = TextView(this).apply {
            text = "This screen will show detailed questions for specific categories in future updates!"
            textSize = 16f
        }
        layout.addView(description)
        
        setContentView(layout)
    }
}
