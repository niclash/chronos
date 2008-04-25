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
package org.qi4j.chronos.ui.account;

import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountEditPage extends AccountAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( AccountEditPage.class );

    public AccountEditPage( @Uses Page goBackPage, final @Uses String accountId )
    {
        super( goBackPage );

        setModel( new CompoundPropertyModel(
            new LoadableDetachableModel()
            {
                public Object load()
                {
                    return getUnitOfWork().find( accountId, AccountEntityComposite.class );
                }
            }
            )
        );
        bindPropertyModel( getModel() );
    }

    public void onSubmitting()
    {
        try
        {
            getUnitOfWork().complete();
            logInfoMsg( "Account is updated successfully." );

            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            logErrorMsg( "Unable to update account!!!. " + uowce.getClass().getSimpleName() );
            LOGGER.error( uowce.getLocalizedMessage() );

            reset();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    @Override protected void divertToGoBackPage()
    {
        reset();
        
        super.divertToGoBackPage();
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Account";
    }
}
