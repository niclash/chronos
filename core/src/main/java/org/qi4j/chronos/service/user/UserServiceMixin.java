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
package org.qi4j.chronos.service.user;

import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.AdminEntityComposite;
import org.qi4j.service.Activatable;
import org.qi4j.composite.scope.This;
import org.qi4j.composite.scope.Structure;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.EntityCompositeNotFoundException;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.entity.association.ManyAssociation;
import org.qi4j.entity.association.SetAssociation;

import static org.qi4j.composite.NullArgumentException.validateNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 13, 2008
 * Time: 12:32:14 AM
 */
public class UserServiceMixin implements UserService, Activatable
{
    private @This UserServiceConfiguration config;

    private @Structure UnitOfWorkFactory factory;

    public User get( UnitOfWork unitOfWork, String userId )
    {
        validateNotNull( "unitOfWork", unitOfWork );
        validateNotNull( "userId", userId );

        User user = null;
        Class[] classes = new Class[]{ StaffEntityComposite.class, ContactPersonEntityComposite.class, AdminEntityComposite.class };
        for( Class<? extends User> clazz : classes )
        {
            try
            {
                user = unitOfWork.find( userId, clazz );
                return user;
            }
            catch( EntityCompositeNotFoundException ecnfe )
            {
                // expected
                unitOfWork.refresh();
            }
        }
        return user;
    }

    public User getUser( Account account, String loginId )
    {
        validateNotNull( "account", account );
        validateNotNull( "loginId", loginId );

        User user =  getUser( loginId, account.staffs() );

        if( user == null )
        {
            SetAssociation<? extends User>[] contactPersons = new SetAssociation[account.customers().size()];
            int i = 0;
            for( Customer customer : account.customers() )
            {
                contactPersons[ i++ ] = customer.contactPersons();
            }
            user = getUser( loginId, contactPersons );
        }
        return user;
    }

    private User getUser( String loginId, ManyAssociation<? extends User>...manyAssociations )
    {
        validateNotNull( "loginId", loginId );
        validateNotNull( "manyAssociations", manyAssociations );

        for( ManyAssociation<? extends User> associations : manyAssociations )
        {
            for( User user : associations )
            {
                Login login = user.login().get();
                if( login.name().get().equals( loginId ) )
                {
                    return user;
                }

            }
        }
        return null;
    }

    public Admin getAdmin( String loginId, String password )
    {
        validateNotNull( "loginId", loginId );
        validateNotNull( "password", password );

        for( Admin admin : config.admins() )
        {
            Login login = admin.login().get();

            if( login.name().get().equals( loginId ) && login.password().get().equals( password ))
            {
                return admin;
            }
        }
        return null;
    }

    public boolean hasThisSystemRole( User user, String systemRoleName )
    {
        validateNotNull( "user", user );
        validateNotNull( "systemRoleName", systemRoleName );

        return hasThisSystemRole( user.systemRoles(), systemRoleName );
    }

    public void addAdmin( Admin admin )
    {
        validateNotNull( "admin", admin );

        config.admins().add( admin );
    }

    private boolean hasThisSystemRole( ManyAssociation<SystemRole> systemRoles, String systemRolesName )
    {
        validateNotNull( "systemRoles", systemRoles );
        validateNotNull( "systemRolesName", systemRolesName );
        
        for( SystemRole systemRole : systemRoles )
        {
            if( systemRole.name().get().equals( systemRolesName ) || hasThisSystemRole( systemRole.systemRoles(), systemRolesName ) )
            {
                return true;
            }
        }
        return false;
    }

    public boolean isUnique( Account account, String loginId )
    {
        validateNotNull( "account", account );
        validateNotNull( "loginId", loginId );
        
        return getUser( account, loginId ) == null;
    }

    public void activate() throws Exception
    {
        validateNotNull( "config", config );
        validateNotNull( "factory", factory );
    }

    public void passivate() throws Exception
    {
    }
}
