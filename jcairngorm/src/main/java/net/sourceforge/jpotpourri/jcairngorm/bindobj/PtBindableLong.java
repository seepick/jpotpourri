package net.sourceforge.jpotpourri.jcairngorm.bindobj;

import java.awt.Toolkit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBindableLong extends PtAbstractBindableSimpleObject<Long> implements IPtBindableStringLike<Long> {

	private static final Log LOG = LogFactory.getLog(PtBindableLong.class);
	
	
	public PtBindableLong(final Long value) {
		this(value, "__Long");
	}
	
	public PtBindableLong(final Long value, final String propertyName) {
		super(value, propertyName);
	}

	public String getAsString() {
		return String.valueOf(this.getValue());
	}

	public void setAsString(final String value) {
		if(value.trim().length() == 0) {
			this.setValue(0L);
		} else {
			try {
				this.setValue(Long.parseLong(value.trim()));
			} catch(final NumberFormatException e) {
				Toolkit.getDefaultToolkit().beep();
				LOG.warn("Invalid number entered [" + value + "]!");
			}
		}
	}
	
}
