package event_handler

import domain.Event
import domain.RemovedEvent
import mu.KotlinLogging
import util.Time
import java.lang.NumberFormatException

class InputEvent {
    private val logger = KotlinLogging.logger {}
    val time = Time()
    val outputEvent = OutputEvent()
    fun getUserInput(userInput: String?): String? {
        try {
            if (userInput != null) {
                println("well done, it is not null")
                return userInput
            }
        } catch (e: NullPointerException) {
            (logger.error { "You have not entered any event" })
        }
        return userInput
    }

    fun getFieldsCount(userInput: String?): Int {
        try {
            if (userInput != null) {
                val fields = userInput.split(" ")
                return fields.size
            }
        } catch (e: NullPointerException) {
            (logger.error { "The user input is null" })
        }
        return 0
    }


    fun dataValidator(userInput: String?): Boolean {
        if (userInput != null) {
            val fields = userInput.split(" ")
            var timestamp = time.convertToEpochTime(fields[5])
            println(fields.size)
            val eventToBeRemoved = ArrayList<RemovedEvent>()
            val event = ArrayList<Event>()
            if (fields.size == 2) {
                eventToBeRemoved.add(
                    RemovedEvent(fields[0], fields[1].toLong())
                )
                return true
            }
            if (fields.size == 7) {
                event.add(
                    Event(
                        fields[0], fields[1].toInt(),
                        fields[2], fields[3],
                        fields[4], timestamp,
                        fields[6].toInt()
                    )
                )
                return true
            }
        }
        return false
    }


    //User adds events
    //program should add the events and then once it spots a negative fuel,
    // go back into the events list and check the correlated plane id and subtract its fuel
    fun addData(fields: List<String>, event: ArrayList<Event>, timestamp: Long ): ArrayList<Event> {
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