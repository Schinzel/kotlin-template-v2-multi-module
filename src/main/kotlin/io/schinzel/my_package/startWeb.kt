package io.schinzel.my_package

import com.atexpose.AtExpose
import com.atexpose.dispatcher_factories.CliFactory
import com.atexpose.dispatcher_factories.WebServerBuilder
import io.schinzel.basic_utils_kotlin.println

/**
 * Starts a web server and a command line interface.
 * The web server serves the files in the folder "site".
 * The command line interface can be used to call the functions in the
 * class ExampleClass.
 *
 * Example calls in browser:
 * http://127.0.0.1:5555/
 * http://127.0.0.1:5555/api/doubleIt?Int=3
 *
 * Example command in cli:
 * doubleIt 10
 */
fun main() {
    AtExpose.create()
        .expose(ExampleClass())
        // Start web server
        .start(WebServerBuilder.create()
            .webServerDir("site")
            .build())
        // Start command line interface
        .start(CliFactory.create())
    "*".repeat(30).println()
    "Web server and cli started.".println()
    "*".repeat(30).println()
}