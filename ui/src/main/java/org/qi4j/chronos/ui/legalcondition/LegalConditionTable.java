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

import java.util.List;
import org.apache.wicket.Component;
import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.LegalCondition;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.associations.HasLegalConditions;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;

public final class LegalConditionTable extends ActionTable<LegalCondition>
{
    private static final long serialVersionUID = 1L;

    private final static String[] COLUMN_NAMES = { "Name", "" };

    public LegalConditionTable( String id, IModel<? extends HasLegalConditions> hasLegalConditions, LegalConditionDataProvider dataProvider )
    {
        super( id, hasLegalConditions, dataProvider, COLUMN_NAMES );

        addActions();
    }

    private void addActions()
    {
        addAction( new DeleteAction<LegalCondition>( "Delete" )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<LegalCondition> legalConditions )
            {
                // TODO kamil: migrate
//                getLegalConditionService().deleteLegalCondition( getProject(), legalConditions );

                info( "Selected legal condition(s) are deleted." );
            }
        } );
    }

    protected void authorizingActionBar( Component component )
    {
        MetaDataRoleAuthorizationStrategy.authorize( component, RENDER, SystemRole.ACCOUNT_ADMIN );
    }

    public void populateItems( final Item<LegalCondition> item )
    {
        String legalConditionName = item.getModelObject().name().get();

        item.add( new SimpleLink( "name", legalConditionName )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                LegalConditionDetailPage detailPage = new LegalConditionDetailPage( this.getPage(), item.getModel() );

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
            private static final long serialVersionUID = 1L;

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
        HasLegalConditions hasLegalConditions = (HasLegalConditions) getDefaultModelObject();

        for( LegalCondition legalCondition : hasLegalConditions.legalConditions() )
        {
            if( legalConditionName.equals( legalCondition.name().get() ) )
            {
                return legalCondition;
            }
        }

        return null;
    }
}
