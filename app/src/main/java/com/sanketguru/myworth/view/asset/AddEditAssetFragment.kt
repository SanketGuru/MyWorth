package com.sanketguru.myworth.view.asset

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sanketguru.myworth.AppCallBack
import com.sanketguru.myworth.MyWorthApp
import com.sanketguru.myworth.R
import com.sanketguru.myworth.data.Asset
import com.sanketguru.myworth.utils.extensions.onClick
import com.sanketguru.myworth.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_edit_asset.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddEditAssetFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddEditAssetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddEditAssetFragment : BaseFragment() {

    override val layout: Int = R.layout.fragment_add_edit_asset
    private var mParam1: Asset? = null
    private var folioId: Int = 0
    private var editMode = false
    //  private var mParam2: String? = null
    lateinit var callBack: AppCallBack


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getParcelable<Asset>(ARG_PARAM1)
            folioId = arguments!!.getInt(ARG_PARAM2)
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
                edit_desc.setText(data.description)
                edit_value.setText(data.value.toString())
            }

        }
        btn_save.onClick { saveAsset() }
        if (editMode) {
            btn_delete.onClick {
                val con = context
                if (con != null) {
                    val alertDialogBuilder = MaterialAlertDialogBuilder(con)
                    alertDialogBuilder.setTitle(R.string.app_name)
                            .setMessage("Delete ${mParam1!!.name}")
                            .setPositiveButton(android.R.string.ok) { dialog, which -> deleteAsset() }
                            .create().show()
                }


            }

        } else {
            btn_delete.visibility = View.GONE

        }
    }

    private fun isValid(): Boolean = if (edit_name.text.toString().trim().isEmpty())
        false
    else edit_value.text.toString().trim().isNotEmpty()


    private fun saveAsset() {
        if (isValid()) {
            val value = edit_value.text.toString().trim()
            val asset = Asset(
                    if (editMode) mParam1!!.uid else 0,
                    edit_name.text.toString().trim(),
                    edit_desc.text.toString().trim(),
                    value = if (value.length < 1) 0.0 else value.toDouble(),
                    portFolioId = folioId)
            val appData = activity!!.application as MyWorthApp
            val thread = Thread(Runnable {
                if (editMode) {
                    appData.db.assetDao().update(asset)
                } else {
                    appData.db.assetDao().insertAll(asset)
                }
                activity!!.runOnUiThread {
                    val imm = context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                    //Hide:
                    imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

                    val data= Intent("CO")
                    data.putExtra("data","Asset edit")
                    context!!.sendBroadcast(data)
LocalBroadcastManager.getInstance(context!!).sendBroadcast(data)
                    callBack.popFragmentAt(tagTitle)
                }
            })
            thread.start()
        }
    }

    private fun deleteAsset() {

        val value = edit_value.text.toString().trim()
        val asset = Asset(if (editMode) mParam1!!.uid else 0,
                edit_name.text.toString().trim(),
                edit_desc.text.toString().trim(),
                value = if (value.isEmpty()) 0.0 else value.toDouble(),
                portFolioId = folioId)
        val appData = activity!!.application as MyWorthApp
        val thread = Thread(Runnable {
            if (editMode) {
                appData.db.assetDao().delete(asset)
            }
            activity!!.runOnUiThread {
                callBack.goHome()
            }
        })
        thread.start()

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        const val tagTitle = "AddAsset"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddEditAssetFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: Asset, folio: Int): AddEditAssetFragment {
            val fragment = AddEditAssetFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM1, param1)
            args.putInt(ARG_PARAM2, folio)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(folio: Int): AddEditAssetFragment {
            val fragment = AddEditAssetFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM2, folio)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
