package net.sourceforge.jpotpourri.jpotspect.notnull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PtNotNull {

	// nothing to do here
	
}
