/*  Copyright 2008 Edward Yakop.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.domain.model.common.comment.assembly;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.qi4j.api.query.QueryBuilder;
import org.qi4j.api.service.ServiceFinder;
import org.qi4j.api.service.ServiceReference;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.chronos.domain.model.common.comment.Comment;
import org.qi4j.chronos.domain.model.common.assembly.AbstractCommonTest;
import org.qi4j.chronos.domain.model.user.admin.Admin;
import org.qi4j.chronos.domain.model.user.assembly.UserAssembler;

/**
 * @author edward.yakop@gmail.com
 * @since 0.5
 */
public final class CommentFactoryTest extends AbstractCommonTest
{
    public final void assemble( ModuleAssembly module )
        throws AssemblyException
    {
        super.assemble( module );
        new UserAssembler().assemble( module );
    }

    @Test
    public final void createCommentTest()
    {
        UnitOfWork uow = unitOfWorkFactory.newUnitOfWork();

        ServiceFinder serviceFinder = moduleInstance.serviceFinder();
        ServiceReference<CommentFactory> commentFactoryRef = serviceFinder.findService( CommentFactory.class );
        CommentFactory commentFactory = commentFactoryRef.get();
        try
        {
            Admin adminUser = findSystemAdmin( uow );

            Comment comment = commentFactory.create( "comment", adminUser );

            // Verify comment
            assertEquals( "comment", comment.content() );
            assertEquals( adminUser, comment.createdBy() );
            assertNotNull( comment.createdDate() );
            assertEquals( comment.createdDate(), comment.lastUpdatedDate() );

            // Remove comment
            uow.remove( comment );
        }
        finally
        {
            uow.discard();
        }
    }

    private Admin findSystemAdmin( UnitOfWork uow )
    {
        QueryBuilder<Admin> builder = queryBuilderFactory.newQueryBuilder( Admin.class );
        Admin adminUser = builder.newQuery( uow ).find();
        assertNotNull( adminUser );
        return adminUser;
    }
}
