package com.windf.core.util;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class StreamUtil {
    /**
     * Copy the contents of the given InputStream into a new byte array.
     * Leaves the stream open when done.
     * @param in the stream to copy from
     * @return the new byte array that has been copied to
     * @throws IOException in case of I/O errors
     */
    public static byte[] copyToByteArray(InputStream in) throws IOException {
        return StreamUtils.copyToByteArray(in);
    }

    /**
     * Copy the contents of the given InputStream into a String.
     * Leaves the stream open when done.
     * @param in the InputStream to copy from
     * @return the String that has been copied to
     * @throws IOException in case of I/O errors
     */
    public static String copyToString(InputStream in, Charset charset) throws IOException {
        return StreamUtils.copyToString(in, charset);
    }

    /**
     * Copy the contents of the given byte array to the given OutputStream.
     * Leaves the stream open when done.
     * @param in the byte array to copy from
     * @param out the OutputStream to copy to
     * @throws IOException in case of I/O errors
     */
    public static void copy(byte[] in, OutputStream out) throws IOException {
        StreamUtils.copy(in, out);
    }

    /**
     * Copy the contents of the given String to the given output OutputStream.
     * Leaves the stream open when done.
     * @param in the String to copy from
     * @param charset the Charset
     * @param out the OutputStream to copy to
     * @throws IOException in case of I/O errors
     */
    public static void copy(String in, Charset charset, OutputStream out) throws IOException {
        StreamUtils.copy(in, charset, out);
    }

    /**
     * Copy the contents of the given InputStream to the given OutputStream.
     * Leaves both streams open when done.
     * @param in the InputStream to copy from
     * @param out the OutputStream to copy to
     * @return the number of bytes copied
     * @throws IOException in case of I/O errors
     */
    public static int copy(InputStream in, OutputStream out) throws IOException {
        return StreamUtils.copy(in, out);
    }

}
