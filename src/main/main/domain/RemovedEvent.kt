package domain

/** Represents parts of an event
 * that need to be used to
 * remove an event
 * */


data class RemovedEvent(
val planeId: String,
val timestamp: Long
)
