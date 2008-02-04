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
import org.qi4j.association.SetAssociation;
import org.qi4j.chronos.model.associations.HasLegalConditions;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.LegalConditionService;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.scope.Structure;

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
        SetAssociation<LegalConditionComposite> legalConditions = hasLegalConditions.legalConditions();
        for( LegalConditionComposite legalConditionComposite : legalConditions )
        {
            boolean next = loopCallBack.callBack( legalConditionComposite );

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
                if( legalCondition.name().get().equals( name ) )
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

        SetAssociation<ProjectEntityComposite> accountProjects = account.projects();
        for( ProjectEntityComposite accountProject : accountProjects )
        {
            list.addAll( findAll( accountProject ) );
        }

        return list;
    }

    public int countAll( AccountEntityComposite account )
    {
        return findAll( account ).size();
    }

    public void deleteLegalCondition( HasLegalConditions hasLegalConditions, Collection<LegalConditionComposite> legalConditions )
    {
        SetAssociation<LegalConditionComposite> targetLegalConditions = hasLegalConditions.legalConditions();
        for( LegalConditionComposite legalCondition : legalConditions )
        {
            LegalConditionComposite toBeDeleted = null;

            for( LegalConditionComposite targetLegalCondition : targetLegalConditions )
            {

                if( targetLegalCondition.name().get().equals( legalCondition.name().get() ) )
                {
                    toBeDeleted = targetLegalCondition;
                    break;
                }
            }

            targetLegalConditions.remove( toBeDeleted );
        }
    }

    public void updateLegalCondition( HasLegalConditions hasLegalConditions, LegalConditionComposite oldLegalCondition, LegalConditionComposite newLegalCondition )
    {
        LegalConditionComposite toBeDeleted = null;

        SetAssociation<LegalConditionComposite> legalConditions = hasLegalConditions.legalConditions();
        String oldLegalConditionName = oldLegalCondition.name().get();
        for( LegalConditionComposite legalCondition : legalConditions )
        {
            if( legalCondition.name().get().equals( oldLegalConditionName ) )
            {
                toBeDeleted = legalCondition;
                break;
            }
        }

        legalConditions.remove( toBeDeleted );
        legalConditions.add( newLegalCondition );
    }
}
