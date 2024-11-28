package ru.itis.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
const val POSITION_KEY = "position"

class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvGroup)
        val adapter = InformationAdapter(
            dataSet = InformationRepository.recyclerItems,
            context = requireActivity().applicationContext,
            setListLayoutManager = {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
            },
            setGridLayoutManager = {
                val gridLayoutManager = GridLayoutManager(requireContext(), 3)
                gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == 0) 3 else 1
                    }
                }

                recyclerView.layoutManager = gridLayoutManager
            },
            navigateToRecyclerItem = {
                val transaction = parentFragmentManager.beginTransaction()
                val detailFragment = DetailFragment()
                detailFragment.arguments = Bundle().apply {
                    putInt(POSITION_KEY, it)
                }
                transaction.replace(R.id.fragment1, detailFragment)
                transaction.addToBackStack("detailScreen")
                transaction.commit()
            }
        )
        val floatingButton = view.findViewById<FloatingActionButton>(R.id.floatingButton)
        val bottomSheet = FragmentBottomSheet { adapter.notifyDataSetChanged() }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        floatingButton.setOnClickListener {
            bottomSheet.show(
                parentFragmentManager, "BottomSheet"
            )
        }
    }
}