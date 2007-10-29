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

import java.util.Iterator;
import org.qi4j.CompositeBuilderFactory;
import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;

public final class CloneUtil
{
    public static PriceRateComposite clonePriceRate( CompositeBuilderFactory factory, PriceRateComposite priceRate )
    {
        PriceRateComposite cloned = factory.newCompositeBuilder( PriceRateComposite.class ).newInstance();

        cloned.setAmount( priceRate.getAmount() );
        cloned.setPriceRateType( priceRate.getPriceRateType() );
        cloned.setProjectRole( priceRate.getProjectRole() );

        return cloned;
    }

    public static LegalConditionComposite cloneLegalCondition( CompositeBuilderFactory factory, LegalConditionComposite legalCondition )
    {
        LegalConditionComposite cloned = factory.newCompositeBuilder( LegalConditionComposite.class ).newInstance();

        cloned.setName( legalCondition.getName() );
        cloned.setDescription( legalCondition.getDescription() );

        return cloned;
    }

    public static PriceRateScheduleComposite clonePriceRateSchedule( CompositeBuilderFactory factory, PriceRateScheduleComposite priceRateSchedule )
    {
        PriceRateScheduleComposite cloned = factory.newCompositeBuilder( PriceRateScheduleComposite.class ).newInstance();

        cloned.setName( priceRateSchedule.getName() );
        cloned.setCurrency( priceRateSchedule.getCurrency() );

        Iterator<PriceRateComposite> priceRateIter = priceRateSchedule.priceRateIterator();

        while( priceRateIter.hasNext() )
        {
            cloned.addPriceRate( clonePriceRate( factory, priceRateIter.next() ) );
        }

        return cloned;
    }

    public static RelationshipComposite cloneRelationship( CompositeBuilderFactory factory, RelationshipComposite relationship )
    {
        RelationshipComposite clonedRelationshipComposite = factory.newCompositeBuilder( RelationshipComposite.class ).newInstance();

        clonedRelationshipComposite.setRelationship( relationship.getRelationship() );

        return clonedRelationshipComposite;
    }

    public static ContactComposite cloneContact( CompositeBuilderFactory factory, ContactComposite contact )
    {
        ContactComposite cloned = factory.newCompositeBuilder( ContactComposite.class ).newInstance();

        cloned.setContactType( contact.getContactType() );
        cloned.setContactValue( contact.getContactValue() );

        return cloned;
    }

    public static ProjectRoleComposite cloneProjectRole( CompositeBuilderFactory factory, ProjectRoleComposite projectRole )
    {
        ProjectRoleComposite cloned = factory.newCompositeBuilder( ProjectRoleComposite.class ).newInstance();

        cloned.setName( projectRole.getName() );

        return cloned;
    }
}
