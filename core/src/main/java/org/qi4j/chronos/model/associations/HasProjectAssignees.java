/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.model.associations;

import java.io.Serializable;
import org.qi4j.association.SetAssociation;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.scope.AssociationField;

@Mixins( HasProjectAssignees.HasProjectAssigneesMixin.class )
public interface HasProjectAssignees extends Serializable
{
    SetAssociation<ProjectAssigneeEntityComposite> projectAssignees();

    final class HasProjectAssigneesMixin
        implements HasProjectAssignees, Serializable
    {
        private static final long serialVersionUID = 1L;

        @AssociationField
        private SetAssociation<ProjectAssigneeEntityComposite> projectAssignees;

        public SetAssociation<ProjectAssigneeEntityComposite> projectAssignees()
        {
            return projectAssignees;
        }
    }
}
