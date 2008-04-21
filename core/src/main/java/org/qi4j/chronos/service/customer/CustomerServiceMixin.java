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
package org.qi4j.chronos.service.customer;

import org.qi4j.service.Activatable;
import org.qi4j.chronos.model.Customer;
import org.qi4j.composite.scope.Structure;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 20, 2008
 * Time: 10:52:13 AM
 */
public class CustomerServiceMixin implements CustomerService, Activatable
{
    public Customer get( String customerId )
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void activate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void passivate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
