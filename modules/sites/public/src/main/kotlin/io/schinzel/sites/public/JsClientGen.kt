package io.schinzel.sites.public

import io.schinzel.JsClientGenerator

fun main() {
    JsClientGenerator(
        sourcePackageName = "io.schinzel.sites.public",
        destinationFile = "modules/sites/public/src/main/resources/my_project/sites/public/js/JsClient.js"
    )
    //JsClientGenerator("io.schinzel.sites.public", "JsClient.js")
}

