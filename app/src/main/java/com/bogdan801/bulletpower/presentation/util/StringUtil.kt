package com.bogdan801.bulletpower.presentation.util


fun String.difference(other: String): String {
    return if (this.length >= other.length) {
        ""
    }
    else {
        var thisI = 0
        var output = ""
        other.forEachIndexed { otherI, c ->
            if (thisI <= this.lastIndex){
                if (c == this[thisI]){
                    thisI++
                }
                else{
                    output += c
                }
            }
            else{
                output += c
            }
        }
        output
    }
}
