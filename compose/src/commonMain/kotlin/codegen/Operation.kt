package codegen

interface Operation {
    fun getStartComment(): List<String>
    fun getOperationCode(): List<String>
    fun getEndComment(): List<String>
}
