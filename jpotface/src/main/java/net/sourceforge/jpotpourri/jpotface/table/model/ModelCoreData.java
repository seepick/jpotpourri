package net.sourceforge.jpotpourri.jpotface.table.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class ModelCoreData<T> {

	private final List<T> data;
	
	public ModelCoreData(final Collection<T> data) {
		this.data = new ArrayList<T>(data);
	}
	
	public synchronized T get(final int rowIndex) {
		return this.data.get(rowIndex);
	}
	
	public synchronized int size() {
		return this.data.size();
	}

	public synchronized void add(final T object) {
		this.data.add(0, object);
		// this.add(0, object);
	}

	public synchronized void add(final int rowIndex, final T object) {
		this.data.add(rowIndex, object);
	}
	
	public synchronized int remove(final T... objects) {
		int removedObjects = 0;
		for (final T object : objects) {
			final boolean wasRemoved = this.data.remove(object);
			if(wasRemoved == true) {
				removedObjects++;
			}
		}
		return removedObjects;
	}
	
	public synchronized void clear() {
		this.data.clear();
	}
	
}
