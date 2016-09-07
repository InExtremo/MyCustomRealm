import javax.security.auth.login.LoginException;

import com.sun.appserv.security.AppservPasswordLoginModule;

/**
 * Custom module
 *
 * @author dgisbert
 */

public class MyCustomModule extends AppservPasswordLoginModule
{
    @Override
    protected void authenticateUser() throws LoginException
    {
        SecurityUtil.authenticateUser(_username, _passwd);

       // String[] groups = {"admin"};

        commitUserAuthentication(SecurityUtil.getGroups(_username)
                .toArray(new String[SecurityUtil.getGroups(_username).size()]));
    }
}
