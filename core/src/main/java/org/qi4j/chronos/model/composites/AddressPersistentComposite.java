package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.mixins.AddressDescriptorMixin;
import org.qi4j.chronos.model.modifiers.AddressNotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.CityStateCountryValidationModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.composites.AddressComposite;

/**
 * Persistable {@link org.qi4j.library.general.model.composites.AddressComposite} that attached services including:
 * {@link org.qi4j.library.general.model.Validatable} and {@link org.qi4j.library.general.model.Descriptor}.
 *
 * AddressPersistentComposite is an entity.
 */
@ModifiedBy( { AddressNotNullValidationModifier.class, CityStateCountryValidationModifier.class } )
@ImplementedBy( { AddressDescriptorMixin.class, PropertiesMixin.class } )
public interface AddressPersistentComposite extends AddressComposite, Validatable, Descriptor, PersistentComposite
{
}
