package com.sanketguru.myworth.view.asset

import android.os.Bundle
import android.view.View
import com.sanketguru.myworth.AppCallBack
import com.sanketguru.myworth.R
import com.sanketguru.myworth.data.Asset
import com.sanketguru.myworth.utils.extensions.onClick
import com.sanketguru.myworth.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_view_asset.*

/**
 * Created by sanket.sphere on 08-12-2018.
 */
class ViewAssetFragment : BaseFragment() {
    override val layout: Int = R.layout.fragment_view_asset
    private var mParam1: Asset? = null
    private lateinit var callBack: AppCallBack
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callBack = activity as AppCallBack
        if (arguments != null) {
            mParam1 = arguments!!.getParcelable<Asset>(ARG_PARAM1)
            if (mParam1 != null) {

            }
            //      mParam2 = arguments!!.getString(ARG_PARAM2)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name.text = mParam1!!.name
        value.text = mParam1?.value.toString()
        desc.text = mParam1!!.description
        btn_edit.onClick {
            val param = mParam1
            if (param != null) {
                callBack.replaceFragment(AddEditAssetFragment.newInstance(param, param.portFolioId),AddEditAssetFragment.tagTitle)
            }

        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        // private val ARG_PARAM2 = "param2"
       const val tagTitle = "ViewAsset"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment ViewAssetFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: Asset): ViewAssetFragment {
            val fragment = ViewAssetFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM1, param1)
            // args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment

        }
    }
}