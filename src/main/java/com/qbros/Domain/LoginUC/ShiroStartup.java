package com.qbros.Domain.LoginUC;

import org.apache.shiro.realm.Realm;
import org.omnifaces.cdi.Eager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by V.Ghasemi
 * on 12/12/2018.
 */



// using custom realm
// Link: https://stackoverflow.com/questions/18507629/inject-cdi-managed-bean-in-custom-shiro-authorizingrealm

@ApplicationScoped
@Eager
public class ShiroStartup {

    public final static String JNDI_REALM_NAME = "realms/myRealm";
    // use CDI interceptor or sth like that instead of this shitty approach
    private final static String CLASSNAME = ShiroStartup.class.getSimpleName();
    private final static Logger LOG = Logger.getLogger( CLASSNAME );

    // Can also be EJB...
//    @Inject private UserAccess userAccess;

    @PostConstruct
    public void setup() {
        final Realm realm = new MyAppRealm();

        // Make the realm available to Shiro.
        try {
            bind(JNDI_REALM_NAME, realm );
        } catch( NamingException ex ) {
            LOG.log(Level.SEVERE, "Could not bind realm: " + JNDI_REALM_NAME, ex );
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            unbind(JNDI_REALM_NAME );
        }
        catch( NamingException ex ) {
            LOG.log(Level.SEVERE, "Could not unbind realm: " + JNDI_REALM_NAME, ex );
        }
    }

    /**
     * Binds a JNDI name to an object.
     *
     * @param jndi The JNDI name.
     * @param object The object to bind to the JNDI name.
     */
    private static void bind( final String jndi, final Object object ) throws NamingException {

        final InitialContext initialContext = createInitialContext();
        initialContext.bind( jndi, object );
    }

    private static void unbind( final String name ) throws NamingException {

        final InitialContext initialContext = createInitialContext();
        initialContext.unbind( name );
    }

    private static InitialContext createInitialContext() throws NamingException {
        return new InitialContext();
    }
}
