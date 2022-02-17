package event_handler

import domain.DisplayedEvent
import mu.KotlinLogging
import domain.Event
import domain.Plane
import domain.RemovedEvent
import util.Time
import java.awt.image.AreaAveragingScaleFilter
import kotlin.math.abs

class OutputEvent {
    private val logger = KotlinLogging.logger {}
    val time = Time()
    fun getEventsSize(timestamp: String?, index: Int): Int {
        val event = ArrayList<Event>()
        if (event.isNotEmpty()){
            if (event[index].timestamp.equals(0) && !(event[index].timestamp.equals(null)) ) {
                val eventDisplay = getEvent(index, timestamp?.toLong())
                return eventDisplay.size
            }
        }
        println("There are no events, please add them")
        return 0
    }

    fun getEvent(index: Int, timestamp: Long?): ArrayList<DisplayedEvent> {
        val event = ArrayList<Event>()
        val eventDisplay = ArrayList<DisplayedEvent>()
        for (i in event) {
            if (i.timestamp == timestamp) {
                eventDisplay.add(
                    DisplayedEvent(
                        event[index].planeId,
                        event[index].eventType,
                        event[index].fuelDelta
                    )
                )
            }
        }
        return eventDisplay
    }


    fun subtractFuel(oldEvent: ArrayList<Event>, newEvent: ArrayList<Event>, index: Int): ArrayList<Event> {
        val updatedEvent = ArrayList<Event>()

        updatedEvent.add(Event
            (
            newEvent[index].planeId,
            newEvent[index].planeModel,
            newEvent[index].origin,
            newEvent[index].destination,
            newEvent[index].eventType,
            newEvent[index].timestamp,
            oldEvent[index].fuelDelta - abs(newEvent[index].fuelDelta)
            ))

        return updatedEvent
    }

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

    fun alterFuel(event: ArrayList<Event>, fields: List<String>, timestamp: Long) :Int{
        var newFuel = 0
        var indexToChange = 0
        var negativeFuelUpdater = 0
        event.forEach { outlerLoop ->
            if (outlerLoop.planeId == fields[0] && outlerLoop.fuelDelta > 0 && outlerLoop.timestamp == timestamp) {
                negativeFuelUpdater = newFuel
                indexToChange = event.indexOf(outlerLoop)
                replaceData(negativeFuelUpdater, event, indexToChange)
                return -1
            }
            if (outlerLoop.planeId == fields[0] && outlerLoop.fuelDelta > 0 && outlerLoop.timestamp != timestamp) {
                newFuel = outlerLoop.fuelDelta + fields[6].toInt()
            }
            if (outlerLoop.planeId == fields[0] && outlerLoop.fuelDelta == 0) {
                val oldFuel = outlerLoop.fuelDelta
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
