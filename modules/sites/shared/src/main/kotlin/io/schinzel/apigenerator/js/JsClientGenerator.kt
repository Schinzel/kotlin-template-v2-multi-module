package io.schinzel.apigenerator.js


import io.schinzel.apigenerator.Endpoint
import io.schinzel.basic_utils_kotlin.println
import io.schinzel.basicutils.file.FileWriter
import io.schinzel.jstranspiler.transpiler.IToJavaScript
import io.schinzel.jstranspiler.transpiler.KotlinClass
import io.schinzel.jstranspiler.transpiler.KotlinEnum
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

    constructor(sourcePackageName: String, destinationFile: String)
            : this(listOf(sourcePackageName), destinationFile)

    init {
        val startExecutionTime = System.nanoTime()
        // Check so that argument destination file name is ok
        validateFile(destinationFile)


        val endpoints: List<Endpoint> = Reflections(sourcePackageNames, MethodsAnnotated)
            .getMethodsAnnotatedWith(Api::class.java)
            .map { Endpoint(it) }

        val dtoClassesAsJs = endpoints.joinToString("") { endpoint ->

            fun getJsFromDto(dto: Class<*>): String {
                val dto: Class<*> = endpoint.returnDataType
                val transpiler: IToJavaScript = when (dto.isEnum) {
                    true -> KotlinEnum(dto)
                    false -> KotlinClass(dto)
                }
                return transpiler.toJavaScript()
            }

            val sb = StringBuilder()
            if (JsDataTypeMapper.isDto(endpoint.returnDataTypeName)) {
                val js = getJsFromDto(endpoint.returnDataType)
                sb.append(js)
            }
//            endpoint.parameters.forEach { param ->
//                param.println()
//            }
            sb.toString()
        }

        val jsFunctions = endpoints.joinToString("") { endpoint ->
            JsFunction(endpoint).jsFunction
        }


        val fileContent = "" +
                JsFileStaticTexts.HEADER +
                JsFileStaticTexts.DATA_OBJECT_CLASS +
                dtoClassesAsJs +
                ServerCallerStaticTexts.start +
                jsFunctions +
                ServerCallerStaticTexts.end +
                JsFileStaticTexts.SERVER_CALLER_INTERNAL_CLASS

        FileWriter.writer()
            .fileName(destinationFile)
            .content(fileContent)
            .write()

        // Calc execution time
        val jobExecutionTimeInSeconds = (System.nanoTime() - startExecutionTime) /
                1_000_000_000
        val numberOfEndpoints = endpoints.size
        val feedback = "JsClientGenerator ran! " +
                "Generated a file named $destinationFile. " +
                "$numberOfEndpoints Javalin endpoints can now be invoked using the JsClient. " +
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