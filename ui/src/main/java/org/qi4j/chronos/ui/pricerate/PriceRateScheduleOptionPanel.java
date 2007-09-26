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

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.chronos.model.composites.PriceRateScheduleComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.service.PriceRateScheduleService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

public abstract class PriceRateScheduleOptionPanel extends Panel
{
    private SimpleDropDownChoice<String> priceRateScheduleChoice;

    private SubmitLink newPriceRateScheduleLink;
    private SubmitLink customizePriceRateScheduleLink;
    private SubmitLink viewPriceRateScheduleLink;

    private List<String> priceRateScheduleList;

    private static List<PriceRateScheduleComposite> addedPriceRateScheduleList;

    public PriceRateScheduleOptionPanel( String id )
    {
        super( id );

        addedPriceRateScheduleList = new ArrayList<PriceRateScheduleComposite>();

        initComponents();
    }

    private void initComponents()
    {
        initPriceRateScheduleList();

        priceRateScheduleChoice = new SimpleDropDownChoice<String>( "priceRateScheduleChoice", priceRateScheduleList, true );
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

        viewPriceRateScheduleLink = new SubmitLink( "viewPriceRateScheduleLink" )
        {
            public void onSubmit()
            {
                handleViewPriceRateSchedule();
            }
        };

        if( priceRateScheduleList.size() == 0 )
        {
            setControlVisible( false );
        }

        add( priceRateScheduleChoice );
        add( newPriceRateScheduleLink );
        add( customizePriceRateScheduleLink );
        add( viewPriceRateScheduleLink );
    }

    private void handleViewPriceRateSchedule()
    {
        PriceRateScheduleDetailPage detailPage = new PriceRateScheduleDetailPage( (BasePage) this.getPage() )
        {
            public PriceRateSchedule getPriceRateSchedule()
            {
                return PriceRateScheduleOptionPanel.this.getSelectedPriceRateSchedule();
            }
        };

        setResponsePage( detailPage );
    }

    private void handleCustomizePriceRateSchedule()
    {

        PriceRateScheduleEditPage editPage = new PriceRateScheduleEditPage( (BasePage) this.getPage() )
        {
            public PriceRateScheduleComposite getPriceRateSchedule()
            {
                return PriceRateScheduleOptionPanel.this.getSelectedPriceRateSchedule();
            }

            public AccountEntityComposite getAccount()
            {
                return PriceRateScheduleOptionPanel.this.getAccount();
            }
        };

        setResponsePage( editPage );
    }

    private PriceRateScheduleComposite getSelectedPriceRateSchedule()
    {
        String priceRateScheduleName = priceRateScheduleChoice.getChoice();

        for( PriceRateScheduleComposite priceRateSchedule : addedPriceRateScheduleList )
        {
            if( priceRateSchedule.getName().equals( priceRateScheduleName ) )
            {
                return priceRateSchedule;
            }
        }

        return null;
    }

    private void handleNewPriceRateSchedule()
    {
        PriceRateScheduleAddPage addPage = new PriceRateScheduleAddPage( (BasePage) this.getPage() )
        {
            public void addPriceRateSchedule( PriceRateScheduleComposite priceRateScheduleComposite )
            {
                PriceRateScheduleOptionPanel.this.addPriceRateSchedule( priceRateScheduleComposite );
            }

            public AccountEntityComposite getAccount()
            {
                return PriceRateScheduleOptionPanel.this.getAccount();
            }
        };

        setResponsePage( addPage );
    }

    private void addPriceRateSchedule( PriceRateScheduleComposite priceRateSchedule )
    {
        addedPriceRateScheduleList.add( priceRateSchedule );
        priceRateScheduleList.add( priceRateSchedule.getName() );

        //set newly added relationship as default value
        priceRateScheduleChoice.setChoice( priceRateSchedule.getName() );

        setControlVisible( true );
    }

    private void setControlVisible( boolean isVisible )
    {
        priceRateScheduleChoice.setVisible( isVisible );
        viewPriceRateScheduleLink.setVisible( isVisible );
        customizePriceRateScheduleLink.setVisible( isVisible );
    }

    private void initPriceRateScheduleList()
    {
        PriceRateScheduleService service = ChronosWebApp.getServices().getPriceRateScheduleService();

        List<PriceRateScheduleComposite> priceRateSchedules = service.findAll( getProject() );

        priceRateScheduleList = new ArrayList<String>();

        for( PriceRateScheduleComposite priceRateSchedule : priceRateSchedules )
        {
            priceRateScheduleList.add( priceRateSchedule.getName() );
        }
    }

    public abstract ProjectEntityComposite getProject();

    public abstract AccountEntityComposite getAccount();
}
