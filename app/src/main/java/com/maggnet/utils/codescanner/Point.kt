
package com.maggnet.utils.codescanner

internal class Point(val x: Int, val y: Int) {

    override fun hashCode(): Int {
        return x xor (y shl Integer.SIZE / 2 or (y ushr Integer.SIZE / 2))
    }

    override fun equals(obj: Any?): Boolean {
        return if (obj === this) {
            true
        } else if (obj is Point) {
            val other =
                obj
            x == other.x && y == other.y
        } else {
            false
        }
    }

    override fun toString(): String {
        return "(" + x + "; " + y + ")"
    }

}