import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom Realm class
 *
 * @author Created by Max on 05.09.2016.
 */

public class MyCustomRealm extends AppservRealm
{
    /** JAAS Context parameter name */ public static final String PARAM_JAAS_CONTEXT = "jaas-context";

    /** Authentication type description */

    public static final String AUTH_TPYE = "Authentication done by checking user at table USERS on database";

    @Override
    public void init(Properties properties) throws BadRealmException, NoSuchRealmException
    {
        String propJaasContext = properties.getProperty(PARAM_JAAS_CONTEXT);

        if (propJaasContext != null) setProperty(PARAM_JAAS_CONTEXT, propJaasContext);

        Logger logger = Logger.getLogger(this.getClass().getName());

        String realmName = this.getClass().getSimpleName();

        logger.log(Level.WARNING, realmName + " started. ");
        logger.log(Level.WARNING, realmName + ": " + getAuthType());
        logger.log(Level.WARNING, realmName + " authentication uses jar file REALM.jar located at $domain/lib folder ");

        for (Entry<Object, Object> property:properties.entrySet()) logger.log(Level.WARNING, property.getKey() + ": " + property.getValue());
    }

    @Override
    public String getAuthType()
    {
        return AUTH_TPYE;
    }

    @Override
    public Enumeration<?> getGroupNames(String usid) throws InvalidOperationException, NoSuchUserException
    {
        return Collections.enumeration(SecurityUtil.getGroups(usid));
    }
 }
