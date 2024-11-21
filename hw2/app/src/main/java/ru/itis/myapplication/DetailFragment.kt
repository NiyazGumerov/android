package ru.itis.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import ru.itis.myapplication.InformationRepository.recyclerItems


class DetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt(POSITION_KEY)
        if (position != null) {
            val item = recyclerItems[position] as Information
            view.findViewById<TextView>(R.id.tvGroup).text = item.text
            view.findViewById<TextView>(R.id.tvAbout).text = item.description
            view.findViewById<ImageView>(R.id.ivImage).setImageDrawable(
                ResourcesCompat.getDrawable(requireContext().getResources(), item.image, null)
            )
        }
    }

}