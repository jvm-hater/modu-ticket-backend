import java.time.Clock
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit
import org.jetbrains.annotations.TestOnly

// 유닛 테스트에서의 시간을 고정하기 위한 코드
// useFixedClock을 통해 now()를 고정시키고
// resetClock을 통해 원복시킨다.
// 아래 2개의 함수는 테스트 목적으로만 사용하고 실코드에서는 사용하지 않는다.
private var systemClock = Clock.systemUTC()

@TestOnly
fun useFixedClock(clock: Clock) {
    systemClock = clock
}

@TestOnly
fun resetClock() {
    systemClock = Clock.systemUTC()
}

fun now(): LocalDateTime {
    return LocalDateTime.now(systemClock)
}

fun Long.elapsedTimeInMillis() = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - this)
