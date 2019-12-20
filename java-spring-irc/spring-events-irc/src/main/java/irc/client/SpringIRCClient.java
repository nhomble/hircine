package irc.client;

import com.sun.org.apache.bcel.internal.generic.FieldOrMethod;
import io.github.hombro.irc.core.IRCClient;
import io.github.hombro.irc.core.SocketIRCClient;
import org.springframework.beans.factory.InitializingBean;

import java.util.Optional;

public class SpringIRCClient implements InitializingBean {

    private final IRCClient irc;

    public SpringIRCClient(IRCClient irc) {
        this.irc = irc;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        irc.login();
        Runtime.getRuntime().addShutdownHook(new Thread(irc::quit));
    }

    public IRCClient getIRC(){
        return irc;
    }
}
