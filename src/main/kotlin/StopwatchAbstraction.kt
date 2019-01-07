import kotlinx.coroutines.*


interface Ticker {
    fun startTicking(user : User, userStopsWhenStopwatchIs: Int)
    fun stopTicking(user : User)
}

class TestTicker() : Ticker {
    var stopwatchValue = 0
    val activeUsers = mutableListOf<User>()
    var stopwatchDone = false
    val hasProcessedStopwatchUpdate: (User) -> Boolean = { it.stopwatchValueReceiverChanged == false }

    fun initializeTicker() {
        GlobalScope.launch {
            while (!stopwatchDone) {
                // we check if stopwatch should be running and all users are ready to receive new values
                if (activeUsers.isNotEmpty() && activeUsers.all(hasProcessedStopwatchUpdate)) {
                    stopwatchValue = stopwatchValue.inc()
                    println("True stopwatch value: " + stopwatchValue)
                    activeUsers.forEach { u -> u.stopwatchValueReceiverChanged = true }
                    activeUsers.forEach { u -> u.stopwatchValueReceiver = stopwatchValue }
                }
            }
        }
    }

    override fun stopTicking(user : User) {
        activeUsers.remove(user)
    }

    override fun startTicking(user: User, userStopsWhenStopwatchIs: Int) {
        activeUsers.add(user)
        user.stopWhenStopwatchIs = userStopsWhenStopwatchIs
    }
}


class User(val id : Int) {
    var stopwatchValueReceiver = 0
    var stopwatchValueReceiverChanged = false
    var userDone = false
    var stopWhenStopwatchIs = 0

    fun initializeUser(ticker : TestTicker) {
        GlobalScope.launch {
            stopwatchValueReceiverChanged = false
            while (!userDone) {
                if (stopwatchValueReceiverChanged) {
                    println("User id: " + id + ", stopwatch value: " + stopwatchValueReceiver)
                    if (stopWhenStopwatchIs == stopwatchValueReceiver) {
                        userDone = true
                        ticker.stopTicking(this@User)
                    }
                    stopwatchValueReceiverChanged = false
                }

            }
        }
    }
}


fun main() = runBlocking {
//    val user1 = User(1)
//    val testTicker = TestTicker()
//    testTicker.initializeTicker()
//    user1.initializeUser(testTicker)
//    testTicker.startTicking(user1, 10)
    val user1 = User(1)
    val user2 = User(2)
    val testTicker = TestTicker()
    testTicker.initializeTicker()
    user1.initializeUser(testTicker)
    user2.initializeUser(testTicker)
    testTicker.startTicking(user1, 5)
    testTicker.startTicking(user2, 10)
}


fun sleepForSeconds(seconds : Long) {
    runBlocking {
        delay(seconds * 1000)
        println("Waited " + seconds + " seconds")
    }
}