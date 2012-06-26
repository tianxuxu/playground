package ch.rasc.github;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.egit.github.core.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class Mylyn {

	public static void main(String[] args) throws IOException, JGitInternalException, InvalidRemoteException, URISyntaxException, WrongRepositoryStateException, InvalidConfigurationException, DetachedHeadException, CanceledException, RefNotFoundException, NoHeadException {
		RepositoryService service = new RepositoryService();
		for (org.eclipse.egit.github.core.Repository repo : service.getRepositories("ralscha")) {
			System.out.println(repo.getName());
			System.out.println(repo.getGitUrl());
			System.out.println();
		}

//		Git.cloneRepository().setBare(true).setURI("https://github.com/ralscha/embeddedtc.git")
//		.setDirectory(new File("test")).call();
		
		Git.open(new File("test")).fetch().call();
		
//		Repository newRepo = Git.init().setBare(true).setDirectory(new File("new")).call().getRepository();
//		
//		RemoteConfig config = new RemoteConfig(newRepo.getConfig(), Constants.DEFAULT_REMOTE_NAME);
//		URIish u = new URIish("git://github.com/ralscha/embeddedtc.git");
//		config.addURI(u);
//		config.setMirror(true);
//		
//		RefSpec refSpec = new RefSpec();
//		refSpec = refSpec.setForceUpdate(true);
//		refSpec = refSpec.setSourceDestination("refs/heads/*","refs/remotes/origin/*");
//		config.addFetchRefSpec(refSpec);
//		
//		config.update(newRepo.getConfig());		
//		newRepo.getConfig().save();
//		
//		
//		Git.wrap(newRepo).fetch().call();
//
//		newRepo.close();
		

	}

}
