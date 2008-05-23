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
package org.qi4j.chronos.ui.systemrole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.SystemRoleEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.entity.Identity;

public abstract class SystemRoleTable extends ActionTable<IModel, String>
{
    private AbstractSortableDataProvider<IModel, String> dataProvider;

    public SystemRoleTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<IModel, String> getDetachableDataProvider()
    {
        if( dataProvider == null )
        {
            dataProvider = new AbstractSortableDataProvider<IModel, String>()
            {
                public int getSize()
                {
                    return SystemRoleTable.this.getSystemRoleIds().size();
                }

                public String getId( IModel t )
                {
                    return ( (Identity) t.getObject() ).identity().get();
                }

                public IModel load( final String s )
                {
                    return new CompoundPropertyModel(
                        new LoadableDetachableModel()
                        {
                            protected Object load()
                            {
                                return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( s, SystemRoleEntityComposite.class );
                            }
                        }
                    );
                }

                public List<IModel> dataList( int first, int count )
                {
                    List<IModel> systemRoles = new ArrayList<IModel>();
                    for( final String systemRoleId : SystemRoleTable.this.dataList( first, count ) )
                    {
                        systemRoles.add(
                            new CompoundPropertyModel(
                                new LoadableDetachableModel()
                                {
                                    protected Object load()
                                    {
                                        return ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().find( systemRoleId, SystemRoleEntityComposite.class );
                                    }
                                }
                            )
                        );
                    }
                    return systemRoles;
                }
            };
        }

        return dataProvider;
    }

    public void populateItems( Item item, IModel iModel )
    {
        SystemRole systemRole = (SystemRole) iModel.getObject();
        item.add( new Label( "systemRoleName", systemRole.name().get() ) );
        item.add( new Label( "systemRoleType", systemRole.systemRoleType().get().toString() ) );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "System Role Type" );
    }

    protected List<String> dataList( int first, int count )
    {
        return getSystemRoleIds().subList( first, first + count );
    }

    public abstract List<String> getSystemRoleIds();
}
