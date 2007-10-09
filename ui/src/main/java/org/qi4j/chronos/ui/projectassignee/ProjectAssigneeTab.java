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
package org.qi4j.chronos.ui.projectassignee;

import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;

public abstract class ProjectAssigneeTab extends NewLinkTab
{
    public ProjectAssigneeTab( String title )
    {
        super( title );
    }

    public NewLinkPanel getNewLinkPanel( String panelId )
    {
        return new ProjectAssigneeNewLinkPanel( panelId );
    }

    private class ProjectAssigneeNewLinkPanel extends NewLinkPanel
    {

        public ProjectAssigneeNewLinkPanel( String id )
        {
            super( id );
        }

        public Panel getContent( String id )
        {
            return new ProjectAssigneeTable( id )
            {
                public ProjectEntityComposite getProject()
                {
                    return ProjectAssigneeTab.this.getProject();
                }
            };
        }

        public void newLinkOnClick()
        {
            ProjectAssigneeAddPage addPage = new ProjectAssigneeAddPage( this.getPage() )
            {
                public ProjectEntityComposite getProject()
                {
                    return ProjectAssigneeTab.this.getProject();
                }
            };

            setResponsePage( addPage );
        }

        public String getNewLinkText()
        {
            return "New Project Assignee";
        }
    }

    public abstract ProjectEntityComposite getProject();
}
