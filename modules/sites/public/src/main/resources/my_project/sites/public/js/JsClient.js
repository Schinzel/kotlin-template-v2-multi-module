/**
 * The purpose of this class is to send requests to the server.
 * There is one function per endpoint in the API.
 * This class has been automatically generated from the endpoints
 * set set up with Javalin annotations.
 */
 // noinspection JSUnusedLocalSymbols
/**
 * This class holds methods common to all transpiled classes.
 */
class DataObject {
    // noinspection JSUnusedGlobalSymbols
    /**
     * return {object} This instance as a json object
     */
    asJsonObject() {
        return JSON.parse(JSON.stringify(this));
    }

    // noinspection JSUnusedGlobalSymbols
    /**
     * return {string} This instance as a json string
     */
    asJsonString() {
        return JSON.stringify(this);
    }

    // noinspection JSUnusedGlobalSymbols
    /**
     * return {object} A clone of this object
     */
    clone() {
        return new this.constructor(this.asJsonObject());
    }
}

