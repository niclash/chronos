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
package org.qi4j.chronos.ui.contactperson;

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasContactPersons;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DefaultAction;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ContactPersonTable extends ActionTable<ContactPerson>
{
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger( ContactPersonTable.class );
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_FAIL = "deleteFailed";
    private static final String ENABLE_ACTION = "enableAction";
    private static final String ENABLE_SUCCESS = "enableSuccessful";
    private static final String ENABLE_FAIL = "enableFailed";
    private static final String DISABLE_ACTION = "disableAction";
    private static final String DISABLE_SUCCESS = "disableSuccessful";
    private static final String DISABLE_FAIL = "disableFailed";

    private final static String[] COLUMN_NAMES = { "First Name", "Last name", "Login Id", "Login Enabled", "Edit" };

    public ContactPersonTable( String id, IModel<? extends HasContactPersons> hasContactPerson, ContactPersonDataProvider dataProvider )
    {
        super( id, hasContactPerson, dataProvider, COLUMN_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction(
            new DeleteAction<ContactPerson>( getString( DELETE_ACTION ) )
            {
                private static final long serialVersionUID = 1L;

                public void performAction( List<ContactPerson> contactPersons )
                {
                    handleDeleteAction( contactPersons );
                    info( getString( DELETE_SUCCESS ) );
                }
            }
        );
        addAction(
            new DefaultAction<ContactPerson>( getString( DISABLE_ACTION ) )
            {
                private static final long serialVersionUID = 1L;

                public void performAction( List<ContactPerson> contactPersons )
                {
                    handleEnableAction( contactPersons, false );
                    info( getString( DISABLE_SUCCESS ) );
                }
            }
        );
        addAction(
            new DefaultAction<ContactPerson>( getString( ENABLE_ACTION ) )
            {
                private static final long serialVersionUID = 1L;

                public void performAction( List<ContactPerson> contactPersons )
                {
                    handleEnableAction( contactPersons, true );
                    info( getString( ENABLE_SUCCESS ) );
                }
            }
        );
    }

    private void handleEnableAction( List<ContactPerson> contactPersons, boolean enable )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {
            for( ContactPerson contactPerson : contactPersons )
            {
                contactPerson.login().get().isEnabled().set( enable );
            }

            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( enable ? ENABLE_FAIL : DISABLE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    // might not be a good idea to have delete action
    private void handleDeleteAction( List<ContactPerson> contactPersons )
    {
        final UnitOfWork unitOfWork = ChronosUnitOfWorkManager.get().getCurrentUnitOfWork();
        try
        {

            final HasContactPersons hasContactPersons = (HasContactPersons) getDefaultModelObject();

            for( ContactPerson contactPerson : contactPersons )
            {
                hasContactPersons.contactPersons().remove( contactPerson );
/*
                for( Project project : getAccount().projects() )
                {
                    project.contactPersons().remove( contactPerson );
                    if( project.primaryContactPerson().get().equals( contactPerson ) )
                    {
                        project.primaryContactPerson().set( null );
                    }
                }
*/
                unitOfWork.remove( contactPerson );
            }
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL, new Model( uowce ) ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }

    protected void authorizingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    public void populateItems( Item<ContactPerson> item )
    {
        final ContactPerson contactPerson = item.getModelObject();

        final String contactPersonId = contactPerson.identity().get();

        item.add( createDetailLink( "firstName", contactPerson.firstName().get(), contactPersonId ) );
        item.add( createDetailLink( "lastName", contactPerson.lastName().get(), contactPersonId ) );
        item.add( new Label( "loginId", contactPerson.login().get().name().get() ) );

        CheckBox loginEnabled =
            new CheckBox( "loginEnabled", new Model( contactPerson.login().get().isEnabled().get() ) );
        loginEnabled.setEnabled( false );
        item.add( loginEnabled );

        SimpleLink editLink = createEditLink( contactPerson );
        item.add( editLink );
    }

    private SimpleLink createEditLink( ContactPerson contactPerson )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
/*
                setResponsePage(
                    new ContactPersonEditPage( this.getPage(), iModel )
                    {
                        public Customer getCustomer()
                        {
                            return ContactPersonTable.this.getCustomer();
                        }

                        public ContactPerson getContactPerson()
                        {
                            return (ContactPerson) iModel.getObject();
                        }
                    }
                );
*/
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }

    private SimpleLink createDetailLink( String id, String text, final String contactPersonId )
    {
        return new SimpleLink( id, text )
        {
            public void linkClicked()
            {
/*
                ContactPersonDetailPage detailPage = new ContactPersonDetailPage( this.getPage(), contactPersonId );

                setResponsePage( detailPage );
*/
            }
        };
    }

}
