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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;

public class OptionalDropDownChoice extends DropDownChoice
{
    private final static String NONE = "-- None --";

    private Object internalChoice;

    public OptionalDropDownChoice( String id, List<? extends Serializable> optionList )
    {
        super( id );

        this.setModel( new PropertyModel( this, "internalChoice" ) );

        setNewChoices( optionList );
    }

    public Object getChoice()
    {
        return isNoneChoice() ? null : internalChoice;
    }

    public String getChoiceAsString()
    {
        return isNoneChoice() ? null : internalChoice.toString();
    }

    private boolean isNoneChoice()
    {
        return internalChoice.equals( NONE );
    }

    public void setChoice( Object choice )
    {
        this.internalChoice = choice;
    }

    public void setNewChoices( List<? extends Serializable> list )
    {
        List<Object> tempList = new ArrayList<Object>( list );

        tempList.add( 0, NONE );

        setChoices( tempList );

        internalChoice = NONE;
    }

}

