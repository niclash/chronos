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
import org.qi4j.chronos.service.AccountService;
import org.qi4j.query.QueryBuilderFactory;
import org.qi4j.entity.UnitOfWork;

public abstract class AccountServiceMixin extends AbstractServiceMixin
    implements AccountService
{
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

}
