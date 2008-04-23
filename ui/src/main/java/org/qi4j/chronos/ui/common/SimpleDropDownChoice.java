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

import java.util.List;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;

public class SimpleDropDownChoice<T> extends DropDownChoice
{
    private static final long serialVersionUID = 1L;

    private T choice;
    private boolean setDefaultValue;

    public SimpleDropDownChoice( String id, List<T> optionList, boolean isSetDefaultValue )
    {
        super( id );

        setDefaultValue = isSetDefaultValue;
        this.setModel( new PropertyModel( this, "choice" ) );

        setNewChoices( optionList );
    }

    private void update( List<T> optionList )
    {
        choice = null;

        boolean hasData = optionList.size() != 0;

        if( hasData && setDefaultValue )
        {
            choice = optionList.get( 0 );
        }

        this.setEnabled( hasData );
    }

    public T getChoice()
    {
        return choice;
    }

    public String getChoiceAsString()
    {
        return choice.toString();
    }

    public void setChoice( T choice )
    {
        this.choice = choice;
    }

    public void setNewChoices( List<T> list )
    {
        super.setChoices( list );

        update( list );
    }
}

