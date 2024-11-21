package ru.itis.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.customview.widget.ViewDragHelper.Callback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.itis.myapplication.InformationRepository.addRandomInformationToList
import ru.itis.myapplication.InformationRepository.deleteRandomInformation




class FragmentBottomSheet(val updateRecyclerItems:()-> Unit) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(
            R.layout.fragment_bottom_sheet,
            container, false
        )

        val text = v.findViewById<EditText>(R.id.editTextNumber)
        val button_for_add_multiple = v.findViewById<Button>(R.id.buttonForAddMultiple)
        val button_for_delete_multiple = v.findViewById<Button>(R.id.buttonForDeleteMultiple)
        val button_for_add_one= v.findViewById<Button>(R.id.buttonForAddOne)
        val button_for_delete_one = v.findViewById<Button>(R.id.buttonForDeleteOne)
        button_for_add_multiple.isEnabled = false
        button_for_delete_multiple.isEnabled = false
        text.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank()) {
                button_for_add_multiple.isEnabled = false
                button_for_delete_multiple.isEnabled = false
            } else {
                button_for_add_multiple.isEnabled = true
                button_for_delete_multiple.isEnabled = true
            }
        }
        button_for_add_one.setOnClickListener {
            addRandomInformationToList()
            updateRecyclerItems()
        }
        button_for_delete_one.setOnClickListener{
            deleteRandomInformation()
            updateRecyclerItems()
        }
        button_for_add_multiple.setOnClickListener{
            val number = text.text.toString().toInt()
            repeat(number) {
                addRandomInformationToList()
            }
            updateRecyclerItems()
        }
        button_for_delete_multiple.setOnClickListener{
            val number = text.text.toString().toInt()
            repeat(number) {
                deleteRandomInformation()
            }
            updateRecyclerItems()
        }


        return v
    }

    override fun dismiss() {
        super.dismiss()
    }
}
