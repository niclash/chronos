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
package org.qi4j.chronos.ui.project;

import java.util.Collections;
import java.util.Iterator;
import org.apache.wicket.Page;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.TimeRangeEntityComposite;
import org.qi4j.composite.scope.Uses;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectAddPage extends ProjectAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( ProjectAddPage.class );
    private static final String ADD_FAIL = "addFailed";
    private static final String ADD_SUCCESS = "addSuccessful";
    private static final String SUBMIT_BUTTON = "addPageSubmitButton";
    private static final String TITLE_LABEL = "addPageTitleLabel";

    public ProjectAddPage( final @Uses Page basePage )
    {
        super( basePage );

        bindModel();
    }

    private void bindModel()
    {
        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    protected Object load()
                    {
                        final UnitOfWork unitOfWork = getUnitOfWork();
                        final Project project =
                            unitOfWork.newEntityBuilder( ProjectEntityComposite.class ).newInstance();
                        final TimeRange actualTime =
                            unitOfWork.newEntityBuilder( TimeRangeEntityComposite.class ).newInstance();
                        final TimeRange estimateTime =
                            unitOfWork.newEntityBuilder( TimeRangeEntityComposite.class ).newInstance();
                        final Customer customer = unitOfWork.dereference( availableCustomers.get( 0 ) );
                        final ContactPerson primaryContactPerson =
                            unitOfWork.dereference( customer.contactPersons().iterator().next() );
                        final PriceRateSchedule priceRateSchedule =
                            unitOfWork.dereference( customer.priceRateSchedules().iterator().next() );
                        project.actualTime().set( actualTime );
                        project.estimateTime().set( estimateTime );
                        project.customer().set( customer );
                        project.primaryContactPerson().set( primaryContactPerson );
                        project.priceRateSchedule().set( priceRateSchedule );
                        project.projectStatus().set( ProjectStatusEnum.ACTIVE );

                        return project;
                    }
                }
            )
        );
        bindPropertyModel( getModel() );
    }

    public void onSubmitting()
    {
        final UnitOfWork unitOfWork = getUnitOfWork();
        try
        {
            final Project project = (Project) getModelObject();
            final Account account = unitOfWork.dereference( getAccount() );
            for( ContactPerson contactPerson : getSelectedContactPersonList() )
            {
                project.contactPersons().add( contactPerson );
            }
            account.projects().add( project );
            unitOfWork.complete();

            logInfoMsg( getString( ADD_SUCCESS ) );
            divertToGoBackPage();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            unitOfWork.reset();

            logErrorMsg( getString( ADD_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
        catch( Exception err )
        {
            unitOfWork.reset();

            logErrorMsg( getString( ADD_FAIL, new Model( err ) ) );
            LOGGER.error( err.getLocalizedMessage(), err );
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

    public Iterator<ContactPerson> getInitSelectedContactPersonList()
    {
        return Collections.EMPTY_LIST.iterator();
    }
}
