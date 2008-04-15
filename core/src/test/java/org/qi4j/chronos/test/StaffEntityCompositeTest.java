/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.test;

import org.qi4j.chronos.model.composites.StaffEntityComposite;
import org.qi4j.chronos.model.composites.LoginEntityComposite;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.model.composites.MoneyEntityComposite;
import org.qi4j.chronos.model.Staff;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.SystemRoleTypeEnum;
import org.qi4j.chronos.model.Money;
import org.qi4j.chronos.model.User;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.composite.CompositeBuilder;
import org.qi4j.library.general.model.GenderType;
import org.qi4j.spi.entity.UuidIdentityGeneratorService;
import org.qi4j.entity.memory.MemoryEntityStoreService;
import org.junit.Test;
import org.junit.Before;
import java.util.Currency;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 12, 2008
 * Time: 7:52:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class StaffEntityCompositeTest extends AbstractCommonTest
{
    @Override public void assemble( ModuleAssembly assembler ) throws AssemblyException
    {
        super.assemble( assembler );
    }

    @Test public void createTest() throws Exception
    {
        print( null, null, StaffEntityCompositeTest.class.getName() + ".createTest" );

        SystemRole staffRole = newSystemRole( SystemRoleTypeEnum.STAFF, SystemRole.STAFF );
        Staff staff = newStaff( "test", "test", "test", "test", GenderType.MALE, 2000L, "USD", staffRole );

        String id = staff.identity().get();
        print( id );

        unitOfWork = complete( unitOfWork );

        staff = unitOfWork.find( id, StaffEntityComposite.class );
        unitOfWork.refresh( staff );

        print( staff.salary().get().displayValue().get() );
        
        User user = staff;

        if( user instanceof Staff )
        {
            print( null, "User is a staff!!!", null );
        }
        print( null, null );
    }
}
