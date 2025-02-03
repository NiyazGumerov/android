package ru.itis.homework4

import android.widget.FrameLayout
import androidx.core.content.ContextCompat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ThemeAdapter(
    private val colors: List<Pair<Int, Int>>,
    private val onColorSelected: (Int) -> Unit
) : RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false)
        return ThemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ThemeViewHolder, position: Int) {
        val (colorResId, themeResId) = colors[position]
        holder.bind(colorResId, themeResId)
    }

    override fun getItemCount(): Int = colors.size

    inner class ThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnThemeColor: FrameLayout = itemView.findViewById(R.id.item_color)

        fun bind(colorResId: Int, themeResId: Int) {
            btnThemeColor.setBackgroundColor(ContextCompat.getColor(itemView.context, colorResId))
            btnThemeColor.setOnClickListener {
                onColorSelected(themeResId)
            }
        }
    }
}