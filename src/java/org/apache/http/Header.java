/*
 * $HeadURL$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 *
 *  Copyright 1999-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.http;

import org.apache.http.util.LangUtils;

/**
 * <p>An HTTP header.</p>
 *
 * @author <a href="mailto:remm@apache.org">Remy Maucherat</a>
 * @author <a href="mailto:mbowler@GargoyleSoftware.com">Mike Bowler</a>
 * @author <a href="mailto:oleg@ural.ru">Oleg Kalnichevski</a>
 * @version $Revision$ $Date$
 */
public class Header {

    private final String name;
    private final String value;
    
    /**
     * Autogenerated header flag.
     */
    private final boolean isAutogenerated;
    
    /**
     * Constructor with name and value, and autogenerated flag
     *
     * @param name the header name
     * @param value the header value
     * @param isAutogenerated <tt>true</tt> if the header is autogenerated,
     */
    public Header(final String name, final String value, boolean isAutogenerated) {
        super();
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.name = name;
        this.value = value;
        this.isAutogenerated = isAutogenerated;
    }

    /**
     * Constructor with name and value
     *
     * @param name the header name
     * @param value the header value
     *  <tt>false</tt> otherwise.
     * 
     * @since 3.0
     */
    public Header(String name, String value) {
        this(name, value, false);
    }

    /**
     * Returns the header name.
     *
     * @return String name The name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the header value.
     *
     * @return String value The current value.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns the value of the auto-generated header flag.
     * 
     * @return <tt>true</tt> if the header is autogenerated,
     *  <tt>false</tt> otherwise.
     * 
     * @since 3.0
     */
    public boolean isAutogenerated() {
        return this.isAutogenerated;
    }

    /**
     * Returns a {@link String} representation of the header.
     *
     * @return a string
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.name);
        buffer.append(": ");
        buffer.append(this.value);
        return buffer.toString();
    }

    /**
     * Returns an array of {@link HeaderElement}s constructed from my value.
     *
     * @see HeaderElement#parseElements(String)
     * 
     * @return an array of header elements
     * 
     * @since 3.0
     */
    public HeaderElement[] getElements() {
        return HeaderElement.parseElements(getValue());
    }

    public boolean equals(final Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof Header) {
            Header that = (Header) object;
            return this.name.equals(that.name)
                  && LangUtils.equals(this.value, that.value);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = LangUtils.HASH_SEED;
        hash = LangUtils.hashCode(hash, this.name);
        hash = LangUtils.hashCode(hash, this.value);
        return hash;
    }
    
    public static Header parse(final String headerLine) throws ProtocolException {
        if (headerLine == null) {
            throw new IllegalArgumentException("Header line may not be null");
        }
        int colon = headerLine.indexOf(":");
        if (colon < 0) {
            throw new ProtocolException("Unable to parse header: " + headerLine);
        }
        String name = headerLine.substring(0, colon).trim();
        String value = headerLine.substring(colon + 1).trim();
        return new Header(name, value);
    }
    
}
