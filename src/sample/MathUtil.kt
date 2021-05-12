package sample

object MathUtil {
    fun fastExp(a: Int, z: Int, n: Int): Int {
        if (z == 0) return 1
        val hz = fastExp(a, z / 2, n)
        return if (z % 2 == 0) ((hz * hz) % n) else ((a * hz * hz) % n)
    }

    fun randomTo(num: Int): Int {
        val random = (Math.random() * num).toInt()
        return if (random < 1) 1 else random
    }
}