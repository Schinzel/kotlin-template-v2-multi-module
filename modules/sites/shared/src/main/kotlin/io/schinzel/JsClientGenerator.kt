package io.schinzel


import com.google.common.annotations.VisibleForTesting
import io.schinzel.basic_utils_kotlin.println
import io.schinzel.basic_utils_kotlin.printlnWithPrefix
import io.schinzel.basicutils.file.FileWriter
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner
import org.reflections.scanners.Scanners
import org.reflections.scanners.Scanners.*
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import se.refur.javalin.Api
import se.refur.javalin.Param
import java.lang.reflect.Method
import java.net.URL


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
                val annotation = method.getAnnotation(Api::class.java)
                annotation.path.printlnWithPrefix("Path")
                annotation.type.printlnWithPrefix("Handler type")
                method.parameters
                    .filter { it.isAnnotationPresent(Param::class.java) }
                    .size
                    .printlnWithPrefix("Number of params")
                method.parameters
                    .filter { it.isAnnotationPresent(Param::class.java) }
                    .forEach { param ->
                        val annotation = param.getAnnotation(Param::class.java)
                        annotation.paramName.printlnWithPrefix("Param name")
                        annotation.parameterType.printlnWithPrefix("Param type")
                        param.type.simpleName.printlnWithPrefix("Param type")
                    }
            }

        /**
         * Sökt:
         * 1) route
         * 2) param names
         * 3) param types
         */


        val fileContent = "" +
                HEADER +
                DATA_OBJECT_CLASS

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
                throw Exception("Destination file must have the extension js")
            }
        }


        private val HEADER = """
            |/**
            | * The purpose of this class is to send requests to the server.
            | * There is one function per endpoint in the API.
            | * This class has been automatically generated from the endpoints
            | * set set up with Javalin annotations.
            | */
            |
        """.trimMargin()


        val DATA_OBJECT_CLASS = """
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
            |
            |    // noinspection JSUnusedGlobalSymbols
            |    /**
            |     * return {object} A clone of this object
            |     */
            |    clone() {
            |        return new this.constructor(this.asJsonObject());
            |    }
            |}
            |
            |
        """.trimMargin()
    }

}