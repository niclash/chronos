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
package org.qi4j.chronos.ui.legalcondition;

import org.apache.wicket.Page;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.LegalCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LegalConditionAddPage extends LegalConditionAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( LegalConditionAddPage.class );
    private static final long serialVersionUID = 1L;

    public LegalConditionAddPage( Page goBackPage, IModel<LegalCondition> legalCondition )
    {
        super( goBackPage, legalCondition );
    }

    public String getSubmitButtonValue()
    {
        return "Add";
    }

    public String getTitleLabel()
    {
        return "Add Legal Condition";
    }

    public void onSubmitting()
    {
        /*   LegalConditionComposite legalCondition = ChronosWebApp.newInstance( LegalConditionComposite.class );

        try
        {
            assignFieldValueToLegalCondition( legalCondition );

            addLegalCondition( legalCondition );

            logInfoMsg( "Legal condition is added successfully!" );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }*/
    }

    public abstract void addLegalCondition( LegalCondition legalCondition );
}
