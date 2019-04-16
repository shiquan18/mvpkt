package com.hazz.kotlinmvp.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class BaseFragmentAdapter : FragmentPagerAdapter {


    private var mTitles: List<String>? = null
    private var mFList: List<Fragment>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.mFList = fragmentList
    }

    constructor(fm: FragmentManager, fList: List<Fragment>, titls: List<String>) : super(fm) {
        this.mTitles = titls
        this.mFList = fList
        setFragment(fm, fList)
    }

    private fun setFragment(fm: FragmentManager, mflist: List<Fragment>) {
        if (this.mFList != null) {
            val bt = fm.beginTransaction()
            mFList?.forEach {
                bt.remove(it)
            }
            bt?.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.mFList = mflist
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return mFList!!.get(position)
    }

    override fun getCount(): Int {
        return mFList!!.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (mTitles != null) mTitles!![position] else ""
    }
}