package io.schinzel.sites.site_starter

import io.schinzel.basicutils.configvar.ConfigVar
import io.schinzel.sites.admin.AdminWebServer
import io.schinzel.sites.public.PublicWebServer


fun main() {
    val siteToStart = ConfigVar.create(".env").getValue("SITE")
    when (siteToStart) {
        "public" -> PublicWebServer()
        "admin" -> AdminWebServer()
        else -> throw IllegalArgumentException("Unknown site $siteToStart")
    }
    println("Web server started for site $siteToStart")

}