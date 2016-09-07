import services.IUserAuthenticationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.security.auth.login.LoginException;

public class SecurityUtil
{
    private static Logger logger = Logger.getLogger(SecurityUtil.class.getName());

    public static final String AUTHENTICATION_INTERFACE = "java:global/pasman/UserAuthenticationService";


    private static IUserAuthenticationService lookupUserValidationService(String jndiClassname) throws Exception
    {
        Properties properties = new Properties();

        properties.put("java.naming.factory.initial" , "com.sun.enterprise.naming.SerialInitContextFactory");
        properties.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        properties.put("java.naming.factory.state"   , "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        InitialContext remoteContext = new InitialContext(properties);

        Object object = remoteContext.lookup(jndiClassname);

        return (IUserAuthenticationService) object;
    }


    public static void authenticateUser(String username, char[] password) throws LoginException
    {
        logger.log(Level.WARNING, AUTHENTICATION_INTERFACE + " trying to authenticate user " + username);

        try
        {
            //logger.log(Level.WARNING,"user password: " + password);
            System.err.println("user password: " + password.toString() + "\n user name: "+username );
            IUserAuthenticationService validationService = lookupUserValidationService(AUTHENTICATION_INTERFACE);
            validationService.validatePassword(username, new String(password));

        }
        catch (LoginException e)
        {
            throw new LoginException((new StringBuilder()).append("Error login: ").append(username).toString() + ". " + e.getMessage());
        } catch (Exception e) {
            throw new LoginException((new StringBuilder()).append("Error login: ").append(username).toString() + ". " + e.getMessage());
        }

    }

    /**
     * Returns the groups of this user
     */

    public static List<String> getGroups(String usid)
    {
        List<String> result = new ArrayList<String>();

        logger.log(Level.WARNING, AUTHENTICATION_INTERFACE + " trying to get groups of user  " + usid);
        try
        {
            IUserAuthenticationService validationService = SecurityUtil.lookupUserValidationService(AUTHENTICATION_INTERFACE);
            result = validationService.getGroups(usid);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
