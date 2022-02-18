import domain.Event
import event_handler.InputEvent
import event_handler.OutputEvent
import org.junit.Test
import org.junit.Assert.*
import util.Time

class DisplayTest {
    var listOfEvents = ArrayList<Event>()
    val userInput = "F222 747 DUBLIN LONDON Re-Fuel 2021-03-29T10:00:00 200"
    val fields = userInput.split(" ")

    val eventOutput = OutputEvent()
    val inputEvent = InputEvent()
    val time = Time()

    @Test
    fun displayEpochTime(){
        val timestamp = "2021-03-29T15:00:00"
        val epochTime = 1617026400000
        time.convertToEpochTime(timestamp)
        assertEquals(epochTime, time.convertToEpochTime(timestamp))
    }

    @Test
    fun hasEvent(){
        listOfEvents = inputEvent.addData(fields, listOfEvents, time.convertToEpochTime("2021-03-29T10:00:00"))
        assertEquals(1, listOfEvents.size)
    }

    @Test
    fun eventRemoved(){
        listOfEvents = inputEvent.addData(fields, listOfEvents, time.convertToEpochTime("2021-03-29T10:00:00"))
        eventOutput.removeEvent("F222 2021-03-29T10:00:00", listOfEvents)

        assertEquals(0, listOfEvents.size)
    }

}