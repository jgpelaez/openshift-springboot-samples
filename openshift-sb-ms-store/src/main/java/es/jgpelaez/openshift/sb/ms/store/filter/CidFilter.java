package es.jgpelaez.openshift.sb.ms.store.filter;

import java.io.IOException;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class CidFilter implements Filter {
	private Random random = new Random();
	private final int MAX_ID_SIZE = 50;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		if (req instanceof HttpServletRequest) {

			HttpServletRequest request = (HttpServletRequest) req;
			String requestCid = request.getHeader("CID");
			String cid = verifyOrCreateId(requestCid);
			// add cid to the MDC
			MDC.put("CID", cid);
		}

		try {
			// call filter(s) upstream for the real processing of the request
			chain.doFilter(req, res);
		} finally {
			// it's important to always clean the cid from the MDC,
			// this Thread goes to the pool but it's loglines would still
			// contain the cid.
			MDC.remove("CID");
		}

	}

	public String verifyOrCreateId(String correlationId) {
		if (correlationId == null) {
			correlationId = generateCorrelationId();
		}
		// prevent on-purpose or accidental DOS attack that
		// fills logs with long correlation id provided by client
		else if (correlationId.length() > MAX_ID_SIZE) {
			correlationId = correlationId.substring(0, MAX_ID_SIZE);
		}

		return correlationId;
	}

	private String generateCorrelationId() {
		long randomNum = random.nextLong();
		return encodeBase62(randomNum);

	}

	/**
	 * Encode the given Long in base 62
	 * 
	 * @param n
	 *            Number to encode
	 * @return Long encoded as base 62
	 */
	private String encodeBase62(long n) {

		final String base62Chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		StringBuilder builder = new StringBuilder();

		// NOTE: Appending builds a reverse encoded string. The most significant
		// value
		// is at the end of the string. You could prepend(insert) but appending
		// is slightly better performance and order doesn't matter here.

		// perform the first selection using unsigned ops to get negative
		// numbers down into positive signed range.
		long index = Long.remainderUnsigned(n, 62);
		builder.append(base62Chars.charAt((int) index));
		n = Long.divideUnsigned(n, 62);
		// now the long is unsigned, can just do regular math ops
		while (n > 0) {
			builder.append(base62Chars.charAt((int) (n % 62)));
			n /= 62;
		}
		return builder.toString();
	}

	@Override
	public void destroy() {
		// nothing
	}

	@Override
	public void init(FilterConfig fc) throws ServletException {
		// nothing
	}
}