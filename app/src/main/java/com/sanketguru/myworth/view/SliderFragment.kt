package com.sanketguru.myworth.view

import android.os.Bundle
import androidx.fragment.app.FragmentPagerAdapter as FragAdapter
import android.view.View
import androidx.fragment.app.FragmentManager
import com.sanketguru.myworth.AppCallBack
import com.sanketguru.myworth.MyWorthApp
import com.sanketguru.myworth.R
import com.sanketguru.myworth.utils.extensions.onClick
import com.sanketguru.myworth.view.folio.AddEditFolioFragment
import com.sanketguru.myworth.view.folio.PortFolioListFragment
import com.sanketguru.myworth.view.liability.AddEditLiabilityFragment
import com.sanketguru.myworth.view.liability.LiabilityListFragment
import kotlinx.android.synthetic.main.activity_slider.*


/**
 * Created by sanket.sphere on 01-01-2019.
 */
class SliderFragment : BaseFragment() {
    override val layout: Int = R.layout.activity_slider

    lateinit var callBack: AppCallBack
    lateinit var app: MyWorthApp
    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app = activity!!.applicationContext as MyWorthApp
        callBack = activity as AppCallBack
        val lMan = fragmentManager
        if (lMan != null) {
            mSectionsPagerAdapter = SectionsPagerAdapter(lMan)
        }
        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
        fab.onClick {
            if (tab.selectedTabPosition == 0) {
                callBack.addFragment(AddEditFolioFragment(), AddEditFolioFragment.tagTitle)


            } else {
                callBack.addFragment(AddEditLiabilityFragment(), AddEditLiabilityFragment.tagTitle)
            }
        }

       // toolbar.title="Balance"


    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) :FragAdapter(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getPageTitle(position: Int): CharSequence? = if (position == 0) "Assets" else "Liability"


        override fun getItem(position: Int): androidx.fragment.app.Fragment = if (position == 0)
            PortFolioListFragment()
        else
            LiabilityListFragment()


        override fun getCount(): Int = 2
    }

    companion object {
        const val tagTitle = "SliderFragment"
    }
}