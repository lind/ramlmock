package org.nextstate;

import static java.util.Arrays.asList;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

public class MockServerStandalone {

    public static void main(String[] args) {

        OptionParser parser = new OptionParser("r::");
        OptionSpec<String> ramlfile = parser.acceptsAll(asList("r", "ramlfile"))
                .withRequiredArg().required()
                .ofType(String.class);
        OptionSpec<Integer> port = parser.acceptsAll(asList("p", "port"))
                .withRequiredArg().required()
                .ofType(Integer.class);

        OptionSet options = parser.parse(args);

        new MockServer(ramlfile.value(options), port.value(options));
    }
}
