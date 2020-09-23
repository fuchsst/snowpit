package at.willhaben.dt.snowpit.service

private val fieldNameRegex = """\w[\w_\d]*""".toRegex()
private val qualifiedNameRegex = """\w[\w_\d]*(\.\w[\w_\d]*){0,2}""".toRegex()

fun String.isValidFieldName(): Boolean {
    return fieldNameRegex.matches(this)
}


fun String.isValidQualifiedName(): Boolean {
    return qualifiedNameRegex.matches(this)
}