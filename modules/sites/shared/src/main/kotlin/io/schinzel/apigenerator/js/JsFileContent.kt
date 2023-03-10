package io.schinzel.apigenerator.js

import io.schinzel.apigenerator.js.static_texts.JsCodeDataObjectClass
import io.schinzel.apigenerator.js.static_texts.JsCodeHeader
import io.schinzel.apigenerator.js.static_texts.JsCodeInternalServerCaller

class JsFileContent(
    dtoClassesAsJs: String,
    endpointsAsJs: String,
    javaScriptClassName: String
) {
    val javaScript: String

    init {
        javaScript = JsCodeHeader.JAVA_SCRIPT +
                JsCodeDataObjectClass.JAVA_SCRIPT +
                dtoClassesAsJs +
                "export class $javaScriptClassName {" +
                endpointsAsJs +
                "}\n" +
                JsCodeInternalServerCaller.JAVA_SCRIPT
    }
}