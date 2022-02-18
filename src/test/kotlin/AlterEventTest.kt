import domain.Event
import domain.Plane
import event_handler.InputEvent
import event_handler.OutputEvent
import org.junit.Assert.assertEquals
import org.junit.Test
import util.Time

class AlterEventTest {
    val outputEvent = OutputEvent()
    val inputEvent = InputEvent()
    val time = Time()

    @Test
    fun fuelSubtracted() {
        val oldFuel = 400
        val fuelEstimate = 200
        var event = ArrayList<Event>()

        event.add(
            Event("F222", 747,
                "DUBLIN", "LONDON",
                "Re-Fuel", time.convertToEpochTime("2021-03-29T10:00:00"),
                oldFuel)
        )

        val userInput = "F222 747 DUBLIN LONDON Re-Fuel 2021-03-29T11:00:00 -200"
        val fields = userInput.split(" ")

        event = inputEvent.addData(fields,event, time.convertToEpochTime(fields[5]))

        assertEquals(fuelEstimate,event[1].fuelDelta)
    }

    @Test
    fun timeFormat(){
        val time = Time()
        val timestamp = "2022-10-09T12:00:00"
        assertEquals(1665313200000,time.convertToEpochTime(timestamp))
    }

    @Test
    fun fuelReplaced(){
        val event = ArrayList<Event>()
        val oldFuel = 400
        val newFuel = 300

        event.add(
            Event("Hey", 222,
                "London", "Paris",
                "take-off", 2022, oldFuel)
        )

        outputEvent.replaceData(newFuel,event, 0)
        assert(event[0].fuelDelta == newFuel)
    }

    @Test
    fun eventRenamed(){
        var event = ArrayList<Plane>()
        event.add(
            Plane("F222", 1,
                "Re-Fuel",300)
        )

        event = outputEvent.renameEvent(event)
        assert(event[0].eventType == "Awaiting-Takeoff")
    }
}