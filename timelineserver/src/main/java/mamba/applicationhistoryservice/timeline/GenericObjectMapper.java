package mamba.applicationhistoryservice.timeline;


import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by dongbin on 2016/10/10.
 */
public class GenericObjectMapper {
    public static final ObjectReader OBJECT_READER;
    public static final ObjectWriter OBJECT_WRITER;
    private static final byte[] EMPTY_BYTES = new byte[0];

    static {
        ObjectMapper mapper = new ObjectMapper();
        OBJECT_READER = mapper.reader(Object.class);
        OBJECT_WRITER = mapper.writer();
    }

    /**
     * Serializes an Object into a byte array. Along with {@link #read(byte[])},
     * can be used to serialize an Object and deserialize it into an Object of
     * the same type without needing to specify the Object's type,
     * as long as it is one of the JSON-compatible objects understood by
     * ObjectMapper.
     *
     * @param o An Object
     * @return A byte array representation of the Object
     * @throws IOException if there is a write error
     */
    public static byte[] write(Object o) throws IOException {
        if (o == null) {
            return EMPTY_BYTES;
        }
        return OBJECT_WRITER.writeValueAsBytes(o);
    }

    /**
     * Deserializes an Object from a byte array created with
     * {@link #write(Object)}.
     *
     * @param b A byte array
     * @return An Object
     * @throws IOException if there is a read error
     */
    public static Object read(byte[] b) throws IOException {
        return read(b, 0);
    }

    /**
     * Deserializes an Object from a byte array at a specified offset, assuming
     * the bytes were created with {@link #write(Object)}.
     *
     * @param b      A byte array
     * @param offset Offset into the array
     * @return An Object
     * @throws IOException if there is a read error
     */
    public static Object read(byte[] b, int offset) throws IOException {
        if (b == null || b.length == 0) {
            return null;
        }
        return OBJECT_READER.readValue(b, offset, b.length - offset);
    }

    /**
     * Converts a long to a 8-byte array so that lexicographic ordering of the
     * produced byte arrays sort the longs in descending order.
     *
     * @param l A long
     * @return A byte array
     */
    public static byte[] writeReverseOrderedLong(long l) {
        byte[] b = new byte[8];
        return writeReverseOrderedLong(l, b, 0);
    }

    public static byte[] writeReverseOrderedLong(long l, byte[] b, int offset) {
        b[offset] = (byte) (0x7f ^ ((l >> 56) & 0xff));
        for (int i = offset + 1; i < offset + 7; i++) {
            b[i] = (byte) (0xff ^ ((l >> 8 * (7 - i)) & 0xff));
        }
        b[offset + 7] = (byte) (0xff ^ (l & 0xff));
        return b;
    }

    /**
     * Reads 8 bytes from an array starting at the specified offset and
     * converts them to a long.  The bytes are assumed to have been created
     * with {@link #writeReverseOrderedLong}.
     *
     * @param b      A byte array
     * @param offset An offset into the byte array
     * @return A long
     */
    public static long readReverseOrderedLong(byte[] b, int offset) {
        long l = b[offset] & 0xff;
        for (int i = 1; i < 8; i++) {
            l = l << 8;
            l = l | (b[offset + i] & 0xff);
        }
        return l ^ 0x7fffffffffffffffl;
    }

}
