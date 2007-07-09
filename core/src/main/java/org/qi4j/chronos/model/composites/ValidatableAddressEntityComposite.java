package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.chronos.model.mixins.AddressDescriptorMixin;
import org.qi4j.chronos.model.modifiers.AddressNotNullValidationModifier;
import org.qi4j.chronos.model.modifiers.CityStateCountryValidationModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.Validatable;
import org.qi4j.library.general.model.composites.AddressEntityComposite;

/**
 * Persistable {@link org.qi4j.library.general.model.composites.AddressEntityComposite} that attached services including:
 * {@link org.qi4j.library.general.model.Validatable} and {@link org.qi4j.library.general.model.Descriptor}.
 *
 * ValidatableAddressEntityComposite is an entity.
 */
@ModifiedBy( { AddressNotNullValidationModifier.class } )
@ImplementedBy( { AddressDescriptorMixin.class, PropertiesMixin.class } )
public interface ValidatableAddressEntityComposite extends AddressEntityComposite, Validatable, Descriptor
{
}