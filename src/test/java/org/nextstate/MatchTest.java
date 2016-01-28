package org.nextstate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.IOException;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Rule;
import org.junit.Test;

public class MatchTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void shouldMatchBasic() {
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>Some content</response>")));

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:" + wireMockRule.port() + "/my/resource")
                .addHeader("Accept", "text/xml")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(response);
        assertEquals(response.code(), 200);
    }

    @Test
    public void shouldMatchPathWithIdParam() {
        stubFor(get(urlMatching("/my/resource/[0-9a-zA-Z.]*/details"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>Some content</response>")));

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:" + wireMockRule.port() + "/my/resource/id.23/details")
                .addHeader("Accept", "text/xml")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(response);
        assertEquals(response.code(), 200);
    }

    @Test
    public void shouldReplaceId() {
        String original = "/my/{id.2}/details";
        String replaced = original.replaceAll("\\{[0-9a-zA-Z.]*\\}", "yes");
        assertEquals("/my/yes/details", replaced);
    }

}
