package org.nextstate;

import org.junit.Test;
import org.raml.model.Raml;
import org.raml.parser.visitor.RamlDocumentBuilder;

public class ParseRamlTest {

    @Test
    public void shouldParseMyRaml() {
        Raml raml = new RamlDocumentBuilder().build(
                Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("prello_api.raml"), "prello_api.raml");
        System.out.println(raml);
    }
}
