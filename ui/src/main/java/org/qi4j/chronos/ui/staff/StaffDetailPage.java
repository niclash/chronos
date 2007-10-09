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
package org.qi4j.chronos.ui.staff;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.ProjectService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.user.UserDetailPanel;

public abstract class StaffDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public StaffDetailPage( Page returnPage )
    {
        this.returnPage = returnPage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new StaffDetailForm( "staffDetailForm" ) );
    }

    private ProjectService getProjectService()
    {
        return ChronosWebApp.getServices().getProjectService();
    }

    private class StaffDetailForm extends Form
    {
        private Button submitButton;

        private TabbedPanel tabbedPanel;

        private UserDetailPanel userDetailPanel;

        public StaffDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            userDetailPanel = new UserDetailPanel( "userDetailPanel" )
            {
                public User getUser()
                {
                    return StaffDetailPage.this.getStaff();
                }
            };

            List<AbstractTab> tabs = new ArrayList<AbstractTab>();

            tabs.add( new ProjectTab( "Project" )
            {
                public int getSize()
                {
                    return getProjectService().countAll( StaffDetailPage.this.getStaff() );
                }

                public List<ProjectEntityComposite> dataList( int first, int count )
                {
                    return getProjectService().findAll( StaffDetailPage.this.getStaff(), new FindFilter( first, count ) );
                }
            } );

            tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( returnPage );
                }
            };

            add( userDetailPanel );
            add( tabbedPanel );
            add( submitButton );
        }
    }

    public abstract StaffEntityComposite getStaff();
}
