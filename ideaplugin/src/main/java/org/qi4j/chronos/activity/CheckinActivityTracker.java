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

import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import org.jetbrains.annotations.NotNull;

public class CheckinActivityTracker extends AbstractAcitivityTracker
{
    private CheckinHandlerFactory checkinHandlerFactory;

    public CheckinActivityTracker( ActivityManager manager )
    {
        super( manager );
    }

    public void start()
    {
        if( checkinHandlerFactory == null )
        {
            checkinHandlerFactory = new MyCheckinHandlerFactory();

            ProjectLevelVcsManager.getInstance( getProject() ).registerCheckinHandlerFactory( checkinHandlerFactory );
        }
    }

    public void stop()
    {
        if( checkinHandlerFactory != null )
        {
            ProjectLevelVcsManager.getInstance( getProject() ).unregisterCheckinHandlerFactory( checkinHandlerFactory );

            checkinHandlerFactory = null;
        }
    }

    private class MyCheckinHandlerFactory extends CheckinHandlerFactory
    {
        @NotNull
        public CheckinHandler createHandler( final CheckinProjectPanel panel )
        {
            return new CheckinHandler()
            {
                public void checkinSuccessful()
                {
                    newActivity( new Activity( "[Checkin] - " + panel.getCommitMessage() ) );
                }
            };
        }
    }
}
