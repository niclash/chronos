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
package org.qi4j.chronos.ui.project;

import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.common.BorderPanel;
import org.qi4j.chronos.ui.common.BorderPanelWrapper;
import org.qi4j.chronos.ui.common.tab.BaseTab;

public abstract class ProjectTab extends BaseTab
{
    public ProjectTab()
    {
        super( "Project" );
    }

    public BorderPanel getBorderPanel( String panelId )
    {
        BorderPanelWrapper wrapper = new BorderPanelWrapper( panelId )
        {
            public Panel getWrappedPanel( String panelId )
            {
                ProjectTable projectTable = new ProjectTable( panelId )
                {
                    public int getSize()
                    {
                        return ProjectTab.this.getSize();
                    }

                    public List<ProjectEntityComposite> dataList( int first, int count )
                    {
                        return ProjectTab.this.dataList( first, count );
                    }
                };

                return projectTable;
            }
        };

        return wrapper;
    }

    public abstract int getSize();

    public abstract List<ProjectEntityComposite> dataList( int first, int count );
}