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
package org.qi4j.chronos.common;

import javax.swing.JOptionPane;
import org.qi4j.chronos.service.Services;
import org.qi4j.chronos.setting.ChronosSetting;
import org.qi4j.chronos.util.ChronosUtil;
import org.qi4j.library.framework.validation.ValidationException;

public abstract class AddEditDialog extends AbstractDialog
{
    public AddEditDialog()
    {
        super( false );

        setOKButtonText( getOkButtonText() );
    }

    protected final void doOKAction()
    {
        try
        {
            handleOkClicked();

            close( OK_EXIT_CODE );
        }
        catch( ValidationException err )
        {
            JOptionPane.showMessageDialog( null, err.getLocalizedMessage(), "Validation Failure", JOptionPane.WARNING_MESSAGE );
        }
        catch( Exception err )
        {
            JOptionPane.showMessageDialog( null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    protected Services getServices()
    {
        return getChronosSetting().getServices();
    }

    protected ChronosSetting getChronosSetting()
    {
        return ChronosUtil.getChronosSetting();
    }

    public abstract String getOkButtonText();

    public abstract void handleOkClicked();
}
