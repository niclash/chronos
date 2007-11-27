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
package org.qi4j.chronos.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import javax.swing.Icon;
import org.qi4j.chronos.setting.ChronosSettingDialog;

public class ChronosSettingAction extends AbstractAction
{
    private final static Icon ICON = IconLoader.getIcon( "/general/applicationSettings.png" );

    public void actionPerformed( AnActionEvent e )
    {
        new ChronosSettingDialog().show();
    }

    public void update( AnActionEvent e )
    {
        super.update( e );

        //TODO fix icon
        e.getPresentation().setIcon( ICON );
    }
}
