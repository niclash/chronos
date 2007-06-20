/*  Copyright 2007 Niclas Hedhman.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.model;

public interface ContactType
{
    // We can't really have enum here because there will be too many contact types and
    // we should be able to add more contact types without changing the code
//    phone_office, phone_home, mobile_office, mobile_private, icq, jabber, msn, yahoo, email
    void setContactType(String type);

    String getContactType();
}
