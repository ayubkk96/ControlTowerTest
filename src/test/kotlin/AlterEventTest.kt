import domain.Event
import domain.RemovedEvent
import event_handler.OutputEvent
import org.junit.Assert.assertEquals
import org.junit.Test
import util.Time

class AlterEventTest {
    val outputEvent = OutputEvent()

    @Test
    fun fuelSubtraction() {
        val fuelEstimate = 200

        val oldEvent = ArrayList<Event>()
        oldEvent.add(
            Event("Hey", 222,
                "London", "Paris",
                "take-off", 2022, 400)
        )

        val newEvent = ArrayList<Event>()
        newEvent.add(
            Event("Hey", 222,
                "London", "Paris",
                "take-off", 2022, -200)
        )
        val updatedEvent = outputEvent.subtractFuel(oldEvent,newEvent, 0)
        val newFuel = updatedEvent[0].fuelDelta

        assertEquals(fuelEstimate, newFuel)
    }

    //@Test
//    fun eventNotRemoved(){
//
//        val removedEvent = ArrayList<RemovedEvent>()
//        removedEvent.add(
//            RemovedEvent(planeId = "Hey",
//            timestamp = 2023)
//        )
//        val event = ArrayList<Event>()
//        event.add(
//            Event("Hey", 222,
//        "London", "Paris",
//        "take-off", 2022, 20)
//        )
//
//        assertEquals(event,outputEvent.removeEvent(removedEvent, event, 0))
//    }

    @Test
    fun timeFormat(){
        val time = Time()
        val timestamp = "2022-10-09T12:00:00"
        assertEquals(1665313200000,time.convertToEpochTime(timestamp))
    }
}