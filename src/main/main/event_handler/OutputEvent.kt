package event_handler

import mu.KotlinLogging
import domain.Event
import domain.Plane
import util.Time

/**
 * A OutputEvent class that has methods that
 * help output the events*/


class OutputEvent {
    val time = Time()


    fun removeEvent(userInput: String, event: ArrayList<Event>): ArrayList<Event> {
        val fields = userInput.split(" ")
        val timestamp = time.convertToEpochTime(fields[1])
        var indexKiller = 0
        event.forEach {
            if (it.planeId == fields[0] && it.timestamp == timestamp){
                indexKiller = event.indexOf(it)
            }
        }
        event.removeAt(indexKiller)
        return event
    }

    fun renameEvent(event: ArrayList<Plane>): ArrayList<Plane> {
        event.sortBy { it.planeId?.substring(1,4) }
        for (i in event) {
            if (i.eventType == "Re-Fuel") {
                i.eventType = "Awaiting-Takeoff"
            }
            if (i.eventType == "Take-Off"){
                i.eventType = "In-Flight"
            }
            if (i.eventType == "Land") {
                i.eventType = "Landed"
            }
            println("${i.planeId}, ${i.eventType}, ${i.fuelDelta}")
        }
        return event
    }

    //Checks if the function needs to replace,
    // subtract or get the most recent fuel before 0
    fun alterFuel(event: ArrayList<Event>, fields: List<String>, timestamp: Long) :Int{
        var newFuel = 0
        var indexToChange: Int
        var negativeFuelUpdater: Int
        event.forEach { outerLoop ->
            if (outerLoop.planeId == fields[0] && outerLoop.fuelDelta > 0
                && outerLoop.timestamp == timestamp) {
                negativeFuelUpdater = newFuel
                indexToChange = event.indexOf(outerLoop)
                replaceData(negativeFuelUpdater, event, indexToChange)
                return -1
            }
            if (outerLoop.planeId == fields[0] && outerLoop.fuelDelta > 0
                && outerLoop.timestamp != timestamp) {
                /* used '+' because the fuel is negative */
                newFuel = outerLoop.fuelDelta + fields[6].toInt()
            }
            if (outerLoop.planeId == fields[0] && outerLoop.fuelDelta == 0) {
                val oldFuel = outerLoop.fuelDelta
                event.forEach{
                    if (it.planeId == fields[0] && it.timestamp < timestamp
                        && it.fuelDelta != oldFuel) {
                        newFuel = it.fuelDelta
                    }
                }
            }
        }
        return newFuel
    }

    fun replaceData(fuel : Int, event: ArrayList<Event>, index: Int) {
        event[index].fuelDelta = fuel
    }
}
