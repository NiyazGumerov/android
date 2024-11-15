package ru.itis.studyprojectt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FragmentBottomSheet : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(
            R.layout.fragment_bottom_sheet,
            container, false
        )

        val text = v.findViewById<EditText>(R.id.editText2)
        val button = v.findViewById<Button>(R.id.button6)
        button.isEnabled = false
        text.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank()) {
                button.isEnabled = false
            } else {
                button.isEnabled = true
            }
        }
        button.setOnClickListener {
            val resultBundle = Bundle().apply {
                putString("result", text.text.toString())
            }

            parentFragmentManager.setFragmentResult("requestKey", resultBundle)
            text.text.clear()
            dismiss()
        }


        return v
    }
}
