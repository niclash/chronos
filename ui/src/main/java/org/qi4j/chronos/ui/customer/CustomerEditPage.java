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

import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.qi4j.composite.scope.Uses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerEditPage extends CustomerAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( CustomerEditPage.class );
    private static final String UPDATE_FAIL = "updateFailed";
    private static final String UPDATE_SUCCESS = "updateSuccessful";
    private static final String SUBMIT_BUTTON = "editPageSubmitButton";
    private static final String TITLE_LABEL = "editPageTitleLabel";

    public CustomerEditPage( @Uses Page basePage, final @Uses String customerId )
    {
        super( basePage );

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( customerId, CustomerEntityComposite.class );
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
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
            info( getString( UPDATE_SUCCESS ) );

            divertToGoBackPage();
        }
        catch( Exception err)
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();
            
            error( getString( UPDATE_FAIL, new Model( err ) ) );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return getString( SUBMIT_BUTTON );
    }

    public String getTitleLabel()
    {
        return getString( TITLE_LABEL );
    }
}
