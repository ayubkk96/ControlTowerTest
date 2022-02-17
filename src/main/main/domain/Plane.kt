package domain

data class Plane(
    var planeId: String?,
    var distanceToTimestamp: Long?,
    var eventType: String?,
    var fuelDelta: Int?
)
