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
package org.qi4j.chronos.service.mocks;

import java.util.List;
import org.qi4j.api.annotation.scope.Property;
import org.qi4j.api.persistence.Identity;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.service.ProjectOwnerService;

public abstract class MockProjectOwnerBasedServiceMixin<ITEM extends Identity> extends MockParentBasedServiceMixin<ITEM, ProjectOwnerEntityComposite>
{
    @Property( "projectOwnerService" ) private ProjectOwnerService projectOwnerService;

    public MockProjectOwnerBasedServiceMixin()
    {
    }

    protected List<ProjectOwnerEntityComposite> getParentList()
    {
        return projectOwnerService.findAll();
    }
}
