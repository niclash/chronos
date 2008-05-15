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
package org.qi4j.chronos.ui.pricerate;

import java.util.List;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.associations.HasPriceRateSchedules;
import org.qi4j.chronos.ui.common.NameChoiceRenderer;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

public abstract class PriceRateScheduleOptionPanel extends Panel
{
//    private SubmitLink newPriceRateScheduleLink;
//    private SubmitLink customizePriceRateScheduleLink;
//    private Label priceRateScheduleNameLabel;
//    private PriceRateSchedule priceRateSchedule;
    protected SimpleDropDownChoice priceRateScheduleChoice;
    private List<PriceRateSchedule> availablePriceRateSchedules;
    private static final IChoiceRenderer nameChoiceRenderer = new NameChoiceRenderer();

    public PriceRateScheduleOptionPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
//        priceRateSchedule = null;
        availablePriceRateSchedules = PriceRateScheduleOptionPanel.this.getAvailablePriceRateSchedules();
        priceRateScheduleChoice =
            new SimpleDropDownChoice( "priceRateScheduleChoice", availablePriceRateSchedules, nameChoiceRenderer );

        add( priceRateScheduleChoice );

/*
        priceRateScheduleNameLabel = new Label( "priceRateScheduleNameLabel", "" );
        newPriceRateScheduleLink = new SubmitLink( "newPriceRateScheduleLink" )
        {
            public void onSubmit()
            {
                handleNewPriceRateSchedule();
            }
        };

        customizePriceRateScheduleLink = new SubmitLink( "customizePriceRateScheduleLink" )
        {
            public void onSubmit()
            {
                handleCustomizePriceRateSchedule();
            }
        };
        priceRateScheduleNameLabel.setVisible( false );
        customizePriceRateScheduleLink.setVisible( false );

        add( priceRateScheduleNameLabel );
        add( newPriceRateScheduleLink );
        add( customizePriceRateScheduleLink );
*/
    }

/*
    private void handleCustomizePriceRateSchedule()
    {
        // TODO kamil: fix the id
        PriceRateScheduleEditPage editPage = new PriceRateScheduleEditPage( (BasePage) this.getPage(), "id" )
        {
            public PriceRateSchedule getPriceRateSchedule()
            {
                return PriceRateScheduleOptionPanel.this.getPriceRateSchedule();
            }

            public void updatePriceRateSchedule( PriceRateSchedule priceRateScheduleComposite )
            {
                setPriceRateSchedule( priceRateScheduleComposite );
            }
        };

        setResponsePage( editPage );
    }

    public PriceRateSchedule getPriceRateSchedule()
    {
        return priceRateSchedule;
    }

    private void handleNewPriceRateSchedule()
    {
        PriceRateScheduleAddPage addPage = new PriceRateScheduleAddPage( (BasePage) this.getPage() )
        {
            public void addPriceRateSchedule( PriceRateSchedule priceRateScheduleComposite )
            {
                PriceRateScheduleOptionPanel.this.addPriceRateSchedule( priceRateScheduleComposite );
            }

            public HasPriceRateSchedules getHasPriceRateSchedules()
            {
                return PriceRateScheduleOptionPanel.this.getHasPriceRateSchedules();
            }

            public String getSubmitButtonValue()
            {
                return "Done";
            }
        };

        setResponsePage( addPage );
    }

    private void addPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        setPriceRateSchedule( priceRateSchedule );
    }
*/

    public boolean checkIfNotValidated()
    {
//        if( priceRateScheduleChoice.getModelObject() == null )
//        {
//            error( "Please create a new price Rate Schedule." );
//            return true;
//        }

        return false;
    }

/*
    public void setPriceRateSchedule( PriceRateSchedule priceRateSchedule )
    {
        this.priceRateSchedule = priceRateSchedule;
        priceRateScheduleNameLabel.setModelObject( priceRateSchedule.name().get() );
        priceRateScheduleNameLabel.setVisible( true );
        customizePriceRateScheduleLink.setVisible( true );
    }
*/

    public abstract List<PriceRateSchedule> getAvailablePriceRateSchedules();

    public abstract HasPriceRateSchedules getHasPriceRateSchedules();
}
