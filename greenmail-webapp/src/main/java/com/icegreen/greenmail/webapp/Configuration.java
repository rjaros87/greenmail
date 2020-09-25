package com.icegreen.greenmail.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * GreenMail Web configuration.
 *
 * @author mm
 */
public class Configuration {
    /**
     * Default hostname ({@value}
     */
    public static final String DEFAULT_HOSTNAME = "localhost";
    /**
     * Default port offset added to SMTP/IMAP/POP3/... default ports ({@value}).
     * <p>
     * Example: A port offset of 10000 results in SMTP port 10025.
     */
    public static final int DEFAULT_PORT_OFFSET = 10000;

    /**
     * A mail service configuration entry.
     * <p>
     * An entry contains a mandatory protocol and optional hostname and port.
     * If the hostname and port are not configured, GreenMail uses
     * the default hostname and the default protocol port plus the port offset.
     */
    public static class ServiceConfiguration {
        Protocol protocol;
        String hostname;
        int port;

        public Protocol getProtocol() {
            return protocol;
        }

        public String getHostname() {
            return hostname;
        }

        public int getPort() {
            return port;
        }
    }

    public static class User {
        String login;
        String password;
        String email;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    private String defaultHostname;
    private int portOffset;
    private List<ServiceConfiguration> services;
    private List<User> users;

    /**
     * Initializes configuration with
     */
    public Configuration() {
        services = new ArrayList<>();
        defaultHostname = DEFAULT_HOSTNAME;
        portOffset = DEFAULT_PORT_OFFSET;
        users = new ArrayList<>();
    }

    public String getDefaultHostname() {
        return defaultHostname;
    }

    public void setDefaultHostname(final String pDefaultHostname) {
        defaultHostname = pDefaultHostname;
    }

    public int getPortOffset() {
        return portOffset;
    }

    public void setPortOffset(final int pPortOffset) {
        portOffset = pPortOffset;
    }

    public void addUser(final User pUser) {
        users.add(pUser);
    }

    public void deleteUser(final String login) {
        if (null != login && !login.isEmpty()) {
            for(Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
                User user = iterator.next();
                if (login.equals(user.getLogin())) {
                    iterator.remove();
                }
            }
        }
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    public void addServiceConfiguration(final ServiceConfiguration pServiceConfiguration) {
        services.add(pServiceConfiguration);
    }

    public List<ServiceConfiguration> getServiceConfigurations() {
        return Collections.unmodifiableList(services);
    }

    public ServiceConfiguration getServiceConfigurationByProtocol(final Protocol pProtocol) {
        for (ServiceConfiguration c : services) {
            if (pProtocol.equals(c.protocol)) {
                return c;
            }
        }
        return null;
    }
}
