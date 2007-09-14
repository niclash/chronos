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

import java.util.List;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.ui.base.BasePage;
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
                public ProjectAssigneeEntityComposite getProjectAssignee()
                {
                    return WorkEntryTab.this.getProjectAssignee();
                }

                public List<WorkEntryEntityComposite> dataList( int first, int count )
                {
                    return WorkEntryTab.this.dataList( first, count );
                }

                public int getSize()
                {
                    return WorkEntryTab.this.getSize();
                }
            };
        }

        public Link getNewLink( String id )
        {
            return new Link( id )
            {
                public void onClick()
                {
                    WorkEntryAddPage addPage = new WorkEntryAddPage( (BasePage) this.getPage() )
                    {
                        public ProjectAssigneeEntityComposite getProjectAssignee()
                        {
                            return WorkEntryTab.this.getProjectAssignee();
                        }
                    };

                    setResponsePage( addPage );
                }
            };
        }

        public String getNewLinkText()
        {
            return "New Work Entry";
        }
    }

    public abstract ProjectAssigneeEntityComposite getProjectAssignee();

    public abstract List<WorkEntryEntityComposite> dataList( int first, int count );

    public abstract int getSize();
}
