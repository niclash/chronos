package org.qi4j.chronos.model.composites.association;

import java.util.Iterator;
import org.qi4j.chronos.model.composites.AccountPersistentComposite;

public interface HasAccounts
{
    void addAccount( AccountPersistentComposite account);

    void removeAccount( AccountPersistentComposite account);

    Iterator<AccountPersistentComposite> accountIterator();
}
