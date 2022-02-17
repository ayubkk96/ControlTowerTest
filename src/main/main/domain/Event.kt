package domain

/** Represents an event
 * coming to the control tower via user input */

data class Event(
    val planeId: String,
    var planeModel: Int,
    var origin: String,
    var destination: String,
    var eventType: String,
    val timestamp: Long,
    var fuelDelta: Int,
)

