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
package org.qi4j.chronos.ui.report;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import java.util.List;
import java.util.Arrays;

public class SimplePanel extends Panel 
{
    private String title;

    private String[] headers;

    private List<String[]> entries;

    public SimplePanel( String id )
    {
        super( id );
    }

    public void initComponents()
    {
        add( new Label( "title", getTitle() ) );

        ListView headers = new ListView( "headers", Arrays.asList( getHeaders() ) )
        {
            public void populateItem( final ListItem listItem )
            {
                listItem.add( new Label( "header", listItem.getModelObjectAsString() ) );
            }
        };

        ListView dataView = new ListView( "dataView", getEntries() )
        {
            public void populateItem( final ListItem listItem )
            {
                String[] entries = (String[]) listItem.getModelObject();
                listItem.add( new ListView( "entries", Arrays.asList( entries ) )
                {
                    public void populateItem( final ListItem listItem )
                    {
                        listItem.add( new Label( "entry", listItem.getModelObjectAsString() ) );
                    }
                });
            }
        };

        add( headers );

        add( dataView );
    }

    public SimplePanel( String id, IModel model )
    {
        super( id, model );
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle( String title )
    {
        this.title = title;
    }

    public String[] getHeaders()
    {
        return headers;
    }

    public void setHeaders( String...headers )
    {
        this.headers = headers;
    }

    public List<String[]> getEntries()
    {
        return entries;
    }

    public void setEntries( List<String[]> entries )
    {
        this.entries = entries;
    }
}
