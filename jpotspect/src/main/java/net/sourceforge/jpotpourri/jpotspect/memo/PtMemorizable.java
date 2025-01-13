package net.sourceforge.jpotpourri.jpotspect.memo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PtMemorizable {

	// nothing to do
	
}
