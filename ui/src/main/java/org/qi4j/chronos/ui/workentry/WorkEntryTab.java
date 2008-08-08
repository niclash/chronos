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
package org.qi4j.chronos.ui.workentry;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;

public final class WorkEntryTab extends NewLinkTab
{
    private static final long serialVersionUID = 1L;

    private IModel<? extends HasWorkEntries> hasWorkEntries;
    private WorkEntryDataProvider dataProvider;
    private IModel<ProjectAssignee> projectAssignee;

    public WorkEntryTab( String title, IModel<? extends HasWorkEntries> hasWorkEntries, WorkEntryDataProvider dataProvider,
                         IModel<ProjectAssignee> projectAssignee )
    {
        super( title );

        this.hasWorkEntries = hasWorkEntries;
        this.dataProvider = dataProvider;
        this.projectAssignee = projectAssignee;
    }

    public NewLinkPanel getNewLinkPanel( String panelId )
    {
        return new WorkEntryNewLinkPanel( panelId );
    }

    private class WorkEntryNewLinkPanel extends NewLinkPanel
    {
        private static final long serialVersionUID = 1L;

        public WorkEntryNewLinkPanel( String id )
        {
            super( id );
        }

        public Panel newContent( String id )
        {
            return new WorkEntryTable( id, hasWorkEntries, dataProvider );
        }

        protected void authorizingLink( Link link )
        {
            if( projectAssignee == null )
            {
                link.setVisible( false );
            }
        }

        public void newLinkOnClick()
        {
            //TODO
//            WorkEntryAddPage addPage = new WorkEntryAddPage( this.getPage() )
//            {
//                public ProjectAssignee getProjectAssignee()
//                {
//                    return WorkEntryTab.this.getProjectAssignee();
//                }
//
//                public HasWorkEntries getHasWorkEntries()
//                {
//                    return WorkEntryTab.this.getHasWorkEntries();
//                }
//            };
//
//            setResponsePage( addPage );
        }

        public String newLinkText()
        {
            return "New Work Entry";
        }
    }
}
