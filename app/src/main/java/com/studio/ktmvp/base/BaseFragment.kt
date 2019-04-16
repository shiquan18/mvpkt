package com.hazz.kotlinmvp.base

import android.app.Fragment
import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.classic.common.MultipleStatusView
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    private var isViewPrepare = false

    private var hasLoadData = false

    protected var mLayoutStartView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayout(), null)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            lazyLoadDataIfPrepared()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()
        mLayoutStartView?.setOnClickListener(Listener)
    }


    fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    open var Listener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }

    abstract fun getLayout(): Int
    abstract fun initView()
    abstract fun lazyLoad()


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.i("BaseFragment", "获取成功的权限 perms=$perms")
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        var sb = StringBuffer()
        for (it in perms) {
            sb.append(it)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(activity, "a;sdlfkj", Toast.LENGTH_LONG).show()
            AppSettingsDialog.Builder(this)
                .setRationale("a;sdlfkj")
                .setPositiveButton("好")
                .setNegativeButton("不好")
                .build().show()
        }
    }
}

