package io.schinzel.apigenerator.js.static_texts

object JsCodeDataObjectClass {
    val JAVA_SCRIPT = """
            | // noinspection JSUnusedLocalSymbols
            |/**
            | * This class holds methods common to all transpiled classes.
            | */
            |class DataObject {
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * return {object} This instance as a json object
            |     */
            |    asJsonObject() {
            |        return JSON.parse(JSON.stringify(this));
            |    }
            |
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * return {string} This instance as a json string
            |     */
            |    asJsonString() {
            |        return JSON.stringify(this);
            |    }
            |}
            |
            |
        """.trimMargin()
}