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
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.composites.LegalConditionEntityComposite;
import org.qi4j.chronos.service.EntityService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;

public class LegalConditionTable extends ActionTable<LegalConditionEntityComposite>
{
    private SimpleDataProvider<LegalConditionEntityComposite> provider;

    public LegalConditionTable( String id )
    {
        super( id );
    }

    public AbstractSortableDataProvider<LegalConditionEntityComposite> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new SimpleDataProvider<LegalConditionEntityComposite>()
            {
                public EntityService<LegalConditionEntityComposite> getEntityService()
                {
                    return ChronosWebApp.getServices().getLegalConditionService();
                }
            };
        }

        return provider;
    }

    public void populateItems( Item item, LegalConditionEntityComposite obj )
    {
        final String legalConditionId = obj.getIdentity();

        add( new SimpleLink( "name", obj.getLegalConditionName() )
        {
            public void linkClicked()
            {
                LegalConditionDetailPage detailPage =
                    new LegalConditionDetailPage( (BasePage) this.getPage(), legalConditionId );

                setResponsePage( detailPage );
            }
        } );

        add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                LegalConditionEditPage editPage =
                    new LegalConditionEditPage( (BasePage) this.getPage(), legalConditionId );

                setResponsePage( editPage );
            }
        } );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Name", "" );
    }
}
