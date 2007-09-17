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
import java.util.Iterator;
import java.util.List;
import org.qi4j.chronos.model.composites.ContactPersonEntityComposite;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.chronos.service.RelationshipService;

public class MockRelationshipServiceMixin implements RelationshipService
{
    public MockRelationshipServiceMixin()
    {
    }

    public List<RelationshipComposite> findAll( ProjectOwnerEntityComposite projectOwner )
    {
        final List<RelationshipComposite> relationshipList = new ArrayList<RelationshipComposite>();

        loopRelationship( projectOwner, new LoopCallBack<RelationshipComposite>()
        {
            public boolean callBack( RelationshipComposite relationshipComposite )
            {
                relationshipList.add( relationshipComposite );

                return true;
            }
        } );

        return relationshipList;
    }

    private void loopRelationship( ProjectOwnerEntityComposite projectOwner, LoopCallBack<RelationshipComposite> loopCallBack )
    {
        Iterator<ContactPersonEntityComposite> contactPersonIterator = projectOwner.contactPersonIterator();

        while( contactPersonIterator.hasNext() )
        {
            ContactPersonEntityComposite contactPerson = contactPersonIterator.next();

            RelationshipComposite relationship = contactPerson.getRelationship();

            if( !loopCallBack.callBack( relationship ) )
            {
                break;
            }
        }
    }

    public RelationshipComposite get( ProjectOwnerEntityComposite projectOwner, final String relationship )
    {
        final RelationshipComposite[] returnValue = new RelationshipComposite[1];

        loopRelationship( projectOwner, new LoopCallBack<RelationshipComposite>()
        {
            public boolean callBack( RelationshipComposite relationshipComposite )
            {
                if( relationshipComposite.getRelationship().equals( relationship ) )
                {
                    returnValue[ 0 ] = relationshipComposite;

                    return false;
                }

                return true;
            }
        } );

        return returnValue[ 0 ];
    }
}
