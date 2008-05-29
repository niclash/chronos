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
package org.qi4j.chronos.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Address;
import org.qi4j.chronos.model.City;
import org.qi4j.chronos.model.Country;
import org.qi4j.chronos.model.State;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.AddressEntityComposite;
import org.qi4j.chronos.model.composites.CityEntityComposite;
import org.qi4j.chronos.model.composites.CountryEntityComposite;
import org.qi4j.chronos.model.composites.StateEntityComposite;
import org.qi4j.chronos.service.AccountService;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.query.QueryBuilderFactory;

public abstract class AccountServiceMixin extends AbstractServiceMixin
    implements AccountService
{
    private static final long serialVersionUID = 1L;

    public List<Account> findAllAccounts()
    {
        UnitOfWork unitOfWork = getUnitOfWork();

        QueryBuilderFactory queryBuilderFactory = unitOfWork.queryBuilderFactory();

        List<Account> accounts = new ArrayList<Account>();

        for( Account account : queryBuilderFactory.newQueryBuilder( Account.class ).newQuery() )
        {
            accounts.add( account );
        }

        return accounts;
    }

    public Account newAccount()
    {
        UnitOfWork unitOfWork = getUnitOfWork();

        Account account = unitOfWork.newEntityBuilder( AccountEntityComposite.class ).newInstance();
        Address address = unitOfWork.newEntityBuilder( AddressEntityComposite.class ).newInstance();
        City city = unitOfWork.newEntityBuilder( CityEntityComposite.class ).newInstance();
        State state = unitOfWork.newEntityBuilder( StateEntityComposite.class ).newInstance();
        Country country = unitOfWork.newEntityBuilder( CountryEntityComposite.class ).newInstance();

        city.state().set( state );
        city.country().set( country );
        address.city().set( city );

        account.address().set( address );
        account.isEnabled().set( true );

        return account;
    }
}
