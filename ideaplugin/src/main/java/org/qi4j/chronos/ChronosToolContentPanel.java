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
package org.qi4j.chronos;

import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import org.qi4j.chronos.common.AbstractPanel;
import org.qi4j.chronos.workentry.WorkEntryTablePanel;

public class ChronosToolContentPanel extends AbstractPanel
{
    private WorkEntryTablePanel workEntryTablePanel;

    private Project project;

    public ChronosToolContentPanel( Project project )
    {
        this.project = project;

        init();
    }

    public void resetData()
    {
        workEntryTablePanel.resetData();
    }

    protected String getLayoutColSpec()
    {
        return "1dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p,3dlu,1dlu:grow";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.setDefaultDialogBorder();

        builder.addLabel( "Recent WorkEntries", cc.xy( 1, 1 ) );
        builder.add( workEntryTablePanel, cc.xy( 1, 3, "fill,fill" ) );
    }

    protected void initComponents()
    {
        workEntryTablePanel = new WorkEntryTablePanel( project );
    }
}
