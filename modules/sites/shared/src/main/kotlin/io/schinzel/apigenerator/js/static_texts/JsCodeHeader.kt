package io.schinzel.apigenerator.js.static_texts

object JsCodeHeader {
    val JAVA_SCRIPT = """
            | // noinspection JSUnusedGlobalSymbols
            |/**
            | * The purpose of this class is to send requests to the server.
            | * There is one function per endpoint in the API.
            | * This class has been automatically generated from the endpoints
            | * set set up with Javalin annotations.
            | */
            |
            |
        """.trimMargin()
}
