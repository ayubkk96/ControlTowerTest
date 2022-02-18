package domain

/** Represents a plane that
 * the control tower reports about*/

data class Plane(
    var planeId: String?,
    var distanceToTimestamp: Long?,
    var eventType: String?,
    var fuelDelta: Int?
)
