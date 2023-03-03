package io.schinzel.sites.public_site

import io.schinzel.JsClientGenerator

fun main() {
    JsClientGenerator(
        sourcePackageNames = listOf(
            "io.schinzel.sites.public_site.myapi1",
            "io.schinzel.sites.public_site.myapi2"
        ),
        destinationFile = "modules/sites/public/src/main/resources/my_project/sites/public/js/JsClient.js"
    )
}

