package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import io.micrometer.prometheus.PrometheusMeterRegistry;

@SuppressWarnings("restriction")
public class ServerHttp {
	private HttpServer server;

	public void createServerHttp(PrometheusMeterRegistry prometheusMeterRegistry, String context, int port) {
		
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext(context, httpExchange -> {
				String response = prometheusMeterRegistry.scrape();
				httpExchange.sendResponseHeaders(200, response.getBytes().length);
				try (OutputStream os = httpExchange.getResponseBody()) {
					os.write(response.getBytes());
				}
			});


			server.start();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void closeServer() {
		server.stop(0);
	}
	
}
