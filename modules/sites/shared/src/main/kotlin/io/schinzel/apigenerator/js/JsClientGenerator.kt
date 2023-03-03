package io.schinzel.apigenerator.js


import io.schinzel.apigenerator.Endpoint
import io.schinzel.basic_utils_kotlin.println
import io.schinzel.basic_utils_kotlin.printlnWithPrefix
import io.schinzel.basicutils.file.FileWriter
import org.reflections.Reflections
import org.reflections.scanners.Scanners.MethodsAnnotated
import se.refur.javalin.Api
import java.lang.reflect.Method


/**
 * The central method for this project. Generates a JavaScript file containing
 * a client to invoke the endpoints in the argument list of packages.
 *
 * @param sourcePackageNames A list of names of Kotlin packages in which to look
 * for Javalin annotations endpoints.
 * @param destinationFile The name of the file into which the generated JavaScript
 * will be written. E.g.
 * "src/main/resources/my_site/js/classes.js"
 * "modules/sites/public/src/main/resources/my_project/sites/public/js/JsClient.js"
 *
 */
class JsClientGenerator(sourcePackageNames: List<String>, destinationFile: String) {

    constructor(sourcePackageName: String, destinationFile: String)
            : this(listOf(sourcePackageName), destinationFile)

    init {
        val startExecutionTime = System.nanoTime()
        // Check so that argument destination file name is ok
        validateFile(destinationFile)


        Reflections(sourcePackageNames, MethodsAnnotated)
            .getMethodsAnnotatedWith(Api::class.java)
            .size
            .printlnWithPrefix("1")

        Reflections(sourcePackageNames, MethodsAnnotated)
            .getMethodsAnnotatedWith(Api::class.java)
            .forEach { method: Method ->
                val endpoint = Endpoint(method)

                endpoint.printlnWithPrefix("Endpoint")
                endpoint.parameters.forEach { param ->
                    param.printlnWithPrefix("Param")
                }
            }

        val endpoints: List<Endpoint> = Reflections(sourcePackageNames, MethodsAnnotated)
            .getMethodsAnnotatedWith(Api::class.java)
            .map { Endpoint(it) }

        val jsFunctions = endpoints.joinToString("") { JsFunction(it).jsFunction }

        val fileContent = "" +
                JsFileStaticTexts.HEADER +
                ServerCallerStaticTexts.start +
                jsFunctions +
                ServerCallerStaticTexts.end +
                JsFileStaticTexts.SERVER_CALLER_INTERNAL_CLASS +
                JsFileStaticTexts.DATA_OBJECT_CLASS

        FileWriter.writer()
            .fileName(destinationFile)
            .content(fileContent)
            .write()

        // Calc execution time
        val jobExecutionTimeInSeconds = (System.nanoTime() - startExecutionTime) /
                1_000_000_000
        val feedback = "JsClientGenerator ran! " +
                "Generated a file named $destinationFile. " +
                "0000 Javalin endpoints can now be invoked using the JsClient. " +
                "The job took $jobExecutionTimeInSeconds seconds. "
        feedback.println()
    }


    companion object {
        private fun validateFile(fileName: String) {
            if (!fileName.endsWith(".js")) {
                throw Exception("Destination file must have the extension '.js'")
            }
        }
    }
}