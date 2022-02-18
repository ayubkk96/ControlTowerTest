package repl

import domain.Event
import event_handler.InputEvent
import event_handler.OutputEvent
import util.Time
import java.lang.NumberFormatException
import java.text.ParseException
import java.util.*
import kotlin.collections.ArrayList

class ReplInterface {

    val instructions = "insert 1 to add or update event(s), " +
            "2 to view event(s) or" +
            " 3 to remove an event(s)"
    val outputEvent = OutputEvent()
    val inputEvent = InputEvent()
    var event = ArrayList<Event>()
    var eventCount = 8
    val time = Time()

    fun run() {
        println("You are entering the REPL")
        val scanner = Scanner(System.`in`)
        println(instructions)
        while (scanner.hasNext()) {
            var userInput: String
            scanner.useDelimiter("\\t")
            userInput = scanner.nextLine()
            if (userInput == "1") {
                println("How much events would you like to add?")
                userInput = scanner.nextLine()
                try {
                    eventCount = userInput.toInt()
                } catch (e: NumberFormatException) {
                    println("You have not entered a number, $instructions")
                    continue
                }
                if (eventCount != 0) {
                    println("Please add your event(s)")
                }
                for (i in 1..eventCount) {
                    userInput = scanner.nextLine()
                    val fields = userInput.split(" ")
                    try {
                        val timestamp = time.convertToEpochTime(fields[5])
                        event = inputEvent.addData(fields, event, timestamp)
                    } catch (e: IndexOutOfBoundsException) {
                        println("The input:$fields is not in the correct format")
                        break
                    }
                }
                println(instructions)
            }
            if (userInput == "2") {
                println("Please insert the timestamp for the event(s) you want to see")
                userInput = scanner.nextLine()
                if (userInput != "") {
                    try {
                        val timestampEpoch = time.convertToEpochTime(userInput)
                        outputEvent.renameEvent(time.closestToTime(timestampEpoch, event))
                        println(instructions)
                    } catch (e: ParseException) {
                        println("you have not entered a correct timestamp, $instructions")
                        continue
                    }
                }
            }
            if (userInput == "3") {
                println("Which event would you like to remove?")
                userInput = scanner.nextLine()
                event = outputEvent.removeEvent(userInput, event)
                println(instructions)
            }


        }
    }
}