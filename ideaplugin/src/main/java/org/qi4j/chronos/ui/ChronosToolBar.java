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

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JComponent;
import org.qi4j.chronos.action.ChronosActionConstant;
import org.qi4j.chronos.ui.common.AbstractPanel;

public class ChronosToolBar extends AbstractPanel
{
    private JComponent settingToolBar;

    private ActionManager actionManager;

    public ChronosToolBar( ActionManager actionManager )
    {
        this.actionManager = actionManager;

        init();
    }

    protected void initComponents()
    {
        ActionGroup chronosSettingGroup = (ActionGroup) actionManager.getAction( ChronosActionConstant.SETTING_GROUP );

        settingToolBar = actionManager.createActionToolbar( "settingToolBar", chronosSettingGroup, true ).getComponent();
    }

    protected String getLayoutColSpec()
    {
        return "p, 1dlu:grow, p";
    }

    protected String getLayoutRowSpec()
    {
        return "p";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( settingToolBar, cc.xy( 1, 1 ) );
    }
}
