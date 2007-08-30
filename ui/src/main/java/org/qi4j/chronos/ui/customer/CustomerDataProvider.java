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
package org.qi4j.chronos.ui.customer;

import java.util.List;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.service.CustomerService;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public class CustomerDataProvider extends AbstractSortableDataProvider<CustomerEntityComposite>
{
    public String getId( CustomerEntityComposite customerEntityComposite )
    {
        return customerEntityComposite.getIdentity();
    }

    public CustomerEntityComposite load( String id )
    {
        return getCustomerService().get( id );
    }

    public CustomerService getCustomerService()
    {
        return ChronosWebApp.getServices().getCustomerService();
    }

    public List<CustomerEntityComposite> dataList( int first, int count )
    {
        return getCustomerService().find( new FindFilter( first, count ) );
    }

    public int size()
    {
        return getCustomerService().countAll();
    }
}
