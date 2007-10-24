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
import java.util.Iterator;
import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public abstract class LegalConditionTable extends ActionTable<LegalConditionComposite, String>
{
    private LegalConditionDataProvider provider;

    public LegalConditionTable( String id )
    {
        super( id );
    }

    protected void authorizatiingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    public AbstractSortableDataProvider<LegalConditionComposite, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new LegalConditionDataProvider()
            {
                public ProjectEntityComposite getProject()
                {
                    return LegalConditionTable.this.getProject();
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, LegalConditionComposite obj )
    {
        final String legalConditionName = obj.getName();

        item.add( new SimpleLink( "name", obj.getName() )
        {
            public void linkClicked()
            {
                LegalConditionDetailPage detailPage = new LegalConditionDetailPage( this.getPage() )
                {
                    public LegalConditionComposite getLegalCondition()
                    {
                        return getServices().getLegalConditionService().get( getProject(), legalConditionName );
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
                LegalConditionEditPage editPage = new LegalConditionEditPage( this.getPage() )
                {
                    public LegalConditionComposite getLegalCondition()
                    {
                        return getServices().getLegalConditionService().get( getProject(), legalConditionName );
                    }

                    public void updateLegalCondition( LegalConditionComposite legalCondition )
                    {
                        LegalConditionTable.this.updateLegalCondition( legalCondition, legalConditionName );
                    }
                };

                setResponsePage( editPage );
            }

            protected void authorizingLink( Link link )
            {
                MetaDataRoleAuthorizationStrategy.authorize( link, ENABLE, SystemRole.ACCOUNT_ADMIN );
            }
        };
    }

    private void updateLegalCondition( LegalConditionComposite legalCondition, String oldLegalConditionName )
    {
        //TODO bp. workaround, wait for ValueObjectComposite
        ProjectEntityComposite project = getProject();

        Iterator<LegalConditionComposite> legalConditionIter = project.legalConditionIterator();

        LegalConditionComposite toDelete = null;

        while( legalConditionIter.hasNext() )
        {
            LegalConditionComposite temp = legalConditionIter.next();

            if( temp.getName().equals( oldLegalConditionName ) )
            {
                toDelete = temp;
                break;
            }
        }

        if( toDelete != null )
        {
            project.removeLegalCondition( toDelete );
        }

        project.addLegalCondition( legalCondition );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "" );
    }

    public abstract ProjectEntityComposite getProject();
}
