package org.wordpress.android.ui.posts

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.wordpress.android.R
import org.wordpress.android.WordPress
import org.wordpress.android.login.widgets.WPBottomSheetDialogFragment
import javax.inject.Inject

class PrepublishingBottomSheetFragment : WPBottomSheetDialogFragment() {
    @Inject internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: PrepublishingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireNotNull(activity).application as WordPress).component().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_prepublishing_bottom_sheet, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        dialog?.setOnShowListener { dialogInterface ->
            val sheetDialog = dialogInterface as? BottomSheetDialog

            val bottomSheet = sheetDialog?.findViewById<View>(
                    com.google.android.material.R.id.design_bottom_sheet
            ) as? FrameLayout

            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PrepublishingViewModel::class.java)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        removeContentFragmentBeforeDismissal()
    }

    private fun removeContentFragmentBeforeDismissal() {
        activity?.supportFragmentManager?.let { fragmentManager ->
            fragmentManager.findFragmentById(R.id.prepublishing_content_fragment)?.also { fragment ->
                fragmentManager.beginTransaction().remove(fragment).commit()
            }
        }
    }

    companion object {
        const val TAG = "prepublishing_bottom_sheet_fragment_tag"

        @JvmStatic
        fun newInstance(
        ): PrepublishingBottomSheetFragment {
            val fragment = PrepublishingBottomSheetFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}