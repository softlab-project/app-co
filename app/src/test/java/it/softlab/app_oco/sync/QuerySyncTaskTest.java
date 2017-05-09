package it.softlab.app_oco.sync;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URL;

import it.softlab.app_oco.utilities.NetworkUtils;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by claudio on 5/9/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class QuerySyncTaskTest {

    @Mock


    @Test
    public void queryProducts() throws Exception {
        URL url = new URL("http://www.example.com");
        MockWebServer server = new MockWebServer();

        server.enqueue(new MockResponse().setBody("hello, world!"));
        server.enqueue(new MockResponse().setBody("sup, bra?"));
        server.enqueue(new MockResponse().setBody("yo dog"));

        server.start();

        server.url(NetworkUtils.BASE_URL);

        URL baseUrl = NetworkUtils.buildUrlWithKeyword("hello");

        String result = NetworkUtils.getResponseFromUrl(baseUrl);
        assertThat(result,is("hello, world!"));

    }

}