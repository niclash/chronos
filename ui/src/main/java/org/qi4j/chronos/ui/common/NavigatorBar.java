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
package org.qi4j.chronos.ui.common;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Page;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationIncrementLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.PropertyModel;

public class NavigatorBar extends Panel
{
    private final static List<String> ITEM_PER_PAGE_LIST = Arrays.asList( "5", "10", "20", "25", "50", "100", "150", "200",
                                                                          "300", "500" );

    private String itemSize = "10";

    public NavigatorBar( final String id, final DataView dataView )
    {
        super( id );

        dataView.setItemsPerPage( Integer.valueOf( itemSize ) );

        Link firstLink = newNavigationLink( "first", dataView, 0, "first" );
        Link prevLink = newIncrementLink( "prev", dataView, -1, "previous" );
        Link nextLink = newIncrementLink( "next", dataView, 1, "next" );
        Link lastLink = newNavigationLink( "last", dataView, -1, "last" );

        add( new DetailLine( "detailLink", dataView ) );

        DropDownChoice itemPerPageChoice = new DropDownChoice( "itemSizeChoice", new PropertyModel( this, "itemSize" ), ITEM_PER_PAGE_LIST );

        itemPerPageChoice.add( new AjaxFormComponentUpdatingBehavior( "onchange" )
        {
            protected void onUpdate( AjaxRequestTarget target )
            {
                dataView.setItemsPerPage( Integer.parseInt( itemSize ) );

                Page page = NavigatorBar.this.getPage();

                setResponsePage( page );
            }
        } );

        add( itemPerPageChoice );

        add( firstLink );
        add( prevLink );
        add( nextLink );
        add( lastLink );
    }

    private class DetailLine extends WebComponent
    {
        private DataView dataView;

        public DetailLine( String id, DataView dataView )
        {
            super( id );

            this.dataView = dataView;
        }

        protected void onComponentTagBody( final MarkupStream markupStream,
                                           final ComponentTag openTag )
        {
            String text = getHeadlineText();
            replaceComponentTagBody( markupStream, openTag, text );
        }

        public String getHeadlineText()
        {
            final int firstListItem = dataView.getCurrentPage() * dataView.getItemsPerPage();

            final StringBuilder buf = new StringBuilder();

            buf.append( "Showing : " )
                .append( String.valueOf( firstListItem + 1 ) )
                .append( " - " )
                .append( String.valueOf( Math.min( dataView.getRowCount(),
                                                   firstListItem + Math.min( dataView.getItemsPerPage(), dataView.getRowCount() ) ) ) )
                .append( " of " )
                .append( String.valueOf( dataView.getRowCount() ) );

            return buf.toString();
        }
    }

    protected Link newIncrementLink( String id, IPageable page, int increment, final String imageName )
    {
        final Link link = new PagingNavigationIncrementLink( id, page, increment );

        addImage( link, imageName );

        return link;
    }

    protected Link newNavigationLink( String id, IPageable page, int pageNum, final String imageName )
    {
        final Link link = new PagingNavigationLink( id, page, pageNum );

        addImage( link, imageName );

        return link;
    }

    private void addImage( final Link link, final String imageName )
    {
        link.add( new Image( "image", new ResourceReference( NavigatorBar.this.getClass(), imageName + ".gif" ) )
        {
            @Override
            protected void onComponentTag( ComponentTag tag )
            {
                String imageUrl = link.isEnabled() ? imageName + ".gif" : imageName + "_off.gif";

                setImageResourceReference( new ResourceReference( NavigatorBar.this.getClass(), imageUrl ) );

                super.onComponentTag( tag );
            }
        } );
    }

    public String getItemSize()
    {
        return itemSize;
    }

    public void setItemSize( String itemSize )
    {
        this.itemSize = itemSize;
    }
}
