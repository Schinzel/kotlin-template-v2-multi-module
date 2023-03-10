package io.schinzel.apigenerator.js


import io.schinzel.apigenerator.Endpoint
import io.schinzel.apigenerator.js.static_texts.JsCodeDataObjectClass
import io.schinzel.apigenerator.js.static_texts.JsCodeHeader
import io.schinzel.apigenerator.js.static_texts.JsCodeInternalServerCaller
import io.schinzel.basic_utils_kotlin.println
import io.schinzel.basicutils.file.FileWriter
import org.reflections.Reflections
import org.reflections.scanners.Scanners.MethodsAnnotated
import se.refur.javalin.Api


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

    @Suppress("unused")
    constructor(sourcePackageName: String, destinationFile: String)
            : this(listOf(sourcePackageName), destinationFile)

    init {
        val startExecutionTime = System.nanoTime()
        // Check so that argument destination file name is ok
        validateFile(destinationFile)
        // Get all endpoints
        val endpoints: List<Endpoint> = getEndpoints(sourcePackageNames)
        // Generate JavaScript for DTOs
        val dtoClassesAsJs = JsDtoGenerator(endpoints).javaScript
        // Generate a JavaScript function for each endpoints
        val endpointsAsJs = endpoints.joinToString("") { endpoint ->
            JsFunction(endpoint).javaScript
        }
        // Compile file content
        val fileContent = getFileContent(dtoClassesAsJs, endpointsAsJs)
        // Write file content to file
        writeContentToFile(destinationFile, fileContent)
        // Print user feedback
        compileUserFeedback(destinationFile, endpoints.size, startExecutionTime)
            .println()
    }


    companion object {

        private fun validateFile(fileName: String) {
            if (!fileName.endsWith(".js")) {
                throw Exception("Destination file must have the extension '.js'")
            }
        }


        private fun getEndpoints(sourcePackageNames: List<String>): List<Endpoint> {
            return Reflections(sourcePackageNames, MethodsAnnotated)
                .getMethodsAnnotatedWith(Api::class.java)
                .map { Endpoint(it) }
                .sortedBy { it.functionName }
        }


        private fun getFileContent(dtoClassesAsJs: String, endpointsAsJs: String): String {
            return JsCodeHeader.JAVA_SCRIPT +
                    JsCodeDataObjectClass.JAVA_SCRIPT +
                    dtoClassesAsJs +
                    "export class ServerCaller {\n" +
                    endpointsAsJs +
                    "}\n" +
                    JsCodeInternalServerCaller.JAVA_SCRIPT
        }


        private fun writeContentToFile(destinationFile: String, fileContent: String) {
            FileWriter.writer()
                .fileName(destinationFile)
                .content(fileContent)
                .write()
        }


        private fun compileUserFeedback(
            destinationFile: String,
            numberOfEndpoints: Int,
            startExecutionTime: Long
        ): String {
            // Calc execution time
            val jobExecutionTimeInSeconds = (System.nanoTime() - startExecutionTime) /
                    1_000_000_000
            return "JsClientGenerator ran! " +
                    "Generated a file named $destinationFile. " +
                    "$numberOfEndpoints Javalin endpoints can now be invoked using the JsClient. " +
                    "The job took $jobExecutionTimeInSeconds seconds. "
        }
    }
}