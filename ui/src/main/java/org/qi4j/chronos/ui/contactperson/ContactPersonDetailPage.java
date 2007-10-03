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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.ui.contact.ContactTab;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.user.UserDetailPanel;

public abstract class ContactPersonDetailPage extends LeftMenuNavPage
{
    private BasePage basePage;

    public ContactPersonDetailPage( BasePage basePage )
    {
        this.basePage = basePage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ContactPersonDetailForm( "contactPersonDetailForm" ) );
    }

    private class ContactPersonDetailForm extends Form
    {
        private UserDetailPanel userDetailPanel;

        private SimpleTextField relationshipField;

        private Button submitButton;

        private TabbedPanel tabbedPanel;

        public ContactPersonDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            ContactPersonEntityComposite contactPerson = getContactPerson();

            relationshipField = new SimpleTextField( "relationshipField", contactPerson.getRelationship().getRelationship() );

            userDetailPanel = new UserDetailPanel( "userDetailPanel" )
            {
                public User getUser()
                {
                    return ContactPersonDetailPage.this.getContactPerson();
                }
            };

            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( basePage );
                }
            };

            List<AbstractTab> tabs = new ArrayList<AbstractTab>();

            tabs.add( new ContactTab()
            {
                public ContactPersonEntityComposite getContactPerson()
                {
                    return ContactPersonDetailPage.this.getContactPerson();
                }
            } );

            tabs.add( new ProjectTab( "Project" )
            {
                public int getSize()
                {
                    return getProjectService().countAll( ContactPersonDetailPage.this.getContactPerson() );
                }

                public List<ProjectEntityComposite> dataList( int first, int count )
                {
                    return getProjectService().findAll( ContactPersonDetailPage.this.getContactPerson(), new FindFilter( first, count ) );
                }
            } );

            tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            add( tabbedPanel );
            add( relationshipField );
            add( userDetailPanel );
            add( submitButton );
        }
    }

    private ProjectService getProjectService()
    {
        return ChronosWebApp.getServices().getProjectService();
    }

    public abstract ContactPersonEntityComposite getContactPerson();
}

