package org.qi4j.chronos.model;

import java.util.List;
import org.qi4j.chronos.model.composites.AccountComposite;

public interface HasAccounts
{
    void addAccount( AccountComposite account);

    void removeAccount(AccountComposite account);

    List<AccountComposite> getAccounts();
}
