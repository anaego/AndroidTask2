import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TwoUserTest {

    @Test
    fun testTwoUserTestMiddle() {
        val user1 = User(1)
        val user2 = User(2)
        val testTicker = TestTicker()
        testTicker.initializeTicker()
        user1.initializeUser(testTicker)
        user2.initializeUser(testTicker)
        testTicker.startTicking(user1, 10)
        Assertions.assertEquals(user1.stopwatchValueReceiver, testTicker.stopwatchValue)
        testTicker.startTicking(user2, 15)
    }

    @Test
    fun testTwoUserTestEnd() {
        val user1 = User(1)
        val user2 = User(2)
        val testTicker = TestTicker()
        testTicker.initializeTicker()
        user1.initializeUser(testTicker)
        user2.initializeUser(testTicker)
        testTicker.startTicking(user1, 10)
        testTicker.startTicking(user2, 15)
        while (user2.stopwatchValueReceiver == 0) {
            continue
        }
        Assertions.assertEquals(user2.stopwatchValueReceiver, testTicker.stopwatchValue)
    }

}