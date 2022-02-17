package domain

data class DisplayedEvent(
    val planeId: String,
    val eventType: String,
    val fuelDelta: Int
)