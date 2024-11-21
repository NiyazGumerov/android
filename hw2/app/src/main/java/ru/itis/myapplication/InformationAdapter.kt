package ru.itis.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.customview.widget.ViewDragHelper.Callback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class InformationAdapter(
    private val dataSet: List<RecyclerItem>,
    val context: Context,
    val setListLayoutManager: () -> Unit,
    val setGridLayoutManager: () -> Unit,
    val navigateToRecyclerItem: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val card: MaterialCardView = view.findViewById(R.id.card)
        val image: ImageView = view.findViewById(R.id.ivImage)
        val text: TextView = view.findViewById(R.id.tvText)
    }

    inner class ViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
        val buttonGrid: Button = view.findViewById(R.id.buttonGrid)
        val buttonList: Button = view.findViewById(R.id.buttonList)

        init {
            buttonList.setOnClickListener {
                setListLayoutManager()
            }
            buttonGrid.setOnClickListener {
                setGridLayoutManager()
            }
        }

    }
    override fun getItemViewType(position: Int): Int {
        return dataSet[position].type
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view = LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.item_group, viewGroup, false)
            return ViewHolder1(view)
        } else {
            val view = LayoutInflater
                .from(viewGroup.context)
                .inflate(R.layout.buttons, viewGroup, false)
            return ViewHolder2(view)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = dataSet[position]
        if (viewHolder is ViewHolder1 && item is Information) {
            viewHolder.card.setOnClickListener{
                navigateToRecyclerItem(position)
            }
            viewHolder.text.text = item.text
            viewHolder.image.setImageDrawable(
                ResourcesCompat.getDrawable(context.getResources(), item.image, null)
            )
        }

    }

    override fun getItemCount() = dataSet.size
}