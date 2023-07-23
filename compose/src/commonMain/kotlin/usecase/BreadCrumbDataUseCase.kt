package usecase

import components.breadcrumb.BreadCrumbData
import components.breadcrumb.BreadCrumbItemData
import okio.Path
import okio.Path.Companion.toOkioPath

class BreadCrumbDataUseCase {

  fun Path.toBreadCrumbData(onItemClick: (Path) -> Unit): BreadCrumbData {
    val directories = directories()

    return BreadCrumbData(
      directories.map { path ->
        BreadCrumbItemData(
          title = path.segments.lastOrNull() ?: "/",
          onClick = { onItemClick(path) }
        )
      }
    )
  }
}

fun Path.directories(): List<Path> {
  val list = mutableListOf<Path>()

  val file = toFile()
  if (file.isDirectory) {
    list += this
  }

  var parent = file.parentFile
  while (parent != null) {
    list += parent.toOkioPath()
    parent = parent.parentFile
  }
  return list.reversed()
}
