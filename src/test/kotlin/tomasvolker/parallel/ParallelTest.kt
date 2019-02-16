package tomasvolker.parallel

import org.junit.Test
import kotlin.math.absoluteValue
import kotlin.random.Random

class ParallelTest {

    @Test
    fun map() {

        Random(0).run {

            val list = List(1024) { nextInt() }

            assert(
                list.mapParallel(100) { (2 * it).absoluteValue * it } ==
                list.map { (2 * it).absoluteValue * it  }
            ) { "mapParallel and map not returning the same result" }

        }

    }

    @Test
    fun reduce() {

        Random(0).run {

            val list = List(1024) { nextInt() }

            assert(
                list.reduceParallel(100) { acc, next -> acc + next } ==
                list.reduce { acc, next -> acc + next }
            ) { "reduceParallel and reduce not returning the same result" }

        }

    }

}