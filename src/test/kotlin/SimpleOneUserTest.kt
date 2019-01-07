import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SimpleOneUserTest {

    @Test
    fun testSimpleOneUserTestEnd() {
        val user1 = User(1)
        val testTicker = TestTicker()
        testTicker.initializeTicker()
        user1.initializeUser(testTicker)
        testTicker.startTicking(user1, 10)
        // have to wait here
        while (user1.stopwatchValueReceiver != 10) {
            continue
        }
        Assertions.assertEquals(10, testTicker.stopwatchValue)
    }

    @Test
    fun testSimpleOneUserTestMiddle() {
        val user1 = User(1)
        val testTicker = TestTicker()
        testTicker.initializeTicker()
        user1.initializeUser(testTicker)
        testTicker.startTicking(user1, 10)
        Assertions.assertEquals(user1.stopwatchValueReceiver, testTicker.stopwatchValue)
    }
}