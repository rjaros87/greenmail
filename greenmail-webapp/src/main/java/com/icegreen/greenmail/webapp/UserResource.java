package com.icegreen.greenmail.webapp;

import com.icegreen.greenmail.Managers;
import com.icegreen.greenmail.user.GreenMailUser;
import com.icegreen.greenmail.user.UserException;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

/**
 * JAX-RS 2.0
 */
@Path("/user")
public class UserResource {
    private @Context
    ServletContext context;

    @PUT
    @Path("/create")
    @Produces("application/json")
    @Consumes("application/json")
    public Configuration createUser(Configuration.User user) {
        Configuration conf = ContextHelper.getConfiguration(context);
        Managers managers = ContextHelper.getManagers(context);

        GreenMailUser greenMailUser = managers.getUserManager().getUser(user.login);
        if (null == greenMailUser) {
            try {
                conf.addUser(user);
                managers.getUserManager().createUser(
                    user.email, user.login, user.password
                );
            } catch (UserException e) {
                throw new IllegalStateException(e);
            }
        }

        return conf;
    }

    @DELETE
    @Path("/delete/{login}")
    @Produces("application/json")
    public Configuration deleteUser(@PathParam("login") String login) {
        Configuration conf = ContextHelper.getConfiguration(context);
        Managers managers = ContextHelper.getManagers(context);

        if(null != login && !login.isEmpty()) {
            GreenMailUser greenMailUser = managers.getUserManager().getUser(login);
            if (null != greenMailUser) {
                managers.getUserManager().deleteUser(greenMailUser);
                conf.deleteUser(greenMailUser.getLogin());
            }
        }

        return conf;
    }
}
