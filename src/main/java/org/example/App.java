package org.example;

import static java.net.http.HttpRequest.BodyPublishers.noBody;
import static java.net.http.HttpResponse.BodyHandlers.ofString;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import com.microsoft.playwright.Playwright;

public class App {
	public static void main(String[] args) {
		try (var playwright = Playwright.create()) {
			String wsEndpoint = createRemoteBrowserAndGetWsEndpoint();
			System.out.println("Connecting to wsEndpoint: " + wsEndpoint);
			try (var browser = playwright.chromium().connectOverCDP(wsEndpoint)) {
				try (var browserContext = browser.newContext()) {
					try (var page = browserContext.newPage()) {
						page.navigate("https://playwright.dev/");
						System.out.println("Page title: " + page.title());
					}
				}
			}
		}

	}

	private static String createRemoteBrowserAndGetWsEndpoint() {
		var client = HttpClient.newHttpClient();

		var request = HttpRequest.newBuilder(
				URI.create("http://localhost:3000/browsers"))
			.POST(noBody())
			.build();

		try {
			return client.send(request, ofString()).body();
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
