package ch.rasc.githubbackup;

import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class CustomJschConfigSessionFactory extends JschConfigSessionFactory {
		
	private final String key;
	private final String knownHosts;
	
    public CustomJschConfigSessionFactory(String key, String knownHosts) {
		super();
		this.key = key;
		this.knownHosts = knownHosts;
	}

	@Override
    protected void configure(OpenSshConfig.Host host, Session session) {
        session.setConfig("StrictHostKeyChecking", "yes");
    }

	@Override
	protected JSch getJSch(Host hc, FS fs) throws JSchException {		
		JSch jsch = super.getJSch(hc, fs);
        jsch.addIdentity(key);
        jsch.setKnownHosts(knownHosts);
        return jsch;
	}
    
}