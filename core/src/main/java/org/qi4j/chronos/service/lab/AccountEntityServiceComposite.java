/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 10, 2008
 * Time: 10:33:24 PM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.service.lab;

import org.qi4j.composite.Mixins;
import org.qi4j.service.ServiceComposite;
import org.qi4j.service.Activatable;
import org.qi4j.chronos.model.composites.AccountEntityComposite;

@Mixins( AccountEntityServiceMixin.class )
public interface AccountEntityServiceComposite extends ServiceComposite, GenericEntityService<AccountEntityComposite>, Activatable
{
}
