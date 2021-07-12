package com.unnivp.thingworx.connectors.kinesis.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.IPasswordCallback;
import static com.unnivp.thingworx.connectors.kinesis.constants.KinesisConnectorConstants.*;

/**
 * Configuration for Web Socket Client connection to ThingWorx
 * 
 * @author unnivp
 *
 */
@Component
public class WSClientConfigurator extends ClientConfigurator {

	private static final Logger logger = LoggerFactory.getLogger(WSClientConfigurator.class);

	public WSClientConfigurator() {
		super();
	}

	/**
	 * Create an edge client configuration that will use the provided API key for
	 * authentication.
	 * 
	 * @return a client configurator.
	 */
	public void initClientConfigurator(String host, int port, String appKey, int reconnectInterval,
			boolean isSecureConnection) {

		// Set the required configuration information
		StringBuffer thingworxUrl = new StringBuffer();
		if (isSecureConnection) {
			thingworxUrl.append(SECURE_WEBSOCKET_PREFIX).append(host).append(DELIMITER_COLON).append(port)
					.append(THINGWORX_URL);
		} else {
			thingworxUrl.append(PLAIN_WEBSOCKET_PREFIX).append(host).append(DELIMITER_COLON).append(port)
					.append(THINGWORX_URL);
		}
		this.setUri(thingworxUrl.toString());
		this.setReconnectInterval(reconnectInterval); // Reconnect every X seconds if a disconnect occurs
		this.setSecurityClaims(new AppKeyPasswordCallback(appKey));
		this.ignoreSSLErrors(true); // All self signed certs

		logger.info("Initializing TWX Client config: " + thingworxUrl.toString());

	}

	/**
	 * Sample password callback to configure an app key. Not suitable for production
	 * environments.
	 *
	 */
	class AppKeyPasswordCallback implements IPasswordCallback {

		private String appKey = null;

		public AppKeyPasswordCallback(String appKey) {
			this.appKey = appKey;
		}

		@Override
		public char[] getSecret() {

			return appKey.toCharArray();
		}
	}

}
