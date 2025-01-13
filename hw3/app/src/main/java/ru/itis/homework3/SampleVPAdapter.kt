package ru.itis.homework3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class SampleVPAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(manager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment.getInstance(position)
    }

    override fun getItemCount(): Int = QuestionnaireRepository.qiestionnaireList.size

}
