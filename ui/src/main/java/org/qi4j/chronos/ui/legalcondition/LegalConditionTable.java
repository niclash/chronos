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
package org.qi4j.chronos.ui.legalcondition;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;

public abstract class LegalConditionTable extends ActionTable<LegalCondition, String>
{
//    private LegalConditionDataProvider provider;

    public LegalConditionTable( String id )
    {
        super( id );

        addActions();
    }

    private void addActions()
    {
        addAction( new SimpleDeleteAction<LegalCondition>( "Delete" )
        {
            public void performAction( List<LegalCondition> legalConditions )
            {
                // TODO kamil: migrate
//                getLegalConditionService().deleteLegalCondition( getProject(), legalConditions );

                info( "Selected legal condition(s) are deleted." );
            }
        } );
    }

    protected void authorizatiingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    public AbstractSortableDataProvider<LegalCondition, String> getDetachableDataProvider()
    {
/*
        if( provider == null )
        {
            provider = new LegalConditionDataProvider()
            {
                public Project getProject()
                {
                    return LegalConditionTable.this.getProject();
                }
            };
        }

        return provider;
*/
        return null;
    }

    public void populateItems( Item item, LegalCondition obj )
    {
        final String legalConditionName = obj.name().get();

        item.add( new SimpleLink( "name", obj.name().get() )
        {
            public void linkClicked()
            {
                LegalConditionDetailPage detailPage = new LegalConditionDetailPage( this.getPage() )
                {
                    public LegalCondition getLegalCondition()
                    {
                        // TODO kamil: migrate
//                        return getServices().getLegalConditionService().get( getProject(), legalConditionName );
                        return LegalConditionTable.this.getLegalCondition( legalConditionName );
                    }
                };

                setResponsePage( detailPage );
            }
        } );

        SimpleLink simpleLink = createEditLink( legalConditionName );

        item.add( simpleLink );
    }

    private SimpleLink createEditLink( final String legalConditionName )
    {
        return new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                //TODO
//                LegalConditionEditPage editPage = new LegalConditionEditPage( this.getPage() )
//                {
//                    public LegalCondition getLegalCondition()
//                    {
//                        // TODO kamil: migrate
////                        return getLegalConditionService().get( getProject(), legalConditionName );
//                        return LegalConditionTable.this.getLegalCondition( legalConditionName );
//                    }
//
//                    public HasLegalConditions getHasLegalConditions()
//                    {
//                        return LegalConditionTable.this.getProject();
//                    }
//                };
//
//                setResponsePage( editPage );
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }

    private LegalCondition getLegalCondition( String legalConditionName )
    {
        for( LegalCondition legalCondition : getProject().legalConditions() )
        {
            if( legalConditionName.equals( legalCondition.name().get() ) )
            {
                return legalCondition;
            }
        }

        return null;
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "" );
    }

    public abstract Project getProject();
}
