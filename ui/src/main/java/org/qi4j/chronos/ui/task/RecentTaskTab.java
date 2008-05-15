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

import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.ui.common.BorderPanel;
import org.qi4j.chronos.ui.common.BorderPanelWrapper;
import org.qi4j.chronos.ui.common.tab.BaseTab;

public abstract class RecentTaskTab extends BaseTab
{
    public RecentTaskTab( String title )
    {
        super( title );
    }

    public BorderPanel getBorderPanel( String panelId )
    {
        BorderPanelWrapper wrapper = new BorderPanelWrapper( panelId )
        {
            public Panel getWrappedPanel( String panelId )
            {
                TaskTable taskTable = new TaskTable( panelId )
                {
                    public int getSize()
                    {
                        return RecentTaskTab.this.getSize();
                    }

                    public List<String> dataList( int first, int count )
                    {
                        return RecentTaskTab.this.dataList( first, count );
                    }
                };

                return taskTable;
            }
        };

        return wrapper;
    }

    public abstract int getSize();

    public abstract List<String> dataList( int first, int count );
}
