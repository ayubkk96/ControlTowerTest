import domain.DisplayedEvent
import domain.Event
import event_handler.InputEvent
import event_handler.OutputEvent
import org.junit.Test
import org.junit.Assert.*
import repl.ReplInterface

class DisplayTest {
    val eventOutput = OutputEvent()
    val inputEvent = InputEvent()
    @Test
    fun isEmpty() {

        assertEquals(0,eventOutput.getEventsSize(null, 0))
    }

    @Test
    fun showEvents() {

        val replInterface = ReplInterface()
       // replInterface.begin()

        val newEvent = eventOutput.getEvent(0, 2022)
        println(newEvent)
        assert(true)
    }

}