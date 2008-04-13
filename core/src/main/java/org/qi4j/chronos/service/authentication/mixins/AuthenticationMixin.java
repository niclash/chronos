/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.service.authentication.mixins;

import org.qi4j.chronos.service.authentication.Authentication;
import org.qi4j.chronos.service.authentication.AuthenticationConfiguration;
import org.qi4j.composite.scope.This;
import org.qi4j.service.Activatable;

public class AuthenticationMixin implements Authentication, Activatable
{
    @This AuthenticationConfiguration config;

    private String username;

    private String password;

    public boolean authenticate( String username, String password )
    {
        return username.equals( this.username ) && password.equals( this.password );
    }

    public void activate() throws Exception
    {
        config.username().set( "robert" );
        config.password().set( "robert" );
        username = config.username().get();
        password = config.password().get();
    }

    public void passivate() throws Exception
    {
        username = null;
        password = null;
    }
}
