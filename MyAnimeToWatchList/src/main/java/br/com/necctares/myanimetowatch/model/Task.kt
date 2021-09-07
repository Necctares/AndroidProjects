package br.com.necctares.myanimetowatch.model

data class Task(
    val name : String,
    val numberOfEps : Int,
    val date : String,
    val taskId : Int = 0,
    val actualEp : Int = 1
){
    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false
        other as Task
        if(taskId != other.taskId) return false
        return true
    }

    override fun hashCode(): Int {
        return taskId
    }
}
