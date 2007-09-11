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

import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.model.ProjectRole;
import org.qi4j.chronos.model.mixins.HasProjectRoleMixin;

/**
 * Generic interface describing association with {@link ProjectRole}
 */
@ImplementedBy( HasProjectRoleMixin.class )
public interface HasProjectRole
{
    void setRole( ProjectRole role );

    ProjectRole getRole();
}