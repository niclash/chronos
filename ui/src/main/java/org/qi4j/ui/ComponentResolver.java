/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.ui;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import org.qi4j.api.Composite;
import org.qi4j.api.CompositeFactory;
import org.qi4j.api.annotation.Dependency;
import org.qi4j.api.annotation.Modifies;
import org.qi4j.api.annotation.Uses;
import org.qi4j.api.model.CompositeModel;
import org.qi4j.ui.annotation.Button;
import org.qi4j.ui.annotation.CheckBox;
import org.qi4j.ui.annotation.EditPanel;
import org.qi4j.ui.annotation.Form;
import org.qi4j.ui.annotation.Label;
import org.qi4j.ui.annotation.ModelledBy;
import org.qi4j.ui.annotation.Page;
import org.qi4j.ui.annotation.Radio;
import org.qi4j.ui.annotation.TextArea;
import org.qi4j.ui.annotation.TextField;
import org.qi4j.ui.association.HasComponents;
import org.qi4j.ui.component.Component;
import org.qi4j.ui.component.ComponentLifecycle;
import org.qi4j.ui.component.composites.ButtonComposite;
import org.qi4j.ui.component.composites.CheckBoxComposite;
import org.qi4j.ui.component.composites.EditPanelComposite;
import org.qi4j.ui.component.composites.FormComposite;
import org.qi4j.ui.component.composites.LabelComposite;
import org.qi4j.ui.component.composites.PageComposite;
import org.qi4j.ui.component.composites.RadioComposite;
import org.qi4j.ui.component.composites.TextAreaComposite;
import org.qi4j.ui.component.composites.TextFieldComposite;
import org.qi4j.ui.model.Model;
import org.qi4j.ui.model.ModelComposite;

public final class ComponentResolver implements ComponentLifecycle
{
    @Dependency private CompositeFactory factory;
    @Modifies private ComponentLifecycle next;
    @Uses private Component component;
    @Uses private HasComponents hasComponents;

    public void init() throws InitFailedException
    {
        CompositeModel compositeModel = component.getCompositeModel();
        Class<? extends Composite> compositeClass = compositeModel.getCompositeClass();

        ModelledBy modelledBy = compositeClass.getAnnotation( ModelledBy.class );
        if( modelledBy != null )
        {
            setupComponents( modelledBy.value() );
        }

        next.init();
    }

    public void dispose()
    {
        next.dispose();
    }

    public void render( Response response ) throws RenderFailedException
    {
        next.render( response );
    }

    private void setupComponents( Class[] classes )
    {
        for( Class aClass : classes )
        {
            setupComponents( aClass );
        }
    }

    private void setupComponents( Class aClass )
    {
        Method[] methods = aClass.getMethods();
        for( Method method : methods )
        {
            Annotation[] annotations = method.getAnnotations();

            for( Annotation annotation : annotations )
            {
                resolveComponent( annotation, method );
            }
        }
    }

    private void resolveComponent( Annotation annotation, Method method )
    {
        Class<? extends Annotation> annotationType = annotation.annotationType();

        if( Label.class.isAssignableFrom( annotationType ) )
        {
            setupLabel( method );
        }
        else if( TextField.class.isAssignableFrom( annotationType ) )
        {
            setupTextField( method );
        }
        else if( TextArea.class.isAssignableFrom( annotationType ) )
        {
            setupTextArea( method );
        }
        else if( Button.class.isAssignableFrom( annotationType ) )
        {
            setupButton( method );
        }
        else if( CheckBox.class.isAssignableFrom( annotationType ) )
        {
            setupCheckBox( method );
        }
        else if( Radio.class.isAssignableFrom( annotationType ) )
        {
            setupRadio( method );
        }
        else if( Form.class.isAssignableFrom( annotationType ) )
        {
            setupForm( method );
        }
        else if( EditPanel.class.isAssignableFrom( annotationType ) )
        {
            setupPanel( method );
        }
        else if( Page.class.isAssignableFrom( annotationType ) )
        {
            setupPage( method );
        }
    }

    private void setupRadio( Method method )
    {
        RadioComposite radioComposite = factory.newInstance( RadioComposite.class );
        Radio radio = method.getAnnotation( Radio.class );
        radioComposite.setName( radio.name() );
        hasComponents.addComponent( radioComposite );
    }

    private void setupTextArea( Method method )
    {
        TextAreaComposite textAreaComposite = factory.newInstance( TextAreaComposite.class );
        TextArea textArea = method.getAnnotation( TextArea.class );
        textAreaComposite.setName( textArea.name() );
        textAreaComposite.setColumnCount( textArea.cols() );
        textAreaComposite.setRowCount( textArea.rows() );

        Model containerModel = component.getModel();
        Model model = factory.newInstance( ModelComposite.class );
        model.setObject( containerModel.getObject() );
        model.setFieldName( method.getName() );
        textAreaComposite.setModel( model );

        hasComponents.addComponent( textAreaComposite );
    }

    private void setupCheckBox( Method method )
    {
        CheckBoxComposite checkBoxComposite = factory.newInstance( CheckBoxComposite.class );
        CheckBox checkBox = method.getAnnotation( CheckBox.class );
        checkBoxComposite.setEnabled( checkBox.enabled() );
        hasComponents.addComponent( checkBoxComposite );
    }

    private void setupButton( Method method )
    {
        ButtonComposite buttonComposite = factory.newInstance( ButtonComposite.class );
        Button button = method.getAnnotation( Button.class );
        buttonComposite.setButtonType( button.type() );
        buttonComposite.setEnabled( button.enabled() );
        buttonComposite.setValue( button.value() );
        hasComponents.addComponent( buttonComposite );
    }

    private void setupForm( Method method )
    {
        Form form = method.getAnnotation( Form.class );
        Class<? extends FormComposite> formClass = form.type();
        FormComposite formComposite = factory.newInstance( formClass );
        formComposite.setModel( component.getModel() );
        formComposite.init();
        hasComponents.addComponent( formComposite );
    }

    private void setupPanel( Method method )
    {
        EditPanel editPanel = method.getAnnotation( EditPanel.class );
        Class<? extends EditPanelComposite> editPanelClass = editPanel.type();
        EditPanelComposite editPanelComposite = factory.newInstance( editPanelClass );
        editPanelComposite.setModel( component.getModel() );
        editPanelComposite.init();
        hasComponents.addComponent( editPanelComposite );
    }

    private void setupPage( Method method )
    {
        Page page = method.getAnnotation( Page.class );
        Class<? extends PageComposite> pageClass = page.type();
        PageComposite pageComposite = factory.newInstance( pageClass );
        pageComposite.init();
        hasComponents.addComponent( pageComposite );
    }

    private void setupTextField( Method method )
    {
        TextFieldComposite textFieldComposite = factory.newInstance( TextFieldComposite.class );
        TextField textField = method.getAnnotation( TextField.class );

        Model containerModel = component.getModel();
        Model model = factory.newInstance( ModelComposite.class );
        model.setObject( containerModel.getObject() );
        model.setFieldName( method.getName() );
        textFieldComposite.setModel( model );

        hasComponents.addComponent( textFieldComposite );
    }

    private void setupLabel( Method method )
    {
        LabelComposite labelComposite = factory.newInstance( LabelComposite.class );
        Label label = method.getAnnotation( Label.class );
        String value = label.value();

        labelComposite.setValue( value );

        hasComponents.addComponent( labelComposite );
    }
}