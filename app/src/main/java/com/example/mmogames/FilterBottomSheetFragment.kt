package com.example.mmogames

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheetFragment() : BottomSheetDialogFragment(), OnClickListener {

    private lateinit var viewModel: RequestGamesViewModel
    private lateinit var idArray: Array<Int>

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
        idArray = arrayOf(
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
            R.id.mmotps,
            //  R.id.platform_pc, R.id.platform_browser,
            R.id.anime,
            R.id.fantasy,
            R.id.flight,
            R.id.horror,
            R.id.martial_arts,
            R.id.military,
            R.id.sailing,
            R.id.scifi,
            R.id.space,
            R.id.superhero,
            R.id.tank,
            R.id.zombie,
            R.id.turn_based, R.id.real_time
        )
        for (i: Int in idArray) {
            view.findViewById<Button>(i).setOnClickListener(this)
        }


        (view.findViewById(R.id.clear_filters) as TextView).setOnClickListener({
            viewModel.clearFilters(idArray)
            deselectFilters(view)
        })

        var radioGroup: RadioGroup = view.findViewById(R.id.sort_by_radio_group)
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                var sortBy: String = (view.findViewById(checkedId) as RadioButton).text.toString()
                viewModel.kek2(sortBy)
            }

        })
        viewModel = ViewModelProvider(requireActivity()).get(RequestGamesViewModel::class.java)
        if (viewModel.filtersMap != null) {
            for (i in viewModel.filtersMap!!.value!!) {
                if (i.value) {
                    (view.findViewById(i.key) as Button).setBackgroundColor(resources.getColor(R.color.black))
                }
            }
        } else {
            viewModel.setFilterMap(idArray)
        }
    }

    override fun onClick(v: View?) {
        val clicked = v as Button
        val color = viewModel.kek(v.id, v.text.toString())
        clicked.setBackgroundColor(resources.getColor(color))
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        (activity as MainActivity).setLoadingState() //?
        viewModel.filterAndSort()
    }

    private fun deselectFilters(view: View) {
        for (i: Int in idArray) {
            (view.findViewById(i) as Button).setBackgroundColor(this.resources.getColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color))
        }
    }

}