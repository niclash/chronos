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
package org.qi4j.chronos.ui.projectowner;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmittingComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.ProjectOwner;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.contactperson.ContactPersonTab;
import org.qi4j.chronos.ui.customer.CustomerDetailPanel;

public class ProjectOwnerDetailPage extends LeftMenuNavPage
{
    private String projectOwnerId;

    private BasePage returnPage;

    public ProjectOwnerDetailPage( BasePage returnPage, String projectOwnerId )
    {
        this.projectOwnerId = projectOwnerId;
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ProjectOwnerDetailForm( "projectOwnerDetailForm" ) );
    }

    private class ProjectOwnerDetailForm extends Form
    {
        private Button submitButton;

        private CustomerDetailPanel customerDetailPanel;

        private AjaxTabbedPanel ajaxTabbedPanel;

        public ProjectOwnerDetailForm( String id )
        {
            super( id );

            ProjectOwner projectOwner = ChronosWebApp.getServices().getProjectOwnerService().get( projectOwnerId );

            customerDetailPanel = new CustomerDetailPanel( "customerDetailPanel", projectOwner );
            add( customerDetailPanel );

            List<AbstractTab> abstractTabs = new ArrayList<AbstractTab>();

            abstractTabs.add( new ContactPersonTab()
            {
                public String getProjectOwnerId()
                {
                    return projectOwnerId;
                }
            } );

            abstractTabs.add( new AbstractTab( new Model( "Price Rate Schedules" ) )
            {
                public Panel getPanel( String panelId )
                {
                    return new Panel( panelId );
                }
            } );

            ajaxTabbedPanel = new AjaxTabbedPanel( "ajaxTabbedPanel", abstractTabs );

            add( ajaxTabbedPanel );

            submitButton = new Button( "submitButton", new Model( "Return" ) );
            add( submitButton );
        }

        protected void delegateSubmit( IFormSubmittingComponent submittingButton )
        {
            if( submittingButton == submitButton )
            {
                setResponsePage( returnPage );
            }
            else
            {
                throw new IllegalArgumentException( submittingButton + " not handled yet!" );
            }
        }
    }
}
