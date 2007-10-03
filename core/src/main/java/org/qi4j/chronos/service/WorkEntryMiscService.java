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
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;

public interface WorkEntryMiscService
{
    List<WorkEntryEntityComposite> findAll( ProjectEntityComposite project );

    List<WorkEntryEntityComposite> findAll( ProjectEntityComposite project, FindFilter findFilter );

    int countAll( ProjectEntityComposite project );

    public List<WorkEntryEntityComposite> getRecentWorkEntryList( AccountEntityComposite account );

    List<WorkEntryEntityComposite> getRecentWorkEntryList( AccountEntityComposite account, FindFilter findFilter );

    int countAll( AccountEntityComposite account );

    void deleteWorkEntry( WorkEntryEntityComposite entryEntityComposite );
}
