package sample

import java.io.File
import java.util.*

object DsaSigner {
    data class SignatureKeys(val x: Int, val y: Int, val g: Int)

    fun signFile(file: File, q: Int, p: Int): Pair<ByteArray, SignatureKeys> {
        val byteArray = file.readBytes()
        val hash = HashUtil.getHash(byteArray, q)

        val g = getG(p, q)
        val x = MathUtil.randomTo(q)
        val y = MathUtil.fastExp(g, x, p)

        var k: Int
        var r: Int
        var s: Int

        do {
            k = MathUtil.randomTo(q)
            r = MathUtil.fastExp(g, k, p).rem(q)
            s = (MathUtil.fastExp(k, q -2, q) * ((hash + x * r).rem(q))).rem(q)
        } while (r == 0 || s == 0)

        val outputArray = LinkedList<Byte>(byteArray.toList())
        outputArray.add(0)
        outputArray.add(r.toByte())
        outputArray.add(s.toByte())

        print("g = $g; x = $x; k = $k; r = $r; s = $s\n")

        return Pair(outputArray.toByteArray(), SignatureKeys(x, y, g))
    }

    fun checkSign(file: File, q: Int, p: Int, g: Int, y: Int): Boolean {
        val byteArray = file.readBytes()
        val delimiterIndex = byteArray.lastIndex - 2
        if (byteArray[delimiterIndex] != 0.toByte()) return false

        val fileBytesArray = byteArray.copyOf(delimiterIndex)
        val s = byteArray.last()
        val r = byteArray[byteArray.lastIndex - 1]
        val hash = HashUtil.getHash(fileBytesArray, q)

        val w = MathUtil.fastExp(s.toInt(), q-2, q)
        val u1 = (hash * w).rem(q)
        val u2 = (r * w).rem(q)
        val v = ((MathUtil.fastExp(g, u1, p) * MathUtil.fastExp(y, u2, p)) % p) % q

        return r.toInt() == v
    }

    private fun getG(p: Int, q: Int): Int {
        var h: Int
        var g: Int
        do {
            h = MathUtil.randomTo(p - 1)
            g = MathUtil.fastExp(h, (p - 1) / q, p)
        } while (g <= 1)

        return g
    }
}