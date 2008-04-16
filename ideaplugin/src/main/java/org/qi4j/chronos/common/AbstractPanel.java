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
package org.qi4j.chronos.common;

import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JPanel;
import org.qi4j.chronos.ChronosApp;
import org.qi4j.chronos.ChronosProjectComponent;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.service.Services;

public abstract class AbstractPanel extends JPanel
{
    private ChronosApp chronosApp;
    private org.qi4j.chronos.model.Project chronosProject;
    private Staff staff;
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

    protected ChronosApp getChronosApp( Project project )
    {
        if( chronosApp == null )
        {
            chronosApp = ChronosProjectComponent.getInstance( project ).getChronosApp();
        }

        return chronosApp;
    }

    protected org.qi4j.chronos.model.Project getChronosProject( Project project )
    {
        if( chronosProject == null )
        {
            chronosProject = getChronosApp( project ).getChronosProject();
        }

        return chronosProject;
    }

    protected Staff getStaff( Project project )
    {
        if( staff == null )
        {
            staff = getChronosApp( project ).getStaff();
        }

        return staff;
    }

    protected Services getServices( Project project )
    {
        if( services == null )
        {
            services = getChronosApp( project ).getServices();
        }

        return services;
    }

    protected abstract String getLayoutColSpec();

    protected abstract String getLayoutRowSpec();

    protected abstract void initLayout( PanelBuilder builder, CellConstraints cc );

    protected abstract void initComponents();
}
