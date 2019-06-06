package com.sanketguru.myworth.view.folio

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sanketguru.myworth.AppCallBack
import com.sanketguru.myworth.BackListner
import com.sanketguru.myworth.MyWorthApp
import com.sanketguru.myworth.R
import com.sanketguru.myworth.data.PortFolio
import com.sanketguru.myworth.utils.extensions.onClick
import com.sanketguru.myworth.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_edit_folio.*

/**
 * Created by sanket.sphere on 13-12-2018.
 */
class AddEditFolioFragment : BaseFragment(), BackListner {
    override val layout: Int = R.layout.fragment_add_edit_folio
    private var mParam1: PortFolio? = null
    private var editMode = false
    lateinit var callBack: AppCallBack
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getParcelable<PortFolio>(AddEditFolioFragment.ARG_PARAM1)
            if (mParam1 != null) {
                editMode = true
            }
            //      mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callBack = activity as AppCallBack
        if (editMode) {
            val data = mParam1
            if (data != null) {
                edit_name.setText(data.name)
                edit_desc.setText(data.desc)

            }

        }
        btn_save.onClick { saveFolio() }
        if (editMode) {
            btn_delete.onClick {
                val con = context
                if (con != null) {
                    val alertBuilder = MaterialAlertDialogBuilder(con)
                    alertBuilder.setTitle(R.string.app_name)
                            .setMessage("Delete ${mParam1!!.name}")
                            .setPositiveButton(android.R.string.ok) { dialog, which -> deleteFolio() }
                            .create().show()
                }
            }
//            btn_view.onClick {
//                //TODO View Port Folio
//                val data= mParam1
//                if (data != null) {
//                    callBack.addFragment(AssetListFragment.newInstance( data),AssetListFragment.tagTitle)
//                }
//            }
        } else {
            btn_delete.visibility = View.GONE
         //   btn_view.visibility = View.GONE
        }
    }

    private fun saveFolio() {
        if (isValid()) {
            val folio = PortFolio(
                    if (editMode) mParam1!!.uid else 0,
                    edit_name.text.toString().trim(),
                    edit_desc.text.toString().trim()
            )

            val appData = activity!!.application as MyWorthApp
            val thread = Thread(object : Runnable {
                override fun run() {
                    if (editMode) {
                        appData.db.portfolioDao().update(folio)
                    } else {
                        appData.db.portfolioDao().insertAll(folio)

                    }
                    activity!!.runOnUiThread {
                        val imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
//Hide:
                        imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                        callBack.goHome()
                    }

                }
            })
            thread.start()
        }
    }

    private fun isValid(): Boolean = edit_name.text.toString().trim().isNotEmpty()

    private fun deleteFolio() {
        val asset = PortFolio(if (editMode) mParam1!!.uid else 0,
                edit_name.text.toString().trim(),
                edit_desc.text.toString().trim()
        )
        val appData = activity!!.application as MyWorthApp
        val thread = Thread(Runnable {
            if (editMode) {
                appData.db.portfolioDao().delete(asset)
            }
            activity!!.runOnUiThread {
                callBack.goHome()
            }
        })
        thread.start()
    }
    override fun onBack() {
        callBack.removeFragment(this)
    }
    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        // private val ARG_PARAM2 = "param2"
        const val tagTitle = "AddPortFolio"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddEditAssetFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: PortFolio): AddEditFolioFragment {
            val fragment = AddEditFolioFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }
}