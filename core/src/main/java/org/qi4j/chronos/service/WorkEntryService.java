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
package org.qi4j.chronos.service;

import java.util.List;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.Staff;

public interface WorkEntryService
{
    List<WorkEntry> findAll( HasWorkEntries hasWorkEntries );

    List<WorkEntry> findAll( HasWorkEntries hasWorkEntries, FindFilter findFilter );

    int countAll( HasWorkEntries hasWorkEntries );

    WorkEntry newInstance( Class<? extends WorkEntry> clazz );

    void update( WorkEntry workEntry );

    void delete( HasWorkEntries hasWorkEntries, List<WorkEntry> workEntries );

    WorkEntry get( HasWorkEntries hasWorkEntries, String id );

    List<WorkEntry> findAll( Project project, Staff staff );

    List<WorkEntry> findAll( Project project, Staff staff, FindFilter findFilter );

    int countAll( Project project, Staff staff );
}
