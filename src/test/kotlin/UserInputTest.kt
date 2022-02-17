import event_handler.InputEvent
import org.junit.Assert.*
import org.junit.Test


class UserInputTest {
    var flightEventInput = InputEvent()

    @Test
    fun eventIsNull(){
        val flightEventInput = InputEvent()
        assertNull(flightEventInput.getUserInput(null))
    }

    @Test
    fun countEventFields() {
        val event = flightEventInput.getUserInput("Hello this is a juxt-test to pass")
        assertEquals(7, flightEventInput.getFieldsCount(event))
    }

    @Test
    fun validateEventInput(){
        assert(flightEventInput.dataValidator("Hello 2 is a juxt-test to 7"))
    }

    @Test
    fun countRemoveEventInput(){
        assertEquals(2, flightEventInput.getFieldsCount("Hello Pass"))
    }

    @Test
    fun validateRemoveEventInput(){
        assert(flightEventInput.dataValidator("Hello Hello"))
    }

}