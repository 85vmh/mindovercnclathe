package components.breadcrumb

import androidx.compose.runtime.Stable

@Stable
data class BreadCrumbData(val items: List<BreadCrumbItemData>) {
  companion object {
    val Empty = BreadCrumbData(emptyList())
  }
}
