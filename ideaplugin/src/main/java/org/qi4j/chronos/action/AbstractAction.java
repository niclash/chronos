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

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataConstants;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import org.qi4j.chronos.ChronosApp;
import org.qi4j.chronos.ChronosProjectComponent;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.service.Services;

public abstract class AbstractAction extends AnAction
{
    //convenient method
    public Services getServices( AnActionEvent e )
    {
        return getChronosApp( e ).getServices();
    }

    //convenient method
    public ProjectAssignee getProjectAssignee( AnActionEvent e )
    {
        return getChronosApp( e ).getProjectAssignee();
    }

    public ChronosApp getChronosApp( AnActionEvent e )
    {
        DataContext dataContext = e.getDataContext();
        Project project = (Project) dataContext.getData( DataConstants.PROJECT );
        return ChronosProjectComponent.getInstance( project ).getChronosApp();
    }
}
