package net.sourceforge.jpotpourri.jcairngorm.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.sourceforge.jpotpourri.jcairngorm.IPtBindingListener;
import net.sourceforge.jpotpourri.jcairngorm.bindobj.PtBindableLong;
import net.sourceforge.jpotpourri.jcairngorm.event.PtBindingEvent;
import net.sourceforge.jpotpourri.jpotface.inputfield.PtTimestampField;
import net.sourceforge.jpotpourri.util.PtDateUtil;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
public class PtBTimestampField implements IPtBindingListener<Long> {

	private final PtTimestampField field;
	
	public PtBTimestampField(final PtTimestampField label, final PtBindableLong value) {
		this.field = label;
		this.field.setText(PtDateUtil.formatTimestamp(value.getValue().longValue()));
		
		this.field.addKeyListener(new KeyListener() {
			public void keyPressed(final KeyEvent event) { /* nothing to do */ }
			public void keyTyped(final KeyEvent event) { /* nothing to do */ }
			
			public void keyReleased(final KeyEvent event) {
				if(PtBTimestampField.this.field.isFullyValid()) {
					value.setValue(PtBTimestampField.this.field.getLongValue());
				}
			}
		});
		
		value.addBindingListener(this);
	}

	public void receiveEvent(final PtBindingEvent<Long> event) {
		this.field.setText(PtDateUtil.formatTimestamp(event.getNewValue().longValue()));
	}
}
