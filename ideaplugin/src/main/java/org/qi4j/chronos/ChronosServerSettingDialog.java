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
package org.qi4j.chronos;

import com.intellij.openapi.util.text.StringUtil;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JLabel;
import org.qi4j.chronos.common.AbstractDialog;
import org.qi4j.chronos.common.text.JMaxLengthTextField;
import org.qi4j.chronos.common.text.JNonFloatingPointTextField;
import org.qi4j.chronos.model.Account;
import org.qi4j.chronos.util.UiUtil;

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

        builder.add( new JLabel( "Port Number" ), cc.xy( 1, 3 ) );
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

    public String getIp()
    {
        return ipField.getText();
    }

    public int getPort()
    {
        return portField.getIntValue();
    }

    public String getAccountName()
    {
        return accountNameField.getText();
    }

    public void setIp( String ip )
    {
        ipField.setText( ip );
    }

    public void setPort( int port )
    {
        portField.setIntValue( port );
    }

    public void setAccountName( String accountName )
    {
        accountNameField.setText( accountName );
    }

    protected final void doOKAction()
    {
        StringBuilder builder = new StringBuilder();

        if( StringUtil.isEmptyOrSpaces( ipField.getText() ) )
        {
            builder.append( "IP field is required." );
        }

        if( StringUtil.isEmptyOrSpaces( accountNameField.getText() ) )
        {
            builder.append( "Account name field is required." );
        }

        if( builder.length() != 0 )
        {
            UiUtil.showErrorMsgDialog( "Required Fields", builder.toString() );
            return;
        }

        close( OK_EXIT_CODE );
    }
}
