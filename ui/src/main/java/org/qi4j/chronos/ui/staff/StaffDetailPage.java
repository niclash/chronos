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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.associations.HasProjects;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.user.UserDetailPanel;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.ui.wicket.model.ChronosCompoundPropertyModel;
import org.qi4j.entity.Identity;

public class StaffDetailPage extends LeftMenuNavPage
{
    private Page returnPage;

    public StaffDetailPage( Page returnPage, final IModel<Staff> staffModel )
    {
        this.returnPage = returnPage;
/*

        setModel(
            new CompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    protected Object load()
                    {
                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( staffId, StaffEntityComposite.class );
                    }
                }
            )
        );
*/
        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new StaffDetailForm( "staffDetailForm", getModel() ) );
    }

    private class StaffDetailForm extends Form
    {
        public StaffDetailForm( String id, final IModel iModel )
        {
            super( id );

            initComponents( iModel );
        }

        private void initComponents( final IModel iModel )
        {
            UserDetailPanel userDetailPanel = new UserDetailPanel( "userDetailPanel", iModel );

            final List<AbstractTab> tabs = new ArrayList<AbstractTab>();
            tabs.add(
                new ProjectTab( "Project" )
                {
                    public IModel<HasProjects> getHasProjectsModel()
                    {
                        return new ChronosCompoundPropertyModel<HasProjects>( StaffDetailPage.this.getAccount() );
                    }

                    public int getSize()
                    {
                        return getAccount().projects().size();
                    }
                }
            );
            final TabbedPanel tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            final Button submitButton =
                new Button( "submitButton", new Model( "Return" ) )
                {
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

    protected List<IModel<Project>> dataList( int first, int count )
    {
        List<IModel<Project>> projects = new ArrayList<IModel<Project>>();
        for( Project project : getAccount().projects() )
        {
            projects.add( new ChronosCompoundPropertyModel<Project>( project ) );
        }

        return projects.subList( first, first + count );
    }
}
