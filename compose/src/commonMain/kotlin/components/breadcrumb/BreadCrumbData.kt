package components.breadcrumb

import androidx.compose.runtime.Immutable

@Immutable
data class BreadCrumbData(val items: List<BreadCrumbItemData>) {
  companion object {
    val Empty = BreadCrumbData(emptyList())
  }
}
