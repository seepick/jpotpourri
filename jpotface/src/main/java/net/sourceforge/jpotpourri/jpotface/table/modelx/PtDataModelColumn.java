package net.sourceforge.jpotpourri.jpotface.table.modelx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PtDataModelColumn {

	String label();
	
	int index();
	
}
