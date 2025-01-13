package net.sourceforge.jpotpourri.jcairngorm.event;


/**
 * @param <T> event type
 * 
 * @author christoph_pickl@users.sourceforge.net
 */
public abstract class PtAbstractCairngormEvent<T extends IPtEventType<?>> {

	private final T type;
	
	private final Object source;

	
	public PtAbstractCairngormEvent(final T type, final Object source) {
		this.type = type;
		this.source = source;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[type=" + this.type + ";source=" + this.source + "]";
	}

	public T getType() {
		return this.type;
	}
	
	public Object getSource() {
		return this.source;
	}
	
}
