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
package org.qi4j.chronos.service.systemrole;

import java.util.List;
import java.util.Collection;
import org.qi4j.chronos.model.composites.SystemRoleComposite;
import org.qi4j.chronos.model.SystemRole;

public interface SystemRoleService
{
    List<SystemRole> findAll();

    void save( SystemRole systemRole );

    void saveAll( Collection<SystemRole> systemRoles );

    List<SystemRole> findAllStaffSystemRole();

    List<SystemRole> findAllStaffSystemRole( int first, int count );

    int countAllStaffSystemRole();

    SystemRole getSystemRoleByName( String name );
}