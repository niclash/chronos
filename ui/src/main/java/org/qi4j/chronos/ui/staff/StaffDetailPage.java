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
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.user.UserDetailPanel;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;

public class StaffDetailPage extends LeftMenuNavPage
{
    private static final long serialVersionUID = 1L;

    private Page returnPage;

    private IModel<? extends HasProjects> hasProjects;

    public StaffDetailPage( Page returnPage, final IModel<Staff> staffModel, final IModel<? extends HasProjects> hasProjects )
    {
        this.returnPage = returnPage;
        this.hasProjects = hasProjects;

        ChronosCompoundPropertyModel<IModel<Staff>> model = new ChronosCompoundPropertyModel<IModel<Staff>>( staffModel );

        setDefaultModel( model );

        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new StaffDetailForm( "staffDetailForm", model ) );
    }

    private class StaffDetailForm extends Form
    {
        private static final long serialVersionUID = 1L;

        public StaffDetailForm( String id, final ChronosCompoundPropertyModel<IModel<Staff>> model )
        {
            super( id );

            UserDetailPanel userDetailPanel = new UserDetailPanel( "userDetailPanel", model.getObject() );

            final List<ITab> tabs = new ArrayList<ITab>();
            tabs.add( new ProjectTab( "Project", hasProjects ) );

            final TabbedPanel tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            final Button submitButton =
                new Button( "submitButton", new Model<String>( "Return" ) )
                {
                    private static final long serialVersionUID = 1L;

                    public void onSubmit()
                    {
//                        reset();

                        setResponsePage( returnPage );
                    }
                };

            add( userDetailPanel );
            add( tabbedPanel );
            add( submitButton );
        }

    }
}
