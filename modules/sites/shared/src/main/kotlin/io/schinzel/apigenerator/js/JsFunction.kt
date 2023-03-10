package io.schinzel.apigenerator.js

import io.schinzel.apigenerator.Endpoint

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

    val jsFunction: String

    init {
        // Get JsDoc parameters
        var jsDocParameters = endpoint.parameters
            .joinToString("\n") { endpointParameter ->
                val parameterName = endpointParameter.name
                val jsDataType = JsDataTypeMapper
                    .getJsDataType(endpointParameter.dataType)

                "     * @param {$jsDataType} $parameterName"
            }
        if (jsDocParameters.isEmpty()) {
            jsDocParameters = "     *"
        }

        val jsFunctionName = endpoint.path.substringAfterLast("/")
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
        jsFunction = """
                |
                |
                |    /**
                |     * No description available
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