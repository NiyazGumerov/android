package ru.itis.homework3

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework3.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment(R.layout.fragment_question) {

    private val viewBinding: FragmentQuestionBinding by viewBinding(
        FragmentQuestionBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt(POSITION_KEY)?.let { position ->

            val questionnaireList = QuestionnaireRepository.qiestionnaireList[position]

            with(questionnaireList) {
                viewBinding.questionTv.text = question
                val adapter = AnswerAdapter(
                    answers = listOf(answer1, answer2, answer3, answer4, answer5),
                    questionNumber = position
                )

                viewBinding.questionRv.adapter = adapter
            }
            viewBinding.questionRv.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        private const val POSITION_KEY = "POSITION"
        fun getInstance(position: Int) = QuestionFragment().apply {
            arguments = bundleOf(POSITION_KEY to position)
        }
    }
}