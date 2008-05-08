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
package org.qi4j.chronos.ui.account.lab;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.AttributeModifier;
import java.util.List;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: kamil
 * Date: Apr 30, 2008
 * Time: 11:03:05 PM
 */
public class SelectItemsPerPageToolBar extends AbstractToolbar
{
    private final static List<Integer> ITEM_PER_PAGE_LIST = Arrays.asList( 1, 5, 10, 20, 25, 50, 100 );

    private int itemSize;

    public SelectItemsPerPageToolBar( final DataTable table )
    {
        super( table );
        itemSize = table.getRowsPerPage();

        WebMarkupContainer span = new WebMarkupContainer("span");
        add( span );
        span.add(new AttributeModifier(
            "colspan", true, new Model( String.valueOf( table.getColumns().length ) ) ) );

        final DropDownChoice iChoice =
            new DropDownChoice( "itemSizeChoice", new PropertyModel( this, "itemSize" ), ITEM_PER_PAGE_LIST );
        iChoice.add( new OnChangeAjaxBehavior()
        {
            protected void onUpdate( AjaxRequestTarget target )
            {
                table.setRowsPerPage( itemSize );
                target.addComponent( table );
            }
        });
        span.add( iChoice );
    }
    
}
