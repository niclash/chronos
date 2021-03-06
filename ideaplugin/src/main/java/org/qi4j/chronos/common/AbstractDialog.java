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
import com.intellij.openapi.ui.DialogWrapper;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.jetbrains.annotations.Nullable;
import org.qi4j.chronos.ChronosApp;
import org.qi4j.chronos.ChronosProjectComponent;
import org.qi4j.chronos.service.Services;

public abstract class AbstractDialog extends DialogWrapper
{
    private final Project project;

    public AbstractDialog( Project project, boolean canBeParent )
    {
        super( canBeParent );
        this.project = project;

        setModal( true );
        setTitle( getDialogTitle() );

        init();
    }

    @Nullable
    protected final JComponent createCenterPanel()
    {
        JPanel centerPanel = new JPanel();

        initComponents();
        initLayout( centerPanel );

        return centerPanel;
    }

    private void initLayout( JPanel panel )
    {
        FormLayout layout = new FormLayout( getLayoutColSpec(), getLayoutRowSpec() );
        PanelBuilder builder = new PanelBuilder( layout, panel );
        builder.setDefaultDialogBorder();

        CellConstraints cc = new CellConstraints();

        initLayout( builder, cc );
    }

    protected Services getServices()
    {
        return getChronosApp().getServices();
    }

    protected ChronosApp getChronosApp()
    {
        ChronosProjectComponent instance = ChronosProjectComponent.getInstance( project );
        ChronosApp app = instance.getChronosApp();
        return app;
    }

    protected Project getProject()
    {
        return project;
    }

    protected abstract void initComponents();

    protected abstract String getLayoutColSpec();

    protected abstract String getLayoutRowSpec();

    protected abstract void initLayout( PanelBuilder builder, CellConstraints cc );

    protected abstract String getDialogTitle();
}
