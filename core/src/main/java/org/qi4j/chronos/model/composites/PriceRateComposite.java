package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.PriceRate;
import org.qi4j.chronos.model.Role;
import org.qi4j.chronos.model.mixins.RoleMixin;
import org.qi4j.library.framework.properties.PropertiesMixin;

@ImplementedBy( { RoleMixin.class, PropertiesMixin.class } )
public interface PriceRateComposite extends PriceRate, Role, PersistentComposite
{
}