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
import java.util.ArrayList;
import java.util.List;

public class ActivityManager
{
    private final List<Activity> activityLists;
    private final List<AbstractAcitivityTracker> acitivityTrackers;

    private Project project;

    public ActivityManager( Project project )
    {
        this.project = project;

        activityLists = new ArrayList<Activity>();
        acitivityTrackers = new ArrayList<AbstractAcitivityTracker>();

        initActivityTracker();
    }

    private void initActivityTracker()
    {
        acitivityTrackers.add( new CheckinActivityTracker( this ) );
        acitivityTrackers.add( new VirtualFileActivityTracker( this, project ) );
        acitivityTrackers.add( new TodoItemActivityTracker( this, project ) );
    }

    public Project getProject()
    {
        return project;
    }

    public void start()
    {
        for( AbstractAcitivityTracker tracker : acitivityTrackers )
        {
            tracker.start();
        }
    }

    public void stop()
    {
        for( AbstractAcitivityTracker tracker : acitivityTrackers )
        {
            tracker.stop();
        }
    }

    public void newActivity( Activity activity )
    {
        synchronized( activityLists )
        {
            activityLists.add( activity );

            System.err.println( "New Activity " + activity.getComment() );
        }
    }

    public Activity[] getActivities()
    {
        synchronized( activityLists )
        {
            return activityLists.toArray( new Activity[activityLists.size()] );
        }
    }

    public void removeAllActivities()
    {
        synchronized( activityLists )
        {
            activityLists.clear();
        }
    }
}
