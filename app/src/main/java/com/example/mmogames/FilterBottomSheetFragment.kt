package com.example.mmogames

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetFragment() : BottomSheetDialogFragment(), OnClickListener {


    private lateinit var viewModel: RequestGamesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(
            R.layout.filter_bottom_sheet, container,
            false
        )

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("OnViewCreated", "done")
        val idArray = arrayOf(
            R.id.mmorpg,
            R.id.moba,
            R.id.action,
            R.id.action_rpg,
            R.id.strategy,
            R.id.shooter,
            R.id.survival,
            R.id.battle_royale,
            R.id.social,
            R.id.sandbox,
            R.id.card,
            R.id.racing,
            R.id.sports,
            R.id.mmofps,
            R.id.mmorts,
            R.id.mmotps
        )
        for (g: Int in idArray) {
            view.findViewById<Button>(g).setOnClickListener(this)
        }
        viewModel = ViewModelProvider(requireActivity()).get(RequestGamesViewModel::class.java)
        if (viewModel.filtersMap != null) {
            Log.i("FilterBottomSheet", "govno3 not null")
            for (i in viewModel.filtersMap!!.value!!) {
                if (i.value==true) (view.findViewById(i.key) as Button ).setBackgroundColor(resources.getColor(R.color.black))
            }
        } else {
            Log.i("FilterBottomSheet", "govno3 is null")
            viewModel.setFilterMap(idArray.associateWith { it -> false } as MutableMap<Int, Boolean>)
        }
    }

    override fun onClick(v: View?) {
        val clicked = v as Button

        Log.i("onClick, whats with views?", "true or false: " + (viewModel.filtersMap?.value?.get(v.id)).toString())
        if (viewModel.filtersMap?.value?.get(v.id) == false) {
            v.setBackgroundColor(resources.getColor(R.color.black))
            viewModel.addFilter(clicked.text.toString())
            viewModel.updateFilterMap(v.id, true)
            Log.i("Filtering", "OnCLick on filter change of colour happening")
        } else {
            v.setBackgroundColor(resources.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
            viewModel.updateFilterMap(v.id, false)
            viewModel.removeFilter(clicked.text.toString())
        }
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        Log.i("FilterBottomSheet", "dismiss")
        viewModel.filter()
    }

}