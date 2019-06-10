package com.sanketguru.myworth.view.liability

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sanketguru.myworth.AppCallBack
import com.sanketguru.myworth.MyWorthApp
import com.sanketguru.myworth.R
import com.sanketguru.myworth.data.Liability
import com.sanketguru.myworth.utils.extensions.onClick
import com.sanketguru.myworth.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_edit_liability.*

/**
 * Created by sanket.sphere on 31-12-2018.
 */
class AddEditLiabilityFragment : BaseFragment() {
    override val layout: Int = R.layout.fragment_add_edit_liability
    private var mParam1: Liability? = null
    private var editMode: Boolean = false
    lateinit var callBack: AppCallBack
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getParcelable<Liability>(AddEditLiabilityFragment.ARG_PARAM1)

            if (mParam1 != null) {
                editMode = true
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sw_emi.setOnCheckedChangeListener { p0, p1 ->
            if (p1) {
                text_value.visibility = View.VISIBLE
                il_noemi.visibility = View.VISIBLE
            } else {
                text_value.visibility = View.GONE
                il_noemi.visibility = View.GONE
                edit_emino.setText("1")
            }
        }
        btn_save.onClick { saveLiability() }
        if (editMode) {
            val data = mParam1
            if (data != null) {
                edit_name.setText(data.name)
                edit_desc.setText(data.desc)
                edit_value.setText(data.emi.toString())
                sw_emi.isChecked = data.isEmi()
                edit_emino.setText(data.noOfEmi.toString())
                sw_enable.isChecked = data.calculateInGrand
            }

        }
        if (editMode) {
            btn_delete.onClick {
                val con = context
                if (con != null) {
                    val buld = MaterialAlertDialogBuilder(con)
                    buld.setTitle(R.string.app_name)
                            .setMessage("Delete ${mParam1!!.name}")
                            .setPositiveButton(android.R.string.ok) { dialog, which -> deleteLiability() }
                            .create().show()
                }
            }

        } else {
            btn_delete.visibility = View.GONE

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCallBack) {
            callBack = context
        }
    }

    private fun isValid(): Boolean = when {
        edit_name.text.isNullOrBlank() -> false
        edit_value.text.isNullOrBlank() -> false
        sw_emi.isChecked -> when {
            edit_emino.text.isNullOrBlank() -> false
            edit_emino.text.toString().toInt() < 1 -> false
            else -> true
        }
        else -> true
    }


    private fun saveLiability() {
        if (isValid()) {
            val value = edit_value.text.toString().trim()
            val liability = Liability(
                    uid = if (editMode) mParam1!!.uid else 0,
                    name = edit_name.text.toString().trim(),
                    desc = edit_desc.text.toString().trim(),
                    emi = if (value.isEmpty()) 0.0 else value.toDouble(),
                    noOfEmi = edit_emino.text.toString().trim().toInt(),
                    dateOfMonth = 2,
                    calculateInGrand = sw_enable.isChecked)
            val appData = activity!!.application as MyWorthApp
            val thread = Thread(Runnable {
                if (editMode) {
                    appData.db.liabilityDao().update(liability)
                } else {
                    appData.db.liabilityDao().insertAll(liability)
                }
                activity?.runOnUiThread {
                    val imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                    //Hide:
                    imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                    callBack.popFragmentAt(tagTitle)
                }
            })
            thread.start()
        }
    }

    private fun deleteLiability() {
        mParam1?.let {
            val appData = activity!!.application as MyWorthApp

            val thread = Thread(Runnable {
                if (editMode) {
                    appData.db.liabilityDao().delete(it)
                }
                activity!!.runOnUiThread {
                    callBack.popFragmentAt(tagTitle)
                }
            })
            thread.start()
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        const val tagTitle = "AddLiability"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment AddEditAssetFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: Liability): AddEditLiabilityFragment {
            val fragment = AddEditLiabilityFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }


}