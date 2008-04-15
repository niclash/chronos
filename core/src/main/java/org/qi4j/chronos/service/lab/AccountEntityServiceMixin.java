/**
 * Created by IntelliJ IDEA.
 * User: User
 * Date: Apr 10, 2008
 * Time: 10:22:43 PM
 * To change this template use File | Settings | File Templates.
 */
package org.qi4j.chronos.service.lab;

import org.qi4j.chronos.model.composites.AccountEntityComposite;
import org.qi4j.service.Activatable;

public class AccountEntityServiceMixin extends AbstractGenericEntityServiceMixin<AccountEntityComposite> implements GenericEntityService<AccountEntityComposite>, Activatable
{
    public void activate() throws Exception
    {
        this.clazz = AccountEntityComposite.class;
    }

    public void passivate() throws Exception
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
