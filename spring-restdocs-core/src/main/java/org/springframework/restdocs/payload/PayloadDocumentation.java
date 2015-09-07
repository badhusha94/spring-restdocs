/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.restdocs.payload;

import java.util.Arrays;
import java.util.Map;

import org.springframework.restdocs.snippet.Snippet;

/**
 * Static factory methods for documenting a RESTful API's request and response payloads.
 * 
 * @author Andreas Evers
 * @author Andy Wilkinson
 */
public abstract class PayloadDocumentation {

	private PayloadDocumentation() {

	}

	/**
	 * Creates a {@code FieldDescriptor} that describes a field with the given
	 * {@code path}.
	 * <p>
	 * When documenting an XML payload, the {@code path} uses XPath, i.e. '/' is used to
	 * descend to a child node.
	 * <p>
	 * When documenting a JSON payload, the {@code path} uses '.' to descend into a child
	 * object and ' {@code []}' to descend into an array. For example, with this JSON
	 * payload:
	 * 
	 * <pre>
	 * {
     *    "a":{
     *        "b":[
     *            {
     *                "c":"one"
     *            },
     *            {
     *                "c":"two"
     *            },
     *            {
     *                "d":"three"
     *            }
     *        ]
     *    }
     * }
	 * </pre>
	 * 
	 * The following paths are all present:
	 * 
	 * <table summary="Paths and their values">
	 * <tr>
	 * <th>Path</th>
	 * <th>Value</th>
	 * </tr>
	 * <tr>
	 * <td>{@code a}</td>
	 * <td>An object containing "b"</td>
	 * </tr>
	 * <tr>
	 * <td>{@code a.b}</td>
	 * <td>An array containing three objects</td>
	 * </tr>
	 * <tr>
	 * <td>{@code a.b[]}</td>
	 * <td>An array containing three objects</td>
	 * </tr>
	 * <tr>
	 * <td>{@code a.b[].c}</td>
	 * <td>An array containing the strings "one" and "two"</td>
	 * </tr>
	 * <tr>
	 * <td>{@code a.b[].d}</td>
	 * <td>The string "three"</td>
	 * </tr>
	 * </table>
	 * 
	 * @param path The path of the field
	 * @return a {@code FieldDescriptor} ready for further configuration
	 */
	public static FieldDescriptor fieldWithPath(String path) {
		return new FieldDescriptor(path);
	}

	/**
	 * Returns a handler that will produce a snippet documenting the fields of the API
	 * call's request.
	 * <p>
	 * If a field is present in the request, but is not documented by one of the
	 * descriptors, a failure will occur when the handler is invoked. Similarly, if a
	 * field is documented, is not marked as optional, and is not present in the request,
	 * a failure will also occur. For payloads with a hierarchical structure, documenting
	 * a field is sufficient for all of its descendants to also be treated as having been
	 * documented.
	 * 
	 * @param descriptors The descriptions of the request's fields
	 * @return the handler
	 * @see #fieldWithPath(String)
	 */
	public static Snippet requestFields(FieldDescriptor... descriptors) {
		return new RequestFieldsSnippet(Arrays.asList(descriptors));
	}

	/**
	 * Returns a handler that will produce a snippet documenting the fields of the API
	 * call's request. The given {@code attributes} will be available during snippet
	 * generation.
	 * <p>
	 * If a field is present in the request, but is not documented by one of the
	 * descriptors, a failure will occur when the handler is invoked. Similarly, if a
	 * field is documented, is not marked as optional, and is not present in the request,
	 * a failure will also occur. For payloads with a hierarchical structure, documenting
	 * a field is sufficient for all of its descendants to also be treated as having been
	 * documented.
	 * 
	 * @param attributes Attributes made available during rendering of the snippet
	 * @param descriptors The descriptions of the request's fields
	 * @return the handler
	 * @see #fieldWithPath(String)
	 */
	public static Snippet requestFields(Map<String, Object> attributes,
			FieldDescriptor... descriptors) {
		return new RequestFieldsSnippet(attributes, Arrays.asList(descriptors));
	}

	/**
	 * Returns a handler that will produce a snippet documenting the fields of the API
	 * call's response.
	 * <p>
	 * If a field is present in the response, but is not documented by one of the
	 * descriptors, a failure will occur when the handler is invoked. Similarly, if a
	 * field is documented, is not marked as optional, and is not present in the response,
	 * a failure will also occur. For payloads with a hierarchical structure, documenting
	 * a field is sufficient for all of its descendants to also be treated as having been
	 * documented.
	 * 
	 * @param descriptors The descriptions of the response's fields
	 * @return the handler
	 * @see #fieldWithPath(String)
	 */
	public static Snippet responseFields(FieldDescriptor... descriptors) {
		return new ResponseFieldsSnippet(Arrays.asList(descriptors));
	}

	/**
	 * Returns a handler that will produce a snippet documenting the fields of the API
	 * call's response. The given {@code attributes} will be available during snippet
	 * generation.
	 * <p>
	 * If a field is present in the response, but is not documented by one of the
	 * descriptors, a failure will occur when the handler is invoked. Similarly, if a
	 * field is documented, is not marked as optional, and is not present in the response,
	 * a failure will also occur. For payloads with a hierarchical structure, documenting
	 * a field is sufficient for all of its descendants to also be treated as having been
	 * documented.
	 * 
	 * @param attributes Attributes made available during rendering of the snippet
	 * @param descriptors The descriptions of the response's fields
	 * @return the handler
	 * @see #fieldWithPath(String)
	 */
	public static Snippet responseFields(Map<String, Object> attributes,
			FieldDescriptor... descriptors) {
		return new ResponseFieldsSnippet(attributes, Arrays.asList(descriptors));
	}

}