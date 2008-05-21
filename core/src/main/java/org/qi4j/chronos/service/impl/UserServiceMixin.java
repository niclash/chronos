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
import java.util.Set;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.associations.HasLogin;
import org.qi4j.chronos.service.UserAuthenticationFailException;
import org.qi4j.chronos.service.UserService;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.query.Query;
import org.qi4j.query.QueryBuilder;
import org.qi4j.query.QueryBuilderFactory;
import static org.qi4j.query.QueryExpressions.and;
import static org.qi4j.query.QueryExpressions.eq;
import static org.qi4j.query.QueryExpressions.templateFor;
import org.qi4j.query.grammar.Conjunction;

public abstract class UserServiceMixin extends AbstractServiceMixin implements UserService
{
    //TODO user encode password

    public User authenticate( final Account account, final String userName, final String password )
        throws UserAuthenticationFailException
    {

        UnitOfWork unitOfWork = getUnitOfWork();

        QueryBuilderFactory queryBuilderFactory = unitOfWork.queryBuilderFactory();

        //user attempting to login as super admin
        if( account == null )
        {
            QueryBuilder<Admin> adminQueryBuilder = queryBuilderFactory.newQueryBuilder( Admin.class );

            Admin adminTemplate = templateFor( Admin.class );

            Query<Admin> adinQuery = adminQueryBuilder.where( getHasLoginQueryExpression( adminTemplate, userName, password ) ).newQuery();

            Admin admin = adinQuery.find();

            if( admin != null )
            {
                return admin;
            }
        }
        else
        {
            //TODO Query support for Assocaition and Join not ready yet.
            //TODO see http://issues.ops4j.org/jira/browse/QI-67
            List<User> candidates = new ArrayList<User>();
            Set<Staff> staffs = account.staffs();
            candidates.addAll( staffs );

            Set<Customer> customers = account.customers();

            for( Customer customer : customers )
            {
                candidates.addAll( customer.contactPersons() );
            }

            for( User user : candidates )
            {
                if( user.login().get().name().get().equals( userName ) && user.login().get().password().get().equals( password ) )
                {
                    return user;
                }
            }
        }

        throw new UserAuthenticationFailException( "Invalid password or username" );
    }

    private Conjunction getHasLoginQueryExpression( HasLogin hasLogin, String userName, String password )
    {
        return and( eq( hasLogin.login().get().name(), userName ),
                    eq( hasLogin.login().get().password(), password ) );
    }
}
