import services.IUserAuthenticationService;

import javax.naming.InitialContext;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityUtil
{
    /**
     * route to UserAuthenticationService in J2ee app for realisation IUserAuthenticationService interface
     */
    private static final String AUTHENTICATION_INTERFACE = "java:global/pasman/UserAuthenticationService";
    private static Logger logger = Logger.getLogger(SecurityUtil.class.getName());

    /**
     * @param jndiClassname - String  route to jndiClassname ( implements IUserAuthenticationService)
     * @return object implements IUserAuthenticationService
     * @throws Exception if jndiClassname not found by param
     */
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

    /**
     * Try to authenticate User by name and password
     *
     * @param username String username
     * @param password char[] arrey of user password
     * @throws LoginException if system cant validate user.
     */
    public static void authenticateUser(String username, char[] password) throws LoginException
    {
        logger.log(Level.WARNING, AUTHENTICATION_INTERFACE + " trying to authenticate user " + username);

        try
        {
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
     * @param usid User name
     * @return List<String> of user groups
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
