package components.breadcrumb

import androidx.compose.runtime.Stable

@Stable data class BreadCrumbItemData(val title: String, val onClick: () -> Unit)
