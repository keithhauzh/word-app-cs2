package com.keith.word_app_cs2.ui.fragments.manageWord

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels

class AddWordFragment : BaseManageWordFragment() {
    override val viewModel: AddWordViewModel by viewModels()
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )
    }
}