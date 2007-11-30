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
package org.qi4j.chronos.setting;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JLabel;
import org.qi4j.chronos.common.AbstractDialog;
import org.qi4j.chronos.common.text.JMaxLengthTextField;
import org.qi4j.chronos.common.text.JNonFloatingPointTextField;
import org.qi4j.chronos.model.Account;

public class ChronosServerSettingDialog extends AbstractDialog
{
    private JMaxLengthTextField ipField;
    private JNonFloatingPointTextField portField;
    private JMaxLengthTextField accountNameField;

    public ChronosServerSettingDialog()
    {
        super( false );
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 100dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, p,3dlu,p";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( new JLabel( "IP Address" ), cc.xy( 1, 1 ) );
        builder.add( ipField, cc.xy( 3, 1 ) );

        builder.add( new JLabel( "Port No." ), cc.xy( 1, 3 ) );
        builder.add( portField, cc.xy( 3, 3 ) );

        builder.add( new JLabel( "Account" ), cc.xy( 1, 5 ) );
        builder.add( accountNameField, cc.xy( 3, 5 ) );
    }

    protected String getDialogTitle()
    {
        return "Chronos Server Setting";
    }

    protected void initComponents()
    {
        ipField = new JMaxLengthTextField( 15 );
        portField = new JNonFloatingPointTextField( false );
        accountNameField = new JMaxLengthTextField( Account.NAME_LEN );
    }

    public void setIp( String ip )
    {
        ipField.setText( ip );
    }

    public String getIp()
    {
        return ipField.getText();
    }

    public void setPort( int port )
    {
        portField.setIntValue( port );
    }

    public int getPort()
    {
        return portField.getIntValue();
    }

    public String getAccountName()
    {
        return accountNameField.getText();
    }

    public void setAccoutName( String accountName )
    {
        accountNameField.setText( accountName );
    }

    protected final void doOKAction()
    {


        close( OK_EXIT_CODE );
    }
}
