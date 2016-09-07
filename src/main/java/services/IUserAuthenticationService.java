
package services;

import java.util.List;

/**
* Interface for user validation services
*/

public interface IUserAuthenticationService
{
    /**
     * Validates the password specified by the user
     */

    public void validatePassword(String usid, String password) throws Exception;

    /**
     * Returns the list of groups whom the user belongs to
     */

    public List<String> getGroups(String usid);

}