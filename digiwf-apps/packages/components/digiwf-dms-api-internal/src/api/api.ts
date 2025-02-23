/* tslint:disable */
/* eslint-disable */
/**
 * DigiWF DMS API
 * API for dms.
 *
 * The version of the OpenAPI document: ${project.version}
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


import { Configuration } from './configuration';
import globalAxios, { AxiosPromise, AxiosInstance, AxiosRequestConfig } from 'axios';
// Some imports not used depending on template conditions
// @ts-ignore
import { DUMMY_BASE_URL, assertParamExists, setApiKeyToObject, setBasicAuthToObject, setBearerAuthToObject, setOAuthToObject, setSearchParams, serializeDataIfNeeded, toPathString, createRequestFunction } from './common';
// @ts-ignore
import { BASE_PATH, COLLECTION_FORMATS, RequestArgs, BaseAPI, RequiredError } from './base';

/**
 * Metadata of the object.
 * @export
 * @interface Metadata
 */
export interface Metadata {
    /**
     * Name of the object.
     * @type {string}
     * @memberof Metadata
     */
    'name': string;
    /**
     * Type of the content.
     * @type {string}
     * @memberof Metadata
     */
    'type'?: string;
    /**
     * Url of the object.
     * @type {string}
     * @memberof Metadata
     */
    'url': string;
}

/**
 * MetadataApi - axios parameter creator
 * @export
 */
export const MetadataApiAxiosParamCreator = function (configuration?: Configuration) {
    return {
        /**
         * Returns metadata of an objetct.
         * @summary Returns metadata of an objetct.
         * @param {'Sachakte' | 'Vorgang' | 'Eingang' | 'Ausgang' | 'Intern' | 'Schriftstueck'} objectclass Object class.
         * @param {string} coo Coo of object.
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        readMetadata: async (objectclass: 'Sachakte' | 'Vorgang' | 'Eingang' | 'Ausgang' | 'Intern' | 'Schriftstueck', coo: string, options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
            // verify required parameter 'objectclass' is not null or undefined
            assertParamExists('readMetadata', 'objectclass', objectclass)
            // verify required parameter 'coo' is not null or undefined
            assertParamExists('readMetadata', 'coo', coo)
            const localVarPath = `/metadata/{objectclass}/{coo}`
                .replace(`{${"objectclass"}}`, encodeURIComponent(String(objectclass)))
                .replace(`{${"coo"}}`, encodeURIComponent(String(coo)));
            // use dummy base URL string because the URL constructor only accepts absolute URLs.
            const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
            let baseOptions;
            if (configuration) {
                baseOptions = configuration.baseOptions;
            }

            const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options};
            const localVarHeaderParameter = {} as any;
            const localVarQueryParameter = {} as any;


    
            setSearchParams(localVarUrlObj, localVarQueryParameter);
            let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
            localVarRequestOptions.headers = {...localVarHeaderParameter, ...headersFromBaseOptions, ...options.headers};

            return {
                url: toPathString(localVarUrlObj),
                options: localVarRequestOptions,
            };
        },
    }
};

/**
 * MetadataApi - functional programming interface
 * @export
 */
export const MetadataApiFp = function(configuration?: Configuration) {
    const localVarAxiosParamCreator = MetadataApiAxiosParamCreator(configuration)
    return {
        /**
         * Returns metadata of an objetct.
         * @summary Returns metadata of an objetct.
         * @param {'Sachakte' | 'Vorgang' | 'Eingang' | 'Ausgang' | 'Intern' | 'Schriftstueck'} objectclass Object class.
         * @param {string} coo Coo of object.
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        async readMetadata(objectclass: 'Sachakte' | 'Vorgang' | 'Eingang' | 'Ausgang' | 'Intern' | 'Schriftstueck', coo: string, options?: AxiosRequestConfig): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Metadata>> {
            const localVarAxiosArgs = await localVarAxiosParamCreator.readMetadata(objectclass, coo, options);
            return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
        },
    }
};

/**
 * MetadataApi - factory interface
 * @export
 */
export const MetadataApiFactory = function (configuration?: Configuration, basePath?: string, axios?: AxiosInstance) {
    const localVarFp = MetadataApiFp(configuration)
    return {
        /**
         * Returns metadata of an objetct.
         * @summary Returns metadata of an objetct.
         * @param {'Sachakte' | 'Vorgang' | 'Eingang' | 'Ausgang' | 'Intern' | 'Schriftstueck'} objectclass Object class.
         * @param {string} coo Coo of object.
         * @param {*} [options] Override http request option.
         * @throws {RequiredError}
         */
        readMetadata(objectclass: 'Sachakte' | 'Vorgang' | 'Eingang' | 'Ausgang' | 'Intern' | 'Schriftstueck', coo: string, options?: any): AxiosPromise<Metadata> {
            return localVarFp.readMetadata(objectclass, coo, options).then((request) => request(axios, basePath));
        },
    };
};

/**
 * Request parameters for readMetadata operation in MetadataApi.
 * @export
 * @interface MetadataApiReadMetadataRequest
 */
export interface MetadataApiReadMetadataRequest {
    /**
     * Object class.
     * @type {'Sachakte' | 'Vorgang' | 'Eingang' | 'Ausgang' | 'Intern' | 'Schriftstueck'}
     * @memberof MetadataApiReadMetadata
     */
    readonly objectclass: 'Sachakte' | 'Vorgang' | 'Eingang' | 'Ausgang' | 'Intern' | 'Schriftstueck'

    /**
     * Coo of object.
     * @type {string}
     * @memberof MetadataApiReadMetadata
     */
    readonly coo: string
}

/**
 * MetadataApi - object-oriented interface
 * @export
 * @class MetadataApi
 * @extends {BaseAPI}
 */
export class MetadataApi extends BaseAPI {
    /**
     * Returns metadata of an objetct.
     * @summary Returns metadata of an objetct.
     * @param {MetadataApiReadMetadataRequest} requestParameters Request parameters.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     * @memberof MetadataApi
     */
    public readMetadata(requestParameters: MetadataApiReadMetadataRequest, options?: AxiosRequestConfig) {
        return MetadataApiFp(this.configuration).readMetadata(requestParameters.objectclass, requestParameters.coo, options).then((request) => request(this.axios, this.basePath));
    }
}


