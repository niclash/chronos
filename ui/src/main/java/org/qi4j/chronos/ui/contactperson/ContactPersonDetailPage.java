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
import org.apache.wicket.Page;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.BoundCompoundPropertyModel;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.contact.ContactTab;
import org.qi4j.chronos.ui.project.ProjectTab;
import org.qi4j.chronos.ui.user.UserDetailPanel;
import org.qi4j.chronos.ui.common.model.CustomCompositeModel;
import org.qi4j.entity.Identity;
import org.qi4j.composite.scope.Uses;

public class ContactPersonDetailPage extends LeftMenuNavPage
{
    private Page basePage;

    public ContactPersonDetailPage( @Uses Page basePage, final @Uses String contactPersonId )
    {
        this.basePage = basePage;

        setModel(
            new BoundCompoundPropertyModel(
                new LoadableDetachableModel()
                {
                    public Object load()
                    {
                        return getUnitOfWork().find( contactPersonId, ContactPersonEntityComposite.class );
                    }
                }
            )
        );

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new ContactPersonDetailForm( "contactPersonDetailForm", getModel() ) );
    }

    private class ContactPersonDetailForm extends Form
    {
        private UserDetailPanel userDetailPanel;

        private TextField relationshipField;

        private Button submitButton;

        private TabbedPanel tabbedPanel;

        public ContactPersonDetailForm( String id, final IModel iModel )
        {
            super( id );

            initComponents( iModel );
        }

        private void initComponents(final IModel iModel )
        {
            IModel relationshipModel = new CustomCompositeModel( iModel, "relationship" );
            relationshipField =
                new TextField( "relationshipField", new CustomCompositeModel( relationshipModel, "relationship" ) );

            userDetailPanel = new UserDetailPanel( "userDetailPanel" )
            {
                public User getUser()
                {
                    return (ContactPerson) iModel.getObject();
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

            tabs.add(
                new ContactTab()
                {
                    public ContactPerson getContactPerson()
                    {
                        return (ContactPerson) iModel.getObject();
                    }
                }
            );

            tabs.add(
                new ProjectTab( "Project" )
                {
                    public int getSize()
                    {
                        return getAccount().projects().size();
                    }

                    public List<IModel> dataList( int first, int count )
                    {
                        List<IModel> models = new ArrayList<IModel>();
                        for( final String projectId : ContactPersonDetailPage.this.dataList( first, count ) )
                        {
                            models.add(
                                new CompoundPropertyModel(
                                    new LoadableDetachableModel()
                                    {
                                        public Object load()
                                        {
                                            return getUnitOfWork().find( projectId, ProjectEntityComposite.class );
                                        }
                                    }
                                )
                            );
                        }
                        return models;
                    }
                }
            );

            tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            add( tabbedPanel );
            add( relationshipField );
            add( userDetailPanel );
            add( submitButton );
        }
    }

    protected List<String> dataList( int first, int count )
    {
        List<String> projects = new ArrayList<String>();
        for( Project project : getAccount().projects() )
        {
            projects.add( ( (Identity) project).identity().get() );
        }

        return projects.subList( first, first + count );
    }
}

