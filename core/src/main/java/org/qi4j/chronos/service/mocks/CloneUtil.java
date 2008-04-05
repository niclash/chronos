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

import org.qi4j.chronos.model.composites.ContactComposite;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.composite.CompositeBuilderFactory;
import org.qi4j.entity.association.SetAssociation;
import org.qi4j.entity.UnitOfWork;
import org.qi4j.entity.UnitOfWorkFactory;
import org.qi4j.library.general.model.Contact;

public final class CloneUtil
{
    public static PriceRateComposite clonePriceRate( UnitOfWorkFactory factory, PriceRateComposite priceRate )
    {
        PriceRateComposite cloned = factory.currentUnitOfWork().newEntityBuilder( PriceRateComposite.class ).newInstance();

        cloned.amount().set( priceRate.amount().get() );
        cloned.priceRateType().set( priceRate.priceRateType().get() );
        cloned.projectRole().set( priceRate.projectRole().get() );

        return cloned;
    }

    public static PriceRateComposite clonePriceRate( CompositeBuilderFactory factory, PriceRateComposite priceRate )
    {
        PriceRateComposite cloned = factory.newCompositeBuilder( PriceRateComposite.class ).newInstance();

        cloned.amount().set( priceRate.amount().get() );
        cloned.priceRateType().set( priceRate.priceRateType().get() );
        cloned.projectRole().set( priceRate.projectRole().get() );

        return cloned;
    }

    public static LegalConditionComposite cloneLegalCondition( CompositeBuilderFactory factory, LegalConditionComposite legalCondition )
    {
        LegalConditionComposite cloned = factory.newCompositeBuilder( LegalConditionComposite.class ).newInstance();

        cloned.name().set( legalCondition.name().get() );
        cloned.description().set( legalCondition.description().get() );

        return cloned;
    }

    public static PriceRateScheduleComposite clonePriceRateSchedule( UnitOfWorkFactory factory, PriceRateScheduleComposite priceRateSchedule )
    {
        PriceRateScheduleComposite cloned = factory.currentUnitOfWork().newEntityBuilder( PriceRateScheduleComposite.class ).newInstance();

        cloned.name().set( priceRateSchedule.name().get() );
        cloned.currency().set( priceRateSchedule.currency().get() );

        SetAssociation<PriceRateComposite> clonedPriceRates = cloned.priceRates();

        for( PriceRateComposite priceRateComposite : priceRateSchedule.priceRates() )
        {
            clonedPriceRates.add( clonePriceRate( factory, priceRateComposite ) );
        }

        return cloned;
    }

    public static PriceRateScheduleComposite clonePriceRateSchedule( CompositeBuilderFactory factory, PriceRateScheduleComposite priceRateSchedule )
    {
        PriceRateScheduleComposite cloned = factory.newCompositeBuilder( PriceRateScheduleComposite.class ).newInstance();

        cloned.name().set( priceRateSchedule.name().get() );
        cloned.currency().set( priceRateSchedule.currency().get() );

//        SetAssociation<PriceRateComposite> clonedPriceRates = cloned.priceRates();
        for( PriceRateComposite priceRateComposite : priceRateSchedule.priceRates() )
        {
//            clonedPriceRates.add( clonePriceRate( factory, priceRateComposite ) );
            cloned.priceRates().add( clonePriceRate( factory, priceRateComposite ) );
        }

        return cloned;
    }

    public static RelationshipComposite cloneRelationship( CompositeBuilderFactory factory, RelationshipComposite relationship )
    {
        RelationshipComposite clonedRelationshipComposite = factory.newCompositeBuilder( RelationshipComposite.class ).newInstance();

        clonedRelationshipComposite.relationship().set( relationship.relationship().get() );

        return clonedRelationshipComposite;
    }

    public static ContactComposite cloneContact( CompositeBuilderFactory factory, ContactComposite contact )
    {
        ContactComposite cloned = factory.newCompositeBuilder( ContactComposite.class ).newInstance();

        cloned.contactType().set( contact.contactType().get() );
        cloned.contactValue().set( contact.contactValue().get() );

        return cloned;
    }

    public static ProjectRoleComposite cloneProjectRole( CompositeBuilderFactory factory, ProjectRoleComposite projectRole )
    {
        ProjectRoleComposite cloned = factory.newCompositeBuilder( ProjectRoleComposite.class ).newInstance();

        cloned.name().set( projectRole.name().get() );

        return cloned;
    }
}
