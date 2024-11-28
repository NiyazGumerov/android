package ru.itis.homework3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework3.databinding.FragmentQuestionnaireBinding


class QuestionnaireFragment : Fragment(R.layout.fragment_questionnaire) {

    private val viewBinding: FragmentQuestionnaireBinding by viewBinding(
        FragmentQuestionnaireBinding::bind
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            viewPager.adapter = SampleVPAdapter(
                manager = parentFragmentManager,
                lifecycle = lifecycle
            )
        }

        val currentItem = viewBinding.viewPager.currentItem

        viewBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewBinding.nextBtn.isEnabled =
                    position < (viewBinding.viewPager.adapter?.itemCount ?: 0) - 1

                viewBinding.previousBtn.isEnabled = position > 0

                viewBinding.questionNumberTv.text =
                    (currentItem + 1).toString() + "/" + viewBinding.viewPager.adapter?.itemCount.toString()

            }
        })

        viewBinding.nextBtn.setOnClickListener {
            viewBinding.viewPager.setCurrentItem(currentItem + 1, true)
        }

        viewBinding.previousBtn.setOnClickListener {
            viewBinding.viewPager.setCurrentItem(currentItem - 1, true)
        }

    }

    override fun onResume() {
        super.onResume()
        viewBinding.viewPager.requestLayout()
    }

}