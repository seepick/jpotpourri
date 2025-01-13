package net.sourceforge.jpotpourri.jpotface.inputfield;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PtTextFieldSuggester extends AbstractTextSuggester {

	private static final long serialVersionUID = 7210875385129631800L;

	private List<String> values; // TODO rename to "suggestions"

	public PtTextFieldSuggester(int columns, List<String> values) {
		super(columns);
		this.values = Collections.unmodifiableList(new ArrayList<String>(values));
	}

	public PtTextFieldSuggester(List<String> values) {
		this.values = Collections.unmodifiableList(new ArrayList<String>(values));
	}
	
	public void setValues(List<String> values) {
		this.values = Collections.unmodifiableList(new ArrayList<String>(values));
	}

	@Override
	protected List<String> getValues() {
		return this.values;
	}

}
