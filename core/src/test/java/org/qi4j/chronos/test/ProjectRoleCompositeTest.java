/*
 * Copyright (c) 2008, kamil. All Rights Reserved.
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

import org.qi4j.chronos.model.composites.ProjectRoleComposite;
import org.qi4j.chronos.test.util.PropertyVOUtil;
import org.qi4j.chronos.test.util.CompImpl;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class ProjectRoleCompositeTest extends AbstractTestCase<ProjectRoleComposite>
{

    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        builder = compositeBuilderFactory.newCompositeBuilder( ProjectRoleComposite.class );
        vo = new PropertyVOUtil<ProjectRoleComposite>( ProjectRoleComposite.class, String.class, String.class );
        init();
    }

    @After
    @Override
    public void tearDown() throws Exception
    {
        builder = null;
        t = null;
        vo = null;
        super.tearDown();
    }

    protected void init()
    {
        comp = new CompImpl( builder );
        t = newInstance( ProjectRoleComposite.class, comp );
    }

    @Test
    public void createTest()
    {
        System.out.println( ProjectRoleCompositeTest.class.getSimpleName() + ".createTest" );
//        super.validate( false );
    }

//    @Test
    public void updateTest()
    {
        System.out.println( ProjectRoleCompositeTest.class.getSimpleName() + ".updateTest" );
        print( "Before---" );
        super.update();
        super.validate( true );
        print( "After---" );
    }
}
