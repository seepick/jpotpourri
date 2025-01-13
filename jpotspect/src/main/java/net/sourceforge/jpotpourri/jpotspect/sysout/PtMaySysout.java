package net.sourceforge.jpotpourri.jpotspect.sysout;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author christoph_pickl@users.sourceforge.net
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE}) // , ElementType.CONSTRUCTOR, ElementType.METHOD
public @interface PtMaySysout {

	// nothing to do

}
