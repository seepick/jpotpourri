package net.sourceforge.jpotpourri.util;

import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public final class PtCloseUtilTest extends PtAbstractUtilTestCase {

    private static final Log LOG = LogFactory.getLog(PtCloseUtilTest.class);


	public void testCloseNull() throws Exception {
		innerTestCloseNull(PtCloseUtil.class);
		innerTestCloseNull(SubCloseUtil.class);
	}

	private void innerTestCloseNull(final Class<? extends PtCloseUtil> clazz) throws Exception {
		final Closeable[] methodArgs = new Closeable[] { null };
		invokeCloseMethod(clazz, methodArgs);
		// should not throw exception
	}


	public void testCloseException() {
		innerTestCloseException(PtCloseUtil.class);
		innerTestCloseException(SubCloseUtil.class);
	}
	
	private void innerTestCloseException(final Class<? extends PtCloseUtil> clazz) {
		try {
			LOG.info("CloseUtil will throw exception.");
			
			invokeCloseMethod(clazz, new Closeable[] { new ThrowingTestCloseable() });
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	

	
	public void testCloseState() throws Exception {
		innerTestCloseState(PtCloseUtil.class);
		innerTestCloseState(SubCloseUtil.class);
	}
	
	private void innerTestCloseState(final Class<? extends PtCloseUtil> clazz) throws Exception {
		final ManagedTestCloseable closeable = new ManagedTestCloseable();
		final Closeable[] methodArgs = new Closeable[] { closeable };
		assertFalse(closeable.isClosed());

		invokeCloseMethod(clazz, methodArgs);
		assertTrue(closeable.isClosed());

		LOG.info("CloseUtil will log exception.");
		invokeCloseMethod(clazz, methodArgs);
	}
	

	
	private static void invokeCloseMethod(final Class<? extends PtCloseUtil> clazz,
			final Closeable[] methodArgs) throws Exception {
		clazz.getMethod("close", Closeable[].class).invoke(null, (Object) methodArgs);
	}

	
	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class ThrowingTestCloseable implements Closeable {
		public void close() throws IOException {
			throw new IOException("CloseUtilTest did throw exception - Okay.");
		}
	}

	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class ManagedTestCloseable implements Closeable {
		private boolean closed = false;
		public void close() throws IOException {
			if(this.isClosed() == true) {
				throw new IOException("TestCloseable is already closed - Okay.");
			}
			this.closed = true;
		}
		public boolean isClosed() {
			return this.closed;
		}
	}

	/**
	 * @author christoph_pickl@users.sourceforge.net
	 */
	private static class SubCloseUtil extends PtCloseUtil {
		protected SubCloseUtil() {
			super();
		}
		// could have some additional functionality
	}
	
}
