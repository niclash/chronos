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
import java.util.List;
import org.qi4j.association.SetAssociation;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.chronos.service.RelationshipService;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.composite.scope.Structure;

public class MockRelationshipServiceMixin implements RelationshipService
{
    @Structure private CompositeBuilderFactory factory;

    public MockRelationshipServiceMixin()
    {
    }

    public List<RelationshipComposite> findAll( CustomerEntityComposite customer )
    {
        final List<RelationshipComposite> relationshipList = new ArrayList<RelationshipComposite>();

        loopRelationship( customer, new LoopCallBack<RelationshipComposite>()
        {
            public boolean callBack( RelationshipComposite relationshipComposite )
            {
                relationshipList.add( CloneUtil.cloneRelationship( factory, relationshipComposite ) );

                return true;
            }
        } );

        return relationshipList;
    }


    private void loopRelationship( CustomerEntityComposite customer, LoopCallBack<RelationshipComposite> loopCallBack )
    {

        SetAssociation<ContactPersonEntityComposite> contacts = customer.contactPersons();
        for( ContactPersonEntityComposite contact : contacts )
        {
            RelationshipComposite relationship = contact.relationship().get();

            if( !loopCallBack.callBack( relationship ) )
            {
                break;
            }
        }
    }

    public RelationshipComposite get( CustomerEntityComposite customer, final String relationship )
    {
        final RelationshipComposite[] returnValue = new RelationshipComposite[1];

        loopRelationship( customer, new LoopCallBack<RelationshipComposite>()
        {
            public boolean callBack( RelationshipComposite relationshipComposite )
            {
                if( relationshipComposite.relationship().get().equals( relationship ) )
                {
                    returnValue[ 0 ] = CloneUtil.cloneRelationship( factory, relationshipComposite );

                    return false;
                }

                return true;
            }
        } );

        return returnValue[ 0 ];
    }
}
