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
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;

public class LegalConditionDataProvider extends AbstractSortableDataProvider<LegalCondition, String>
{
    //TODO bp. FIXME. Remove static and use non detachable model.
    private static List<LegalCondition> list;

    public LegalConditionDataProvider( List<LegalCondition> legalConditions )
    {
        this.list = legalConditions;
    }

    public String getId( LegalCondition legalCondition )
    {
        return legalCondition.getLegalConditionName();
    }

    public LegalCondition load( String id )
    {
        for( LegalCondition legalCondition : list )
        {
            if( legalCondition.getLegalConditionName().equals( id ) )
            {
                return legalCondition;
            }
        }

        return null;
    }

    public List<LegalCondition> dataList( int first, int count )
    {
        return list.subList( first, first + count );
    }

    public int getSize()
    {
        return list.size();
    }
}
