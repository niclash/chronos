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
import org.qi4j.chronos.common.text.JMaxLengthPasswordField;
import org.qi4j.chronos.common.text.JMaxLengthTextField;
import org.qi4j.chronos.model.Login;
import org.qi4j.chronos.util.UiUtil;

public class UserPassSettingDialog extends AbstractDialog
{
    private JMaxLengthTextField loginIdField;
    private JMaxLengthPasswordField passwordField;

    public UserPassSettingDialog()
    {
        super( false );
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 100dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, p";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( new JLabel( "Login Id" ), cc.xy( 1, 1 ) );
        builder.add( loginIdField, cc.xy( 3, 1 ) );

        builder.add( new JLabel( "Password" ), cc.xy( 1, 3 ) );
        builder.add( passwordField, cc.xy( 3, 3 ) );
    }

    protected String getDialogTitle()
    {
        return "User Login";
    }

    protected void initComponents()
    {
        loginIdField = new JMaxLengthTextField( 15 );
        passwordField = new JMaxLengthPasswordField( Login.PASSWORD_LEN );
    }

    public void setLoginId( String loginId )
    {
        loginIdField.setText( loginId );
    }

    public void setPassword( String password )
    {
        passwordField.setText( password );
    }

    public String getLoginId()
    {
        return loginIdField.getText();
    }

    public String getPassword()
    {
        return new String( passwordField.getPassword() );
    }

    protected final void doOKAction()
    {
        StringBuilder errMessages = new StringBuilder();

        if( StringUtil.isEmptyOrSpaces( loginIdField.getText() ) )
        {
            errMessages.append( "Login Id is required." );
            errMessages.append( "\n" );
        }

        if( passwordField.getPassword().length == 0 )
        {
            errMessages.append( "Password is required." );
            errMessages.append( "\n" );
        }

        if( errMessages.length() != 0 )
        {
            UiUtil.showErrorMsgDialog( "Required Fields", errMessages.toString() );
            return;
        }

        close( OK_EXIT_CODE );
    }
}
