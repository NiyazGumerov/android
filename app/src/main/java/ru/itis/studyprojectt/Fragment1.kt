package ru.itis.studyprojectt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button2 = view.findViewById<Button>(R.id.button2)
        val button1 = view.findViewById<Button>(R.id.button1)
        val button3 = view.findViewById<Button>(R.id.button3)
        val editText = view.findViewById<TextView>(R.id.editText)

        button1.setOnClickListener {
            val text = editText.text.toString()
            val transaction = parentFragmentManager.beginTransaction()
            val Fragment2 = Fragment2()
            Fragment2.arguments = Bundle().apply {
                if (text.isEmpty()) {
                    putString("text", "Нет текста")
                } else {
                    putString("text", text)
                }
            }
            transaction.replace(R.id.fragment1, Fragment2)
            transaction.addToBackStack("screen2")
            transaction.commit()
        }

        button2.setOnClickListener {
            val text = editText.text.toString()
            val transaction2 = parentFragmentManager.beginTransaction()
            val Fragment2 = Fragment2()
            Fragment2.arguments = Bundle().apply {
                if (text.isEmpty()) {
                    putString("text", "Нет текста")
                } else {
                    putString("text", text)
                }
            }
            transaction2.replace(R.id.fragment1, Fragment2)
            transaction2.addToBackStack("screen2")
            transaction2.commit()
            val transaction = parentFragmentManager.beginTransaction()
            val Fragment3 = Fragment3()
            Fragment3.arguments = Bundle().apply {
                if (text.isEmpty()) {
                    putString("text", "Нет текста")
                } else {
                    putString("text", text)
                }
            }
            transaction.replace(R.id.fragment1, Fragment3)
            transaction.addToBackStack("screen3")
            transaction.commit()
        }
        val bottomSheet = FragmentBottomSheet()

        parentFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { _, bundle ->
            val text = bundle.getString("result")
            editText.text = text
        }

        button3.setOnClickListener {
            bottomSheet.show(
                parentFragmentManager, "Button"
            )
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = Fragment1().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}