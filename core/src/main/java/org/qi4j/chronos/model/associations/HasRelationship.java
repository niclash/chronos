/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.model.associations;

import java.io.Serializable;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.scope.PropertyField;
import org.qi4j.property.Property;

/**
 * Generic interface to describe association with relationship.
 */
@Mixins( HasRelationship.HasRelationshipMixin.class )
public interface HasRelationship extends Serializable
{
    Property<RelationshipComposite> relationship();

    final class HasRelationshipMixin
        implements HasRelationship, Serializable
    {
        private static final long serialVersionUID = 1L;

        @PropertyField
        private Property<RelationshipComposite> relationship;

        public final Property<RelationshipComposite> relationship()
        {
            return relationship;
        }
    }
}
