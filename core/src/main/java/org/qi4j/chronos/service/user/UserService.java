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
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.Admin;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.entity.UnitOfWork;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 13, 2008
 * Time: 12:41:22 AM
 */
public interface UserService
{
    User get( UnitOfWork unitOfWork, String userId );

    User getUser( String accountId, String loginId );

    User getUser( Account account, String loginId );

    Admin getAdmin( String loginId, String password );

    boolean hasThisSystemRole( User user, String systemRoleName );

    boolean isUnique( Account account, String loginId );

    void addAdmin( Admin admin );
}
