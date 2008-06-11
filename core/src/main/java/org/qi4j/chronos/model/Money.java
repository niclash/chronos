/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 12, 2008
 * Time: 7:42:01 PM
 */
package org.qi4j.chronos.model;

import java.text.NumberFormat;
import org.qi4j.composite.Mixins;
import org.qi4j.injection.scope.PropertyField;
import org.qi4j.library.general.model.Amount;
import org.qi4j.library.general.model.Currency;
import org.qi4j.property.ComputedPropertyInstance;
import org.qi4j.property.ImmutableProperty;

@Mixins( Money.DescriptorMixin.class )
public interface Money extends Currency, Amount<Long>, Descriptor
{
    public static final class DescriptorMixin implements Descriptor
    {
        private @This Money money;

        private @PropertyField ImmutableProperty<String> displayValue;

        public ImmutableProperty<String> displayValue()
        {
            return new ComputedPropertyInstance<String>( displayValue )
            {
                private String m_displayValue = money.currency().get().getSymbol() + NumberFormat.getIntegerInstance().format( money.amount().get() );

                public String get()
                {
                    return m_displayValue;
                }
            };
        }
    }
}
