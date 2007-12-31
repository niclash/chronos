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
package org.qi4j.chronos.ui.workentry;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.model.composites.WorkEntryEntityComposite;
import org.qi4j.chronos.service.FindFilter;
import org.qi4j.chronos.service.WorkEntryService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.common.AbstractSortableDataProvider;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.SimpleDeleteAction;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.util.DateUtil;

public abstract class WorkEntryTable extends ActionTable<WorkEntryEntityComposite, String>
{
    private WorkEntryDataProvider provider;

    public WorkEntryTable( String id )
    {
        super( id );

        initActions();
    }

    private void initActions()
    {
        addAction( new SimpleDeleteAction<WorkEntryEntityComposite>( "Delete" )
        {
            public void performAction( List<WorkEntryEntityComposite> workEntries )
            {
                getWorkEntryService().delete( getHasWorkEntries(), workEntries );

                info( "Selected work entries are deleted." );
            }
        } );
    }

    public AbstractSortableDataProvider<WorkEntryEntityComposite, String> getDetachableDataProvider()
    {
        if( provider == null )
        {
            provider = new WorkEntryDataProvider()
            {
                public List<WorkEntryEntityComposite> dataList( int first, int count )
                {
                    return getWorkEntryService().findAll( getHasWorkEntries(), new FindFilter( first, first + count ) );
                }

                public int getSize()
                {
                    return getWorkEntryService().countAll( getHasWorkEntries() );
                }

                public HasWorkEntries getHasWorkEntries()
                {
                    return WorkEntryTable.this.getHasWorkEntries();
                }
            };
        }

        return provider;
    }

    private WorkEntryService getWorkEntryService()
    {
        return ChronosWebApp.getServices().getWorkEntryService();
    }

    public void populateItems( Item item, WorkEntryEntityComposite obj )
    {
        final String workEntryId = obj.identity().get();

        item.add( new SimpleLink( "title", obj.getTitle() )
        {
            public void linkClicked()
            {
                WorkEntryDetailPage detailPage = new WorkEntryDetailPage( this.getPage() )
                {
                    public WorkEntryEntityComposite getWorkEntry()
                    {
                        return getWorkEntryService().get( getHasWorkEntries(), workEntryId );
                    }
                };

                setResponsePage( detailPage );
            }
        } );

        item.add( new Label( "createdDate", DateUtil.formatDateTime( obj.getCreatedDate() ) ) );
        item.add( new Label( "fromTime", DateUtil.formatDateTime( obj.getStartTime() ) ) );
        item.add( new Label( "toTime", DateUtil.formatDateTime( obj.getEndTime() ) ) );

        item.add( new SimpleLink( "editLink", "Edit" )
        {
            public void linkClicked()
            {
                WorkEntryEditPage editPage = new WorkEntryEditPage( (BasePage) this.getPage() )
                {
                    public WorkEntryEntityComposite getWorkEntry()
                    {
                        return getWorkEntryService().get( getHasWorkEntries(), workEntryId );
                    }
                };

                setResponsePage( editPage );
            }
        } );
    }

    public List<String> getTableHeaderList()
    {
        return Arrays.asList( "Title", "Created Date", "From Time", "To Time", "" );
    }

    public abstract HasWorkEntries getHasWorkEntries();
}
