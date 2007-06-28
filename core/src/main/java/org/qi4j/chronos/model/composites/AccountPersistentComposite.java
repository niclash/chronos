package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.composites.association.HasPriceRateSchedules;
import org.qi4j.chronos.model.composites.association.HasProjects;
import org.qi4j.chronos.model.mixins.AccountNameMixin;
import org.qi4j.chronos.model.modifiers.NotNullValidationModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Name;

@ImplementedBy( { AccountNameMixin.class, PropertiesMixin.class } )
public interface AccountPersistentComposite extends Name, HasPriceRateSchedules, HasProjects, PersistentComposite
{

}
