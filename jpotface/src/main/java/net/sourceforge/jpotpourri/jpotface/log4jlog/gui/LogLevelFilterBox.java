package net.sourceforge.jpotpourri.jpotface.log4jlog.gui;

import javax.swing.JComboBox;

import net.sourceforge.jpotpourri.jpotface.log4jlog.PtAllLogLevels;

import org.apache.log4j.Level;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
final class LogLevelFilterBox extends JComboBox {

	private static final long serialVersionUID = 4527119967934749051L;

	public LogLevelFilterBox() {
		this(Level.ALL); // default value
	}
	
	public LogLevelFilterBox(final Level initLevel) {
		super(PtAllLogLevels.LEVELS_WITHOUT_ALL_AND_OFF.toArray());
		this.setSelectedItem(initLevel);

	}
	
	public Level getSelectedLevel() {
		return (Level) this.getSelectedItem();
	}
	
}
