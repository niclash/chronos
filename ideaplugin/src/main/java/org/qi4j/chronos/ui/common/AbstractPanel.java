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
package org.qi4j.chronos.ui.common;

import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JPanel;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.ui.setting.ChronosSetting;
import org.qi4j.chronos.util.ChronosUtil;

public abstract class AbstractPanel extends JPanel
{
    private ChronosSetting chronosSetting;
    private ProjectEntityComposite chronosProject;
    private StaffEntityComposite staff;
    private Services services;

    public AbstractPanel()
    {
    }

    protected final void init()
    {
        initComponents();

        initLayout();
    }

    private void initLayout()
    {
        FormLayout layout = new FormLayout( getLayoutColSpec(), getLayoutRowSpec() );
        PanelBuilder builder = new PanelBuilder( layout, this );

        CellConstraints cc = new CellConstraints();

        initLayout( builder, cc );
    }

    protected ChronosSetting getChronosSetting( Project project )
    {
        if( chronosSetting == null )
        {
            chronosSetting = ChronosUtil.getChronosSetting( project );
        }

        return chronosSetting;
    }

    protected ProjectEntityComposite getChronosProject( Project project )
    {
        if( chronosProject == null )
        {
            chronosProject = getChronosSetting( project ).getChronosProject();
        }

        return chronosProject;
    }

    protected StaffEntityComposite getStaff( Project project )
    {
        if( staff == null )
        {
            staff = getChronosSetting( project ).getStaff();
        }

        return staff;
    }

    protected Services getServices( Project project )
    {
        if( services == null )
        {
            services = getChronosSetting( project ).getServices();
        }

        return services;
    }

    protected abstract String getLayoutColSpec();

    protected abstract String getLayoutRowSpec();

    protected abstract void initLayout( PanelBuilder builder, CellConstraints cc );

    protected abstract void initComponents();
}
