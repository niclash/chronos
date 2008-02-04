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
package org.qi4j.chronos.model.associations;

import java.io.Serializable;
import org.qi4j.association.Association;
import org.qi4j.chronos.model.composites.ProjectAssigneeEntityComposite;
import org.qi4j.composite.Mixins;
import org.qi4j.composite.scope.PropertyField;

@Mixins( HasProjectAssignee.HasProjectAssigneeMixin.class )
public interface HasProjectAssignee
{
    Association<ProjectAssigneeEntityComposite> projectAssignee();

    final class HasProjectAssigneeMixin
        implements HasProjectAssignee, Serializable
    {
        private static final long serialVersionUID = 1L;

        @PropertyField
        private Association<ProjectAssigneeEntityComposite> projectAssignee;

        public final Association<ProjectAssigneeEntityComposite> projectAssignee()
        {
            return projectAssignee;
        }
    }
}
