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
package org.qi4j.chronos.service.mocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.qi4j.CompositeBuilderFactory;
import org.qi4j.annotation.scope.Structure;
import org.qi4j.chronos.model.associations.HasLegalConditions;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.LegalConditionService;

public class MockLegalConditionServiceMixin implements LegalConditionService
{
    @Structure private CompositeBuilderFactory factory;

    public List<LegalConditionComposite> findAll( HasLegalConditions hasLegalConditions, FindFilter findFilter )
    {
        return findAll( hasLegalConditions ).subList( findFilter.getFirst(), findFilter.getFirst() + findFilter.getCount() );
    }

    public List<LegalConditionComposite> findAll( HasLegalConditions hasLegalConditions )
    {
        final List<LegalConditionComposite> list = new ArrayList<LegalConditionComposite>();

        loopLegalCondition( hasLegalConditions, new LoopCallBack<LegalConditionComposite>()
        {
            public boolean callBack( LegalConditionComposite legalCondition )
            {
                list.add( CloneUtil.cloneLegalCondition( factory, legalCondition ) );

                return true;
            }
        } );

        return list;
    }

    public int countAll( HasLegalConditions hasLegalConditions )
    {
        return findAll( hasLegalConditions ).size();
    }

    private void loopLegalCondition( HasLegalConditions hasLegalConditions, LoopCallBack<LegalConditionComposite> loopCallBack )
    {
        Iterator<LegalConditionComposite> iter = hasLegalConditions.legalConditionIterator();

        while( iter.hasNext() )
        {
            boolean next = loopCallBack.callBack( iter.next() );

            if( !next )
            {
                break;
            }
        }
    }

    public LegalConditionComposite get( HasLegalConditions hasLegalConditions, final String name )
    {
        final LegalConditionComposite[] returnValue = new LegalConditionComposite[1];

        loopLegalCondition( hasLegalConditions, new LoopCallBack<LegalConditionComposite>()
        {
            public boolean callBack( LegalConditionComposite legalCondition )
            {
                if( legalCondition.getName().equals( name ) )
                {
                    returnValue[ 0 ] = CloneUtil.cloneLegalCondition( factory, legalCondition );
                    return false;
                }

                return true;
            }
        } );

        return returnValue[ 0 ];
    }

    public List<LegalConditionComposite> findAll( AccountEntityComposite account )
    {
        List<LegalConditionComposite> list = new ArrayList<LegalConditionComposite>();

        Iterator<ProjectEntityComposite> projectIter = account.projectIterator();

        while( projectIter.hasNext() )
        {
            list.addAll( findAll( projectIter.next() ) );
        }

        return list;
    }

    public int countAll( AccountEntityComposite account )
    {
        return findAll( account ).size();
    }

    public void deleteLegalCondition( HasLegalConditions hasLegalConditions, Collection<LegalConditionComposite> legalConditions )
    {
        for( LegalConditionComposite legalCondition : legalConditions )
        {
            LegalConditionComposite toBeDeleted = null;

            Iterator<LegalConditionComposite> legalConditionIter = hasLegalConditions.legalConditionIterator();

            while( legalConditionIter.hasNext() )
            {
                LegalConditionComposite tempLegalCondition = legalConditionIter.next();

                if( tempLegalCondition.getName().equals( legalCondition.getName() ) )
                {
                    toBeDeleted = tempLegalCondition;
                    break;
                }
            }

            hasLegalConditions.removeLegalCondition( toBeDeleted );
        }
    }

    public void updateLegalCondition( HasLegalConditions hasLegalConditions, LegalConditionComposite oldLegalCondition, LegalConditionComposite newLegalCondition )
    {
        LegalConditionComposite toBeDeleted = null;

        Iterator<LegalConditionComposite> legalConditionIter = hasLegalConditions.legalConditionIterator();

        while( legalConditionIter.hasNext() )
        {
            LegalConditionComposite temp = legalConditionIter.next();

            if( temp.getName().equals( oldLegalCondition.getName() ) )
            {
                toBeDeleted = temp;
            }
        }

        hasLegalConditions.removeLegalCondition( toBeDeleted );
        hasLegalConditions.addLegalCondition( newLegalCondition );
    }
}
