package ru.itis.homework3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.customview.widget.ViewDragHelper.Callback
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class AnswerAdapter(
    private val answers: List<String>,
    private val questionNumber: Int
) :
    RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {
    private var selectedPosition = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val answerText: TextView = view.findViewById(R.id.answer_tv)
        val button: RadioButton = view.findViewById(R.id.answer_radio_btn)
        val card: MaterialCardView = view.findViewById(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_answer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        selectedPosition = QuestionnaireRepository.qiestionnaireList[questionNumber].chosenNumber
        holder.answerText.text = answers[position]

        holder.card.setCardBackgroundColor(
            if (position == selectedPosition)
                ContextCompat.getColor(holder.card.context, R.color.green) else
                ContextCompat.getColor(holder.card.context, R.color.white)
        )
        holder.button.isChecked = position == selectedPosition

        holder.button.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

                selectedPosition = position
                QuestionnaireRepository.qiestionnaireList[questionNumber].chosenNumber = position
                notifyDataSetChanged()
            }
        }


    }

    override fun getItemCount(): Int = answers.size
}