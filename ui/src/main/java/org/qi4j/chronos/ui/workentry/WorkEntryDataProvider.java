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
package org.qi4j.chronos.ui.workentry;

import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class WorkEntryDataProvider extends AbstractSortableDataProvider<WorkEntry, String>
{
    public String getId( WorkEntry workEntry )
    {
        return ( (WorkEntryEntityComposite) workEntry).identity().get();
    }

    public final WorkEntry load( String id )
    {
        return getWorkEntryService().get( getHasWorkEntries(), id );
    }

    private WorkEntryService getWorkEntryService()
    {
        return ChronosWebApp.getServices().getWorkEntryService();
    }

    public abstract HasWorkEntries getHasWorkEntries();

}
