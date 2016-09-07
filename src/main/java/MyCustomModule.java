import com.sun.appserv.security.AppservPasswordLoginModule;

import javax.security.auth.login.LoginException;

/**
 * Custom module
 *
 * @author Created by Max on 05.09.2016.
 */

public class MyCustomModule extends AppservPasswordLoginModule
{
    @Override
    protected void authenticateUser() throws LoginException
    {
        SecurityUtil.authenticateUser(_username, _passwd);

        commitUserAuthentication(SecurityUtil.getGroups(_username)
                .toArray(new String[SecurityUtil.getGroups(_username).size()]));
    }
}
