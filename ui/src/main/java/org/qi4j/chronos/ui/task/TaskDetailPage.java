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
package org.qi4j.chronos.ui.task;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.qi4j.chronos.model.composites.TaskEntityComposite;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.base.LeftMenuNavPage;
import org.qi4j.chronos.ui.common.SimpleTextArea;
import org.qi4j.chronos.ui.common.SimpleTextField;
import org.qi4j.chronos.util.DateUtil;

public abstract class TaskDetailPage extends LeftMenuNavPage
{
    private BasePage basePage;

    public TaskDetailPage( BasePage basePage )
    {
        this.basePage = basePage;

        initComponents();
    }

    private void initComponents()
    {
        add( new FeedbackPanel( "feedbackPanel" ) );
        add( new TaskMasterDetailForm( "taskDetailForm" ) );
    }

    private class TaskMasterDetailForm extends Form
    {
        private Button submitButton;

        private SimpleTextField titleField;
        private SimpleTextField createDateField;
        private SimpleTextArea descriptionTextArea;

        private TabbedPanel tabbedPanel;

        public TaskMasterDetailForm( String id )
        {
            super( id );

            initComponents();
        }

        private void initComponents()
        {
            TaskEntityComposite taskMaster = getTaskMaster();

            titleField = new SimpleTextField( "titleField", taskMaster.getTitle() );
            createDateField = new SimpleTextField( "createDateField", DateUtil.formatDateTime( taskMaster.getCreatedDate() ) );
            descriptionTextArea = new SimpleTextArea( "descriptionTextArea", taskMaster.getDescription() );

            submitButton = new Button( "submitButton", new Model( "Return" ) )
            {
                public void onSubmit()
                {
                    setResponsePage( basePage );
                }
            };

            List<AbstractTab> tabs = new ArrayList<AbstractTab>();

            tabbedPanel = new TabbedPanel( "tabbedPanel", tabs );

            add( titleField );
            add( createDateField );
            add( descriptionTextArea );
            add( submitButton );
            add( tabbedPanel );
        }
    }

    public abstract TaskEntityComposite getTaskMaster();
}
