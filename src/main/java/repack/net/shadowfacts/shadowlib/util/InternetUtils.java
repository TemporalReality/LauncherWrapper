package repack.net.shadowfacts.shadowlib.util;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author shadowfacts
 */
public class InternetUtils {

	/**
	 * Retrieves the contents of a file on the internet.
	 * @param url The URL of the file to retrieve
	 * @return The contents the file as a {@link String}
	 * @throws IOException Thrown if there was a problem, opening, reading from, or closing the stream.
	 * 						Thrown if no protocol is specified, an unknown protocol was specified, or if the URL was {@code null}
	 */
	public static String getResourceAsString(String url) throws IOException {
		return getResourceAsString(new URL(url));
	}

	/**
	 * Retrieves the contents of a file on the internet.
	 * @param url The URL of the file to retrieve
	 * @return The contents of the file as a {@link String}
	 * @throws IOException Thrown if there was a problem, opening, reading from, or closing the stream.
	 */
	public static String getResourceAsString(URL url) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer buffer = new StringBuffer();
			int read;
			char[] chars = new char[1024];
			while ((read = reader.read(chars)) != -1) {
				buffer.append(chars, 0, read);
			}
			return buffer.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	/**
	 * Downloads a file from the specified URL to the destination.
	 * @param url
	 * @param destination
	 * @throws IOException Thrown if the URL is invalid the input stream cannot be opened, the output file cannot be found, problem transferring the file
	 */
	public static void downloadFile(String url, File destination) throws IOException {
		downloadFile(new URL(url), destination);
	}

	/**
	 * Downloads a file from the specified URL to the destination.
	 * @param url
	 * @param destination
	 * @throws IOException Thrown if the URL is invalid the input stream cannot be opened, the output file cannot be found, problem transferring the file
	 */
	public static void downloadFile(String url, String destination) throws IOException {
		downloadFile(new URL(url), new File(destination));
	}

	/**
	 * Downloads a file from the specified URL to the destination.
	 * @param url
	 * @param destination
	 * @throws IOException Thrown if the URL is invalid the input stream cannot be opened, the output file cannot be found, problem transferring the file
	 */
	public static void downloadFile(URL url, String destination) throws IOException {
		downloadFile(url, new File(destination));
	}

	/**
	 * Downloads a file from the specified URL to the destination.
	 * @param url
	 * @param destination
	 * @throws IOException Thrown if the URL is invalid the input stream cannot be opened, the output file cannot be found, problem transferring the file
	 */
	public static void downloadFile(URL url, File destination) throws IOException {
		ReadableByteChannel in = Channels.newChannel(url.openStream());

		File parent = destination.getParentFile();
		if (!parent.exists()) parent.mkdirs();

		FileChannel out = new FileOutputStream(destination).getChannel();
		out.transferFrom(in, 0, Long.MAX_VALUE);
	}

}