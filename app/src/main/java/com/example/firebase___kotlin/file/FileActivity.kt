package com.example.firebase___kotlin.file

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.firebase___kotlin.databinding.ActivityFileBinding
import com.example.firebase___kotlin.base.BaseActivity
import java.io.File

class FileActivity : BaseActivity<ActivityFileBinding>(
    ActivityFileBinding::inflate
) {
    private val multiplePermissionId = 14
    private val multiplePermissionNameList = if (Build.VERSION.SDK_INT >= 33) {
        arrayListOf(
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        arrayListOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )
    }
//    private val requestPermissionLauncher =
//        registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//                getAllMediaFiles()
//            } else {
//
//            }
//        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            multiplePermissionId -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    override fun initView() {
        requestPermisison()
        getAllMediaFiles()

    }

    private fun requestPermisison() {
        val listPermissionNeeded = arrayListOf<String>()
        for (permission in multiplePermissionNameList) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // You can use the API that requires the permission.
                }
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this, permission
                ) -> {
                }
                else -> {
                    listPermissionNeeded.add(permission)
                }
            }
        }

        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionNeeded.toTypedArray(),
                multiplePermissionId
            )
        }
    }

    override fun initData() {

    }

    override fun handleEvent() {

    }

    private fun getAllMediaFiles() {
        // Đường dẫn đến thư mục gốc của thiết bị
        // Đường dẫn đến thư mục gốc của thiết bị
        val rootPath = Environment.getExternalStorageDirectory().absolutePath

        // Gọi hàm để in ra tất cả thư mục và file con

        // Gọi hàm để in ra tất cả thư mục và file con
        printFilesAndFolders(rootPath)
    }

    private fun printFilesAndFolders(directoryPath: String) {
        val directory = File(directoryPath)

        if (directory.exists() && directory.isDirectory) {
            val files = directory.listFiles()

            if (files != null) {
                for (file in files) {
                    Log.e("File .....", file.toString())
                    if (file.isDirectory) {
                        // Nếu là thư mục, in ra tên thư mục và duyệt vào bên trong
                        Log.e("FileExplorer", "Folder: ${file.name}")
                        printFilesAndFolders(file.absolutePath)
                    } else {
                        // Nếu là file, in ra tên file
                        Log.e("FileExplorer", "File: ${file.name}")
                    }
                }
            }
        }
    }
}