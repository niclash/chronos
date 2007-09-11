/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
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
package org.qi4j.chronos.service.mocks;

import java.util.Iterator;
import java.util.List;
import org.qi4j.api.CompositeBuilderFactory;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.AccountService;

public class MockProjectServiceMixin extends MockAccountBasedServiceMixin
{
    public MockProjectServiceMixin( CompositeBuilderFactory factory, AccountService accountService )
    {
        super( factory, accountService );
    }

    public void addItem( AccountEntityComposite account, List<Identity> items )
    {
        Iterator<ProjectEntityComposite> projectIterator = account.projectIterator();

        while( projectIterator.hasNext() )
        {
            items.add( projectIterator.next() );
        }
    }

}
