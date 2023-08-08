package components.breadcrumb

import androidx.compose.runtime.Immutable

@Immutable
data class BreadCrumbItemData(
    val title: String,
    val onClick: () -> Unit
)
