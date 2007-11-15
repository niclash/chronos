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

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.michaelbaranov.microba.calendar.DatePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class JDateTime extends AbstractPanel
{
    private DatePicker datePicker;

    private JComboBox hourComboBox;
    private JComboBox minuteComboBox;

    private JLabel hourLabel;
    private JLabel minuteLabel;

    public JDateTime()
    {
        init();
    }

    public JDateTime( Date date )
    {
        init();

        setDate( date );
    }

    protected String getLayoutColSpec()
    {
        return "p, 1dlu, p, 1dlu, p, 1dlu, p, 1dlu, p, 1dlu, p";
    }

    protected String getLayoutRowSpec()
    {
        return "p";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( datePicker, cc.xy( 1, 1 ) );

        builder.add( hourLabel, cc.xy( 3, 1 ) );
        builder.add( hourComboBox, cc.xy( 5, 1 ) );

        builder.add( minuteLabel, cc.xy( 7, 1 ) );
        builder.add( minuteComboBox, cc.xy( 9, 1 ) );
    }

    protected void initComponents()
    {
        datePicker = new DatePicker();

        hourComboBox = new JComboBox( getHourList() );
        minuteComboBox = new JComboBox( getMinuteList() );

        hourLabel = new JLabel( "hh" );
        minuteLabel = new JLabel( "mm" );
    }

    private Integer[] getHourList()
    {
        return generateNumberList( 24 );
    }

    private Integer[] getMinuteList()
    {
        return generateNumberList( 60 );
    }

    private Integer[] generateNumberList( int max )
    {
        List<Integer> resultList = new ArrayList<Integer>();

        for( int i = 0; i < max; i++ )
        {
            resultList.add( new Integer( i ) );
        }

        return resultList.toArray( new Integer[resultList.size()] );
    }

    public void setDate( Date date )
    {
        try
        {
            Calendar calendar = Calendar.getInstance();

            calendar.setTime( date );

            datePicker.setDate( date );

            hourComboBox.setSelectedItem( calendar.get( Calendar.HOUR_OF_DAY ) );
            minuteComboBox.setSelectedItem( calendar.get( Calendar.MINUTE ) );
        }
        catch( Exception err )
        {
            err.printStackTrace();
        }
    }

    public Date getDate()
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( datePicker.getDate() );

        calendar.set( Calendar.HOUR_OF_DAY, (Integer) hourComboBox.getSelectedItem() );
        calendar.set( Calendar.MINUTE, (Integer) minuteComboBox.getSelectedItem() );

        return calendar.getTime();
    }
}
