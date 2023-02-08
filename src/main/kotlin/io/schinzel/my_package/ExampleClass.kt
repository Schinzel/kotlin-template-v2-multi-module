package io.schinzel.my_package

import com.atexpose.Expose

class ExampleClass {
    @Expose(arguments = ["Int"])
    fun doubleIt(number: Int): Int {
        return number * 2
    }
}