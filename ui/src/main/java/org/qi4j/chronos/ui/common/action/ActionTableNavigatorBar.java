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
package org.qi4j.chronos.ui.common.action;

import java.util.Arrays;
import java.util.List;
import org.apache.wicket.Page;
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

public class ActionTableNavigatorBar extends Panel
{
    private static final long serialVersionUID = 1L;

    private final static List<String> ITEM_PER_PAGE_LIST = Arrays.asList( "5", "10", "20", "25", "50", "100", "150", "200",
                                                                          "300", "500" );
    private static final String WICKET_ID_FIRST = "first";
    private static final String WICKET_ID_PREV = "prev";
    private static final String WICKET_ID_NEXT = "next";
    private static final String WICKET_ID_LAST = "last";
    private static final String WICKET_ID_DETAIL_LINK = "detailLink";
    private static final String WICKET_ID_ITEM_SIZE_CHOICE = "itemSizeChoice";

    private String itemPerPageSize = "10";

    public ActionTableNavigatorBar( final String id, final DataView dataView )
    {
        super( id );

        dataView.setItemsPerPage( Integer.valueOf( itemPerPageSize ) );

        Link firstLink = newNavigationLink( WICKET_ID_FIRST, dataView, 0, WICKET_ID_FIRST );
        Link prevLink = newIncrementLink( WICKET_ID_PREV, dataView, -1, "previous" );
        Link nextLink = newIncrementLink( WICKET_ID_NEXT, dataView, 1, WICKET_ID_NEXT );
        Link lastLink = newNavigationLink( WICKET_ID_LAST, dataView, -1, WICKET_ID_LAST );

        add( new DetailLine( WICKET_ID_DETAIL_LINK, dataView ) );

        DropDownChoice itemPerPageChoice = newItemPerPageDropDownChoice( dataView );

        add( itemPerPageChoice );

        add( firstLink );
        add( prevLink );
        add( nextLink );
        add( lastLink );
    }

    @SuppressWarnings( "unchecked" )
    private DropDownChoice newItemPerPageDropDownChoice( final DataView dataView )
    {
        DropDownChoice itemPerPageChoice = new DropDownChoice( WICKET_ID_ITEM_SIZE_CHOICE,
                                                               new PropertyModel( this, "itemPerPageSize" ), ITEM_PER_PAGE_LIST );

        itemPerPageChoice.add( new AjaxFormComponentUpdatingBehavior( "onchange" )
        {
            private static final long serialVersionUID = 1L;

            protected void onUpdate( AjaxRequestTarget target )
            {
                handleItemPerPageChanged( dataView );
            }
        } );

        return itemPerPageChoice;
    }

    private void handleItemPerPageChanged( final DataView dataView )
    {
        beforeItemPerPageChanged();

        dataView.setItemsPerPage( Integer.parseInt( itemPerPageSize ) );

        Page page = getPage();

        setResponsePage( page );
    }

    private class DetailLine extends WebComponent
    {
        private static final long serialVersionUID = 1L;

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

    private Link newIncrementLink( String id, IPageable page, int increment, final String imageName )
    {
        final Link link = new PagingNavigationIncrementLink( id, page, increment )
        {
            private static final long serialVersionUID = 1L;

            public void onClick()
            {
                beforeNextNagivation();

                super.onClick();
            }
        };

        addImage( link, imageName );

        return link;
    }

    private Link newNavigationLink( String id, IPageable page, int pageNum, final String imageName )
    {
        final Link link = new PagingNavigationLink( id, page, pageNum )
        {
            private static final long serialVersionUID = 1L;

            public void onClick()
            {
                beforeNextNagivation();
                super.onClick();
            }
        };

        addImage( link, imageName );

        return link;
    }

    private void addImage( final Link link, final String imageName )
    {
        link.add( new Image( "image" )
        {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onComponentTag( ComponentTag tag )
            {
                super.onComponentTag( tag );

                String imageUrl = link.isEnabled() ? imageName + ".gif" : imageName + "_off.gif";

                tag.put( "src", "images/" + imageUrl );
            }
        } );
    }

    public final String getItemPerPageSize()
    {
        return itemPerPageSize;
    }

    final void setItemPerPageSize( String itemPerPageSize )
    {
        this.itemPerPageSize = itemPerPageSize;
    }

    void beforeNextNagivation()
    {
    }

    void beforeItemPerPageChanged()
    {
    }
}
