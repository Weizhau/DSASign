package sample

object HashUtil {
    fun getHash(byteArray: ByteArray, q: Int): Int {
        var currentH = 100
        byteArray.forEach { currentH = (currentH + it).powTwo().rem(q) }

        return currentH
    }

    private fun Int.powTwo() = this * this
}