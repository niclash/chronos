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
package org.qi4j.chronos.test;

import org.junit.Test;
import org.qi4j.entity.Identity;
import org.qi4j.chronos.model.Name;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.composites.AccountEntityComposite;

import static org.junit.Assert.assertEquals;

public class SimpleMethodTest extends AbstractCommonTest
{
    @Test public void simpleTest() throws Exception
    {
        Account account = newAccount( "test", "test" );
        account = set( (AccountEntityComposite) account, "another test" );

        assertEquals( "Account name is not equals to \"another test\"!!!!", "another test", account.name().get() );

        TestDelegator delegator = new TestDelegator( (AccountEntityComposite) account );

        assertEquals( "Delegator Name is not equals to \"another test\"!!!!", "another test", delegator.getName() );
        assertEquals( "Delegator Id is not equals!!!!", ( (Identity) account ).identity().get(), delegator.getId() );
    }

    public <T extends Identity & Name> T set( T t, String name )
    {
        t.name().set( name );

        return t;
    }

    private static final class TestDelegator
    {
        private String id;
        private String name;

        public <T extends Identity & Name> TestDelegator( T t )
        {
            this.id = t.identity().get();
            this.name = t.name().get();
        }

        public String getId()
        {
            return id;
        }

        public String getName()
        {
            return name;
        }
    }
}
