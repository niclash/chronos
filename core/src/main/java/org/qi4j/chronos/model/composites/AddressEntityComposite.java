package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.annotation.ModifiedBy;
import org.qi4j.api.persistence.composite.EntityComposite;
import org.qi4j.chronos.model.mixins.AddressDescriptorMixin;
import org.qi4j.chronos.model.modifiers.RequiredFields;
import org.qi4j.chronos.model.modifiers.RequiredFieldsValidationModifier;
import org.qi4j.library.framework.properties.PropertiesMixin;
import org.qi4j.library.general.model.Address;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.library.general.model.Validatable;

/**
 * Persistable Address entity that provides services including:
 * {@link org.qi4j.library.general.model.Validatable} and {@link org.qi4j.library.general.model.Descriptor}.
 */
@ModifiedBy( { RequiredFieldsValidationModifier.class } )
@ImplementedBy( { AddressDescriptorMixin.class, PropertiesMixin.class } )
@RequiredFields( { "firstLine", "zipCode", "city" } )
public interface AddressEntityComposite extends Address, Validatable, Descriptor, EntityComposite
{
}