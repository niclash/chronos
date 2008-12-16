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

import java.util.List;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.ui.common.SimpleLink;
import org.qi4j.chronos.ui.common.action.ActionTable;
import org.qi4j.chronos.ui.common.action.DeleteAction;
import org.qi4j.chronos.ui.wicket.bootstrap.ChronosUnitOfWorkManager;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.api.unitofwork.UnitOfWorkCompletionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class WorkEntryTable extends ActionTable<WorkEntry>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( WorkEntryTable.class );
    
    private static final String DELETE_ACTION = "deleteAction";
    private static final String DELETE_SUCCESS = "deleteSuccessful";
    private static final String DELETE_FAIL = "deleteFailed";

    private static final long serialVersionUID = 1L;

    private final static String[] COLUMN_NAMES = { "Title", "Created Date", "From Time", "To Time", "" };

    private static final String WICKET_ID_CREATED_DATE = "createdDate";
    private static final String WICKET_ID_FROM_TIME = "fromTime";
    private static final String WICKET_ID_TO_TIME = "toTime";
    private static final String WICKET_ID_EDIT_LINK = "editLink";

    public WorkEntryTable( String id, IModel<? extends HasWorkEntries> hasWorkEntries, WorkEntryDataProvider dataProvider )
    {
        super( id, hasWorkEntries, dataProvider, COLUMN_NAMES );

        initActions();
    }

    private void initActions()
    {
        addAction( new DeleteAction<WorkEntry>( getString( DELETE_ACTION ) )
        {
            private static final long serialVersionUID = 1L;

            public void performAction( List<WorkEntry> workEntries )
            {
                handleDeleteAction( workEntries );
                info( getString( DELETE_SUCCESS ) );
            }
        }
        );
    }

    private void handleDeleteAction( List<WorkEntry> workEntries )
    {
        try
        {
            HasWorkEntries hasWorkEntries = (HasWorkEntries) getDefaultModelObject();

            for( WorkEntry workEntry : workEntries )
            {
                hasWorkEntries.workEntries().remove( workEntry );
                ChronosUnitOfWorkManager.get().getCurrentUnitOfWork().remove( workEntry );
            }
            ChronosUnitOfWorkManager.get().completeCurrentUnitOfWork();
        }
        catch( UnitOfWorkCompletionException uowce )
        {
            ChronosUnitOfWorkManager.get().discardCurrentUnitOfWork();

            error( getString( DELETE_FAIL ) );
            LOGGER.error( uowce.getLocalizedMessage(), uowce );
        }
    }


    public void populateItems( final Item<WorkEntry> item )
    {
        final WorkEntry workEntry = item.getModelObject();

        item.add( new SimpleLink( "title", workEntry.title().get() )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                WorkEntryDetailPage detailPage = new WorkEntryDetailPage( this.getPage(), item.getModel() );
                setResponsePage( detailPage );
            }
        }
        );
        item.add( new Label( WICKET_ID_CREATED_DATE, DateUtil.formatDateTime( workEntry.createdDate().get() ) ) );
        item.add( new Label( WICKET_ID_FROM_TIME, DateUtil.formatDateTime( workEntry.startTime().get() ) ) );
        item.add( new Label( WICKET_ID_TO_TIME, DateUtil.formatDateTime( workEntry.endTime().get() ) ) );
        
        item.add( new SimpleLink( WICKET_ID_EDIT_LINK, "Edit" )
        {
            private static final long serialVersionUID = 1L;

            public void linkClicked()
            {
                //TODO
//                    WorkEntryEditPage editPage = new WorkEntryEditPage( (BasePage) this.getPage(), workEntryId );
//                    setResponsePage( editPage );
            }
        }
        );
    }
}
