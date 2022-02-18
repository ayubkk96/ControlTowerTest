package event_handler

import domain.Event

/**
 * A InputEvent class that has methods that
 * help input the events*/

class InputEvent {
    val outputEvent = OutputEvent()

    fun addData(fields: List<String>, event: ArrayList<Event>, timestamp: Long): ArrayList<Event> {
        val newFuel: Int
        if (fields[6].toInt() <= 0) {
            newFuel = outputEvent.alterFuel(event, fields, timestamp)
            if (newFuel != -1) {
                event.add(
                    Event(
                        fields[0], fields[1].toInt(),
                        fields[2], fields[3], fields[4],
                        timestamp, newFuel
                    )
                )
            }
        } else {
            event.add(
                Event(
                    fields[0], fields[1].toInt(), fields[2],
                    fields[3], fields[4], timestamp,
                    fields[6].toInt()
                )
            )
        }
        return event
    }
}