package com.azathoth.handlist.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.azathoth.handlist.data.DemoData
import com.azathoth.handlist.ui.screens.UIFile

class FsVM: ViewModel() {
    private val fs by mutableStateOf(DemoData.demoNodeTree())

    val root: UIFile
        get() = fs.root()
}