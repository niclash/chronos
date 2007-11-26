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
package org.qi4j.chronos.activity;

import com.intellij.openapi.project.Project;

public abstract class AbstractAcitivityTracker
{
    private ActivityManager manager;

    public AbstractAcitivityTracker( ActivityManager manager )
    {
        this.manager = manager;
    }

    protected void newActivity( Activity activity )
    {
        manager.newUserActivity( activity );
    }

    protected Project getProject()
    {
        return manager.getProject();
    }

    public abstract void start();

    public abstract void stop();
}
