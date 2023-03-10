package io.schinzel.sites.public_site

import io.schinzel.apigenerator.js.JsClientGenerator


fun main() {
    JsClientGen.generate()
}

object JsClientGen {
    fun generate() {
        JsClientGenerator(
            sourcePackageNames = listOf(
                "io.schinzel.sites.public_site.myapi1",
                "io.schinzel.sites.public_site.myapi2"
            ),
            destinationFile = "modules/sites/public/src/main/resources/my_project/sites/public/js/JsClient.js",
            javaScriptClassName = "ServerCaller"
        )

    }
}