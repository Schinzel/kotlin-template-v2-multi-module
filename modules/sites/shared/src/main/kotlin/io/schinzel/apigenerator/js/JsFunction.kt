package io.schinzel.apigenerator.js

import io.schinzel.apigenerator.Endpoint
import io.schinzel.basic_utils_kotlin.println

//Gör först utan parametrar
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
        val functionName = endpoint.path.substringAfterLast("/")
        val jsReturnDataType = JsDataTypeMapper.getJsDataType(endpoint.returnDataType)
        jsFunction = """
            |
            |    /**
            |     * No description available
            |     * @returns {Promise<$jsReturnDataType>}
            |     */
            |    async $functionName(){
            |        let response = await new ServerCallerInt()
            |            .setPath('${endpoint.path}')
            |            .callWithPromise();
            |        return response;
            |    }
            |
        """.trimMargin()
    }

}