package at.willhaben.dt.snowpit.service

private val regex = """\w[\w_\d]*""".toRegex()

fun String.isValidFieldName():Boolean {
    return regex.matches(this)
}