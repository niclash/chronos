package org.qi4j.chronos.model.composites;

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.api.persistence.composite.PersistentComposite;
import org.qi4j.chronos.model.Contact;
import org.qi4j.library.framework.properties.PropertiesMixin;

/**
 * TODO:
 * ContactCompositeValidator - performing regular expression checking on the contact based on the contact type
 */
@ImplementedBy( { PropertiesMixin.class } )
public interface ContactComposite extends Contact, ContactTypeComposite, PersistentComposite
{
}
