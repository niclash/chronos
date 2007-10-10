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
package org.qi4j.chronos.ui.legalcondition;

import java.util.List;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.LegalConditionService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public abstract class LegalConditionDataProvider extends AbstractSortableDataProvider<LegalConditionComposite, String>
{
    public String getId( LegalCondition legalCondition )
    {
        return legalCondition.getName();
    }

    public String getId( LegalConditionComposite t )
    {
        return t.getName();
    }

    public LegalConditionComposite load( String id )
    {
        return getLegalConditionService().get( getProject(), id );
    }

    public List<LegalConditionComposite> dataList( int first, int count )
    {
        return getLegalConditionService().findAll( getProject(), new FindFilter( first, count ) );
    }

    public int getSize()
    {
        return getLegalConditionService().countAll( getProject() );
    }

    private LegalConditionService getLegalConditionService()
    {
        return ChronosWebApp.getServices().getLegalConditionService();
    }

    public abstract ProjectEntityComposite getProject();
}
