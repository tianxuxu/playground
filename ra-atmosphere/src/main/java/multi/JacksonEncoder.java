package multi;

import java.io.IOException;

import org.atmosphere.config.managed.Encoder;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Encode a {@link ChatProtocol} into a String
 */
public class JacksonEncoder implements Encoder<JacksonEncoder.Encodable, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String encode(Encodable m) {
        try {
            return mapper.writeValueAsString(m);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Marker interface for Jackson.
     */
    public static interface Encodable {
    }
}
