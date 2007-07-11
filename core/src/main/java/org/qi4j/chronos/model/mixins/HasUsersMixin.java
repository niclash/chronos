/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.model.mixins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasUsers;

/**
 * Default mixin implementation for {@link HasUsers}
 */
public final class HasUsersMixin implements HasUsers
{
    private final List<User> users;

    public HasUsersMixin()
    {
        users = new ArrayList<User>();
    }

    public void addUser( User user )
    {
        users.add( user );
    }

    public void removeUser( User user )
    {
        users.remove( user );
    }

    public Iterator<User> userIterator()
    {
        return users.iterator();
    }
}
