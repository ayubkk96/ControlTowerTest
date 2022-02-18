package util

import domain.Event
import domain.Plane
import java.text.SimpleDateFormat


/**
 * A class that contains useful utility functions to
 * help process the events involving time
 */
class Time {
    fun convertToEpochTime(timestamp: String): Long {
        val newTimeStamp = timestamp.replace("T", " ")
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val date = format.parse(newTimeStamp)
        return date.time
    }

    fun closestToTime(timestamp: Long, event: ArrayList<Event>): ArrayList<Plane> {
        var distanceMeasurement: Long
        val planeIdsChecked = ArrayList<Plane>()
        event.forEach {

            if (it.timestamp < timestamp) {
                distanceMeasurement = timestamp - it.timestamp
                if (planeIdsChecked.any { true }) {
                    for (i in planeIdsChecked.toList()) {
                        if (it.planeId == i.planeId && distanceMeasurement < i.distanceToTimestamp!!) {
                            i.distanceToTimestamp = distanceMeasurement
                            i.eventType = it.eventType
                            i.fuelDelta = it.fuelDelta
                        }
                    }
                }

                if (!(planeIdsChecked.contains(
                        Plane(
                            it.planeId, distanceMeasurement, it.eventType, it.fuelDelta
                        )
                    ))
                ) {
                    planeIdsChecked.add(
                        Plane(it.planeId, distanceMeasurement, it.eventType, it.fuelDelta)
                    )
                }
            }
                if (it.timestamp == timestamp) {
                    planeIdsChecked.add(
                        Plane(it.planeId, 0, it.eventType, it.fuelDelta)
                    )
                }

        }
        return planeIdsChecked
    }
}