package io.schinzel.apigenerator.js

import io.schinzel.apigenerator.Endpoint
import io.schinzel.apigenerator.EndpointParameter

/**
 *    /**
 *      * No description available
 *      * @param {string} process_id - An identifier for a process
 *      * @param {string} note_text - A description
 *      * @returns {Promise<void>}
 *      */
 *     async addProcessNote(process_id, note_text){
 *         let response = await new ServerCallerInt()
 *             .setPath('/api/addProcessNote')
 *             .addArg('process_id', process_id)
 *             .addArg('note_text', note_text)
 *             .callWithPromise();
 *     }
 */
class JsFunction(endpoint: Endpoint) {

    val javaScript: String

    init {
        // Get JsDoc parameters
        var jsDocParameters = endpoint.parameters
            .joinToString("\n") { endpointParameter: EndpointParameter ->
                val jsDataType = JsDataTypeMapper
                    .getJsDataType(endpointParameter.dataType)
                val jsParameterName = endpointParameter.name
                val jsDescription  = when (endpointParameter.description.length) {
                    0 -> ""
                    else -> " - " + endpointParameter.description
                }

                "     * @param {$jsDataType} $jsParameterName$jsDescription"
            }
        if (jsDocParameters.isEmpty()) {
            jsDocParameters = "     *"
        }

        val jsFunctionName = endpoint.path.substringAfterLast("/")
        val jsFunctionDescription = when (endpoint.description.isEmpty()){
            true -> "No description available"
            else -> endpoint.description
        }
        val jsFunctionParameters = endpoint.parameters
            .joinToString(", ") { endpointParameter ->
                endpointParameter.name
            }

        val jsServerCallerArguments: String = when (endpoint.parameters.size) {
            0 -> ""
            else -> endpoint.parameters
                .joinToString("\n") { endpointParameter ->
                    val parameterName = endpointParameter.name
                    "            .addArg('$parameterName', $parameterName)"
                }
        }


        val jsReturnDataType = JsDataTypeMapper.getJsDataType(endpoint.returnDataTypeName)
        javaScript = """
                |
                |
                |    /**
                |     * $jsFunctionDescription
                |$jsDocParameters
                |     * @returns {Promise<$jsReturnDataType>}
                |     */
                |    async $jsFunctionName($jsFunctionParameters){
                |        return await new ServerCallerInt()
                |            .setPath('${endpoint.path}')
                |$jsServerCallerArguments            
                |            .callWithPromise();
                |    }
                |
                """.trimMargin()
    }

}