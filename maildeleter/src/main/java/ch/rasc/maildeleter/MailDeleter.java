package ch.rasc.maildeleter;

import java.util.Properties;
import java.util.TimerTask;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.mail.imap.IMAPFolder;

public class MailDeleter extends TimerTask {

	private final static Logger logger = LoggerFactory.getLogger(Main.class);

	private final Config config;

	MailDeleter(Config config) {
		this.config = config;
	}

	@Override
	public void run() {
		logger.info("start");

		IMAPFolder folder = null;
		Store store = null;
		try {
			Properties props = System.getProperties();
			Session session = Session.getDefaultInstance(props);

			store = session.getStore("imap");
			store.connect(config.getHost(), config.getUser(), config.getPassword());

			folder = (IMAPFolder) store.getFolder("INBOX");

			folder.open(Folder.READ_WRITE);

			DateTime tenDaysAgo = DateTime.now().minusDays(config.getDays());

			Message[] messages = folder.getMessages();
			for (Message msg : messages) {

				DateTime receivedDateTime = new DateTime(msg.getReceivedDate());
				if (receivedDateTime.isBefore(tenDaysAgo)) {
					logger.info("delete msg: {}", folder.getUID(msg));
					msg.setFlag(Flags.Flag.DELETED, true);
				} else {
					logger.info("keep msg: {}", folder.getUID(msg));
				}
			}
		} catch (MessagingException e) {
			logger.error("delete emails", e);
		} finally {
			if (folder != null && folder.isOpen()) {
				try {
					folder.close(true);
				} catch (MessagingException e) {
					logger.error("close folder", e);
				}
			}
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					logger.error("close store", e);
				}
			}
		}
		logger.info("end");

	}

}
