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
package org.qi4j.chronos.ui;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import org.qi4j.chronos.ui.common.AbstractPanel;

public class ChronosToolMainPanel extends AbstractPanel
{
    private Project project;
    private ActionManager actionManager;

    private ChronosToolBar chronosToolBar;
    private ChronosToolContentPanel chronosToolCententPanel;

    public ChronosToolMainPanel( Project project, ActionManager actionManager )
    {
        this.project = project;
        this.actionManager = actionManager;

        init();
    }

    public String getLayoutColSpec()
    {
        return "1dlu:grow";
    }

    public String getLayoutRowSpec()
    {
        return "18dlu,p, 1dlu:grow";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( chronosToolBar, cc.xy( 1, 1, "fill, fill" ) );
        builder.addSeparator( "", cc.xy( 1, 2 ) );
        builder.add( chronosToolCententPanel, cc.xy( 1, 3, "fill, fill" ) );
    }

    protected void initComponents()
    {
        chronosToolBar = new ChronosToolBar( actionManager );
        chronosToolCententPanel = new ChronosToolContentPanel( project );
    }
}
