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
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;

public abstract class WorkEntryTab extends NewLinkTab
{
    public WorkEntryTab( String title )
    {
        super( title );
    }

    public NewLinkPanel getNewLinkPanel( String panelId )
    {
        return new WorkEntryNewLinkPanel( panelId );
    }

    private class WorkEntryNewLinkPanel extends NewLinkPanel
    {
        public WorkEntryNewLinkPanel( String id )
        {
            super( id );
        }

        public Panel getContent( String id )
        {
            return new WorkEntryTable( id )
            {
                public HasWorkEntries getHasWorkEntries()
                {
                    return WorkEntryTab.this.getHasWorkEntries();
                }
            };
        }

        protected void authorizingLink( Link link )
        {
            if( getProjectAssignee() == null )
            {
                link.setVisible( false );
            }
        }

        public void newLinkOnClick()
        {
            WorkEntryAddPage addPage = new WorkEntryAddPage( this.getPage() )
            {
                public ProjectAssignee getProjectAssignee()
                {
                    return WorkEntryTab.this.getProjectAssignee();
                }

                public void addingWorkEntry( WorkEntry workentry )
                {
                    WorkEntryTab.this.addingWorkEntry( workentry );
                }
            };

            setResponsePage( addPage );
        }

        public String getNewLinkText()
        {
            return "New Work Entry";
        }
    }

    public abstract void addingWorkEntry( WorkEntry workEntry );

    public abstract ProjectAssignee getProjectAssignee();

    public abstract HasWorkEntries getHasWorkEntries();
}
