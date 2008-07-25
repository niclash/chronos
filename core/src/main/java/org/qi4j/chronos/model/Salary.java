/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 11, 2008
 * Time: 9:55:56 PM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.model;

import org.qi4j.composite.Mixins;
import org.qi4j.injection.scope.PropertyField;
import org.qi4j.injection.scope.This;
import org.qi4j.library.general.model.Amount;
import org.qi4j.library.general.model.Currency;
import org.qi4j.library.general.model.Descriptor;
import org.qi4j.property.ComputedProperty;
import org.qi4j.property.ComputedPropertyInstance;

@Mixins( Salary.DescriptorMixin.class )
public interface Salary extends Amount<Long>, Currency, Descriptor
{
    static final class DescriptorMixin implements Descriptor
    {
        private @This Salary salary;

        private @PropertyField ComputedProperty<String> displayValue;

        public ComputedProperty<String> displayValue()
        {
            return new ComputedPropertyInstance<String>( displayValue )
            {
                private String m_displayValue = salary.currency().get().getSymbol() + salary.amount().get().toString();

                public String get()
                {
                    return m_displayValue;
                }
            };
        }
    }
}
